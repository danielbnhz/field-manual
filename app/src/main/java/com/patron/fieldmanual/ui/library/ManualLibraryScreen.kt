package com.patron.fieldmanual.ui.library

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.patron.fieldmanual.data.AssetManualReader
import com.patron.fieldmanual.data.ExternalTextReader
import com.patron.fieldmanual.data.ManualDocument
import com.patron.fieldmanual.data.OpenedFile
import com.patron.fieldmanual.ui.reader.ManualReaderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualLibraryScreen(
    documents: List<ManualDocument>,
) {
    val context = LocalContext.current

    var selectedDocument by remember { mutableStateOf<ManualDocument?>(null) }
    var openedFile by remember { mutableStateOf<OpenedFile?>(null) }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            openedFile = ExternalTextReader.read(context, uri)
        }
    }

    val file = openedFile
    val document = selectedDocument

    when {
        file != null -> {
            ManualReaderScreen(
                title = file.title,
                documentText = file.text,
                onBack = { openedFile = null },
            )
        }

        document != null -> {
            val text = remember(document) {
                AssetManualReader.readText(
                    context = context,
                    assetPath = document.assetPath,
                )
            }
            ManualReaderScreen(
                title = document.title,
                documentText = text,
                onBack = { selectedDocument = null },
            )
        }

        else -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Field Manual") },
                        actions = {
                            TextButton(
                                onClick = { picker.launch(arrayOf("text/plain")) },
                            ) {
                                Text("Open file")
                            }
                        },
                    )
                },
            ) { innerPadding ->
                ManualDocumentList(
                    documents = documents,
                    onDocumentClick = { selectedDocument = it },
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}