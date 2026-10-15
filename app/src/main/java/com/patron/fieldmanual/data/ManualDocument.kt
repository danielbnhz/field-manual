package com.patron.fieldmanual.data

const val MAX_DESCRIPTION_LENGTH = 50
const val NO_DESCRIPTION_TEXT = "No description text is available"

sealed interface DocumentLocation {
    data class Asset(val path: String) : DocumentLocation
    data class External(val uri: String, val treeUri: String? = null) : DocumentLocation
}

data class ManualDocument(
    val id: String,
    val title: String,
    val description: String = "",
    val tags: List<String> = emptyList(),
    val source: String,
    val location: DocumentLocation,
)