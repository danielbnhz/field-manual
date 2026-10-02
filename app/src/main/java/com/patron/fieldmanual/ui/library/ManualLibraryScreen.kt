package com.patron.fieldmanual.ui.library

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.patron.fieldmanual.data.AssetManualReader
import com.patron.fieldmanual.data.ManualDocument
import com.patron.fieldmanual.ui.reader.ManualReaderScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualLibraryScreen(
    documents: List<ManualDocument>,
) {
    var selectedDocument by remember {
        mutableStateOf<ManualDocument?>(null)
    }

    val document = selectedDocument

    if (document == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Field Manual") },
                )
            },
        ) { innerPadding ->
            ManualDocumentList(
                documents = documents,
                onDocumentClick = { selected ->
                    selectedDocument = selected
                },
                modifier = Modifier.padding(innerPadding),
            )
        }
    } else {
        val context = LocalContext.current
        val text = AssetManualReader.readText(
            context = context,
            assetPath = document.assetPath,
        )

        ManualReaderScreen(
            document = document,
            documentText = text,
            onBack = {
                selectedDocument = null
            },
        )
    }
}