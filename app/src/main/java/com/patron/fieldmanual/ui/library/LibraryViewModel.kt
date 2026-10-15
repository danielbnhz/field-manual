package com.patron.fieldmanual.ui.library

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.patron.fieldmanual.data.DocumentLocation
import com.patron.fieldmanual.data.ExternalTextReader
import com.patron.fieldmanual.data.FolderScanner
import com.patron.fieldmanual.data.MAX_DESCRIPTION_LENGTH
import com.patron.fieldmanual.data.ManualDocument
import com.patron.fieldmanual.data.ManualIndexStore
import com.patron.fieldmanual.data.removeTxtSuffix
import com.patron.fieldmanual.data.sampleManualDocuments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LibraryViewModel(app: Application) : AndroidViewModel(app) {

    private val appContext = app.applicationContext
    private val store = ManualIndexStore(appContext)
    private val mutex = Mutex()
    private val state = MutableStateFlow(ManualIndexStore.Snapshot(emptyList(), emptyList()))

    val documents: StateFlow<List<ManualDocument>> = state
        .map { sampleManualDocuments + it.documents }
        .stateIn(viewModelScope, SharingStarted.Eagerly, sampleManualDocuments)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = store.load()
            rescanAll()
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) { rescanAll() }
    }

    fun addFiles(uris: List<Uri>, description: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val newDocs = uris.map { uri ->
                persistPermission(uri)
                ManualDocument(
                    id = uri.toString(),
                    title = ExternalTextReader.displayName(appContext, uri).removeTxtSuffix(),
                    description = description.take(MAX_DESCRIPTION_LENGTH),
                    source = "Added file",
                    location = DocumentLocation.External(uri.toString()),
                )
            }
            mutate { snap ->
                val existing = snap.documents.map { it.id }.toSet()
                snap.copy(documents = snap.documents + newDocs.filter { it.id !in existing })
            }
        }
    }

    fun addFolder(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            persistPermission(uri)
            val tree = uri.toString()
            val scanned = FolderScanner.scan(appContext, uri) ?: return@launch
            mutate { snap ->
                snap.copy(
                    treeUris = (snap.treeUris + tree).distinct(),
                    documents = merge(snap.documents, tree, scanned),
                )
            }
        }
    }

    fun updateDescription(id: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mutate { snap ->
                snap.copy(
                    documents = snap.documents.map {
                        if (it.id == id) it.copy(description = description.take(MAX_DESCRIPTION_LENGTH)) else it
                    }
                )
            }
        }
    }

    private suspend fun rescanAll() {
        for (tree in state.value.treeUris) {
            val scanned = FolderScanner.scan(appContext, Uri.parse(tree)) ?: continue
            mutate { snap -> snap.copy(documents = merge(snap.documents, tree, scanned)) }
        }
    }

    private fun merge(
        current: List<ManualDocument>,
        treeUri: String,
        scanned: List<ManualDocument>,
    ): List<ManualDocument> {
        val old = current.associateBy { it.id }
        val fresh = scanned.map { s -> old[s.id]?.copy(title = s.title) ?: s }
        val others = current.filterNot { (it.location as? DocumentLocation.External)?.treeUri == treeUri }
        return others + fresh
    }

    private suspend fun mutate(block: (ManualIndexStore.Snapshot) -> ManualIndexStore.Snapshot) {
        mutex.withLock {
            val next = block(state.value)
            state.value = next
            store.save(next)
        }
    }

    private fun persistPermission(uri: Uri) {
        try {
            appContext.contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        } catch (e: SecurityException) {
            // Not all providers allow persisting; the file may stop opening after a restart.
        }
    }
}