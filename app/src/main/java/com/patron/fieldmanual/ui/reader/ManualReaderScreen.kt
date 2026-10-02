package com.patron.fieldmanual.ui.reader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.patron.fieldmanual.data.ManualDocument

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualReaderScreen(
    document: ManualDocument,
    documentText: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(document.title)
                },
                navigationIcon = {
                    TextButton(
                        onClick = onBack,
                    ) {
                        Text("Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Text(
            text = documentText,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 26.sp,
        )
    }
}