package com.patron.fieldmanual.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patron.fieldmanual.data.ManualDocument

@Composable
fun ManualDocumentList(
    documents: List<ManualDocument>,
    onDocumentClick: (ManualDocument) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = documents,
            key = { document -> document.id },
        ) { document ->
            ManualDocumentItem(
                document = document,
                onClick = {
                    onDocumentClick(document)
                },
            )
        }
    }
}