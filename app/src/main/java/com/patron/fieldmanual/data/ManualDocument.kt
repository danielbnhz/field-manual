package com.patron.fieldmanual.data

data class ManualDocument(
    val id: String,
    val title: String,
    val tags: List<String>,
    val source: String,
    val assetPath: String,
)