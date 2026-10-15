package com.patron.fieldmanual.data

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile

object FolderScanner {

    fun scan(context: Context, treeUri: Uri): List<ManualDocument>? {
        val root = DocumentFile.fromTreeUri(context, treeUri) ?: return null
        if (!root.canRead()) return null

        val out = mutableListOf<ManualDocument>()
        walk(root, root.name ?: "Folder", treeUri.toString(), out)
        return out
    }

    private fun walk(
        dir: DocumentFile,
        folderName: String,
        treeUri: String,
        out: MutableList<ManualDocument>,
    ) {
        for (child in dir.listFiles()) {
            val name = child.name ?: continue
            when {
                child.isDirectory -> walk(child, folderName, treeUri, out)
                child.isFile && name.endsWith(".txt", ignoreCase = true) -> out += ManualDocument(
                    id = child.uri.toString(),
                    title = name.removeTxtSuffix(),
                    source = "Folder: $folderName",
                    location = DocumentLocation.External(child.uri.toString(), treeUri),
                )
            }
        }
    }
}

fun String.removeTxtSuffix(): String = removeSuffix(".txt").removeSuffix(".TXT")