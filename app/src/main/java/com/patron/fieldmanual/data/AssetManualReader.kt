package com.patron.fieldmanual.data

import android.content.Context

object AssetManualReader {

    fun readText(
        context: Context,
        assetPath: String,
    ): String {
        return context.assets
            .open(assetPath)
            .bufferedReader()
            .use { reader ->
                reader.readText()
            }
    }
}