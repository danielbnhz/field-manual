package com.patron.fieldmanual.ui.library

import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patron.fieldmanual.data.AssetManualReader
import com.patron.fieldmanual.data.DocumentLocation
import com.patron.fieldmanual.data.ExternalTextReader
import com.patron.fieldmanual.data.ManualDocument
import com.patron.fieldmanual.ui.reader.ManualReaderScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualLibraryScreen(
    viewModel: LibraryViewModel = viewModel(),
) {
    val context = LocalContext.current
    val documents by viewModel.documents.collectAsState()

    var selectedDocument by remember { mutableStateOf<ManualDocument?>(null) }
    var pendingSingleFile by remember { mutableStateOf<Uri?>(null) }
    var editingDocument by remember { mutableStateOf<ManualDocument?>(null) }

    val filePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        when {
            uris.isEmpty() -> Unit
            uris.size == 1 -> pendingSingleFile = uris.first()
            else -> viewModel.addFiles(uris)
        }
    }

    val folderPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri != null) viewModel.addFolder(uri)
    }

    val document = selectedDocument
    if (document != null) {
        val text by produceState<String?>(initialValue = null, key1 = document) {
            value = withContext(Dispatchers.IO) { loadText(context, document) }
        }
        BackHandler { selectedDocument = null }
        ManualReaderScreen(
            title = document.title,
            documentText = text ?: "Loading…",
            onBack = { selectedDocument = null },
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Field Manual") },
                    actions = {
                        TextButton(onClick = { filePicker.launch(arrayOf("text/plain")) }) {
                            Text("Add files")
                        }
                        TextButton(onClick = { folderPicker.launch(null) }) {
                            Text("Add folder")
                        }
                    },
                )
            },
        ) { innerPadding ->
            ManualDocumentList(
                documents = documents,
                onDocumentClick = { selectedDocument = it },
                onEditDescription = { editingDocument = it },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }

    pendingSingleFile?.let { uri ->
        DescriptionDialog(
            title = "Add a description",
            initialText = "",
            confirmLabel = "Add",
            skipLabel = "Skip",
            onConfirm = { viewModel.addFiles(listOf(uri), it); pendingSingleFile = null },
            onSkip = { viewModel.addFiles(listOf(uri)); pendingSingleFile = null },
        )
    }

    editingDocument?.let { doc ->
        DescriptionDialog(
            title = "Edit description",
            initialText = doc.description,
            confirmLabel = "Save",
            skipLabel = "Cancel",
            onConfirm = { viewModel.updateDescription(doc.id, it); editingDocument = null },
            onSkip = { editingDocument = null },
        )
    }
}

private fun loadText(context: Context, doc: ManualDocument): String = try {
    when (val loc = doc.location) {
        is DocumentLocation.Asset -> AssetManualReader.readText(context, loc.path)
        is DocumentLocation.External ->
            ExternalTextReader.read(context, Uri.parse(loc.uri))?.text
                ?: "Could not open this file. It may have been moved, or access to it was revoked."
    }
} catch (e: Exception) {
    "Could not open this file."
}