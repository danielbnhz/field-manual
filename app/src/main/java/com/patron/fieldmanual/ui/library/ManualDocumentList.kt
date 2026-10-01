package com.patron.fieldmanual.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patron.fieldmanual.data.ManualDocument
import com.patron.fieldmanual.ui.library.ManualDocumentItem
@Composable
fun ManualDocumentList(
    documents: List<ManualDocument>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = documents,
            key = { document -> document.id },
        ) { document ->
            ManualDocumentItem(document = document)
        }
    }
}