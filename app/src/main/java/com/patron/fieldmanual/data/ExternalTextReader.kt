package com.patron.fieldmanual.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.IOException

data class OpenedFile(val title: String, val text: String)

object ExternalTextReader {
    fun read(context: Context, uri: Uri): OpenedFile? {
        val text = try {
            context.contentResolver.openInputStream(uri)
                ?.bufferedReader(Charsets.UTF_8)
                ?.use { it.readText() }
        } catch (e: IOException) {
            null
        } ?: return null

        return OpenedFile(title = displayName(context, uri), text = text)
    }

    private fun displayName(context: Context, uri: Uri): String {
        context.contentResolver.query(
            uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) return cursor.getString(index)
            }
        }
        return "Untitled"
    }
}
