package com.patron.fieldmanual.ui.library

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.patron.fieldmanual.data.MAX_DESCRIPTION_LENGTH

@Composable
fun DescriptionDialog(
    title: String,
    initialText: String,
    confirmLabel: String,
    skipLabel: String,
    onConfirm: (String) -> Unit,
    onSkip: () -> Unit,
) {
    var text by remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onSkip,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { if (it.length <= MAX_DESCRIPTION_LENGTH) text = it },
                singleLine = true,
                supportingText = { Text("${text.length}/$MAX_DESCRIPTION_LENGTH") },
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text.trim()) }) { Text(confirmLabel) }
        },
        dismissButton = {
            TextButton(onClick = onSkip) { Text(skipLabel) }
        },
    )
}