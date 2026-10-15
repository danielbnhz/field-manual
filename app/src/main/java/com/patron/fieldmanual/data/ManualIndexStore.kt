package com.patron.fieldmanual.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class ManualIndexStore(context: Context) {

    data class Snapshot(
        val documents: List<ManualDocument>,
        val treeUris: List<String>,
    )

    private val file = File(context.filesDir, "manual_index.json")

    fun load(): Snapshot {
        if (!file.exists()) return Snapshot(emptyList(), emptyList())
        return try {
            val root = JSONObject(file.readText())
            val docs = root.getJSONArray("documents")
            val trees = root.getJSONArray("trees")
            Snapshot(
                documents = List(docs.length()) { i ->
                    val d = docs.getJSONObject(i)
                    val tags = d.getJSONArray("tags")
                    ManualDocument(
                        id = d.getString("id"),
                        title = d.getString("title"),
                        description = d.getString("description"),
                        tags = List(tags.length()) { tags.getString(it) },
                        source = d.getString("source"),
                        location = DocumentLocation.External(
                            uri = d.getString("uri"),
                            treeUri = if (d.isNull("treeUri")) null else d.getString("treeUri"),
                        ),
                    )
                },
                treeUris = List(trees.length()) { trees.getString(it) },
            )
        } catch (e: Exception) {
            Snapshot(emptyList(), emptyList())
        }
    }

    fun save(snapshot: Snapshot) {
        val docs = JSONArray()
        snapshot.documents.forEach { d ->
            val loc = d.location as? DocumentLocation.External ?: return@forEach
            docs.put(
                JSONObject()
                    .put("id", d.id)
                    .put("title", d.title)
                    .put("description", d.description)
                    .put("tags", JSONArray(d.tags))
                    .put("source", d.source)
                    .put("uri", loc.uri)
                    .put("treeUri", loc.treeUri ?: JSONObject.NULL)
            )
        }
        val root = JSONObject()
            .put("documents", docs)
            .put("trees", JSONArray(snapshot.treeUris))

        val tmp = File(file.parentFile, "manual_index.json.tmp")
        tmp.writeText(root.toString())
        tmp.renameTo(file)
    }
}