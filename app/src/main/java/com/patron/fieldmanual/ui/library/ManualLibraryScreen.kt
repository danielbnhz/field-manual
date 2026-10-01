package com.patron.fieldmanual.ui.library

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.patron.fieldmanual.data.ManualDocument
import com.patron.fieldmanual.ui.library.ManualDocumentList
import com.patron.fieldmanual.ui.library.ManualDocumentItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualLibraryScreen(
    documents: List<ManualDocument>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Field Manual") },
            )
        },
    ) { innerPadding ->
        ManualDocumentList(
            documents = documents,
            modifier = Modifier.padding(innerPadding),
        )
    }
}