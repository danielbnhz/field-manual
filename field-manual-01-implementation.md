# Field Manual — Milestone 1 Implementation Packet

## Goal

Replace the starter Android Compose UI with a local, static, scrollable
Field Manual library.

The application launches directly into a list of hard-coded demonstration
`ManualDocument` entries. This milestone establishes a visible offline
library interface only.

It does not import files, persist data, render full documents, perform
network requests, or make emergency-information claims.

## Implemented package layout

```text
app/src/main/java/com/patron/fieldmanual/
  MainActivity.kt
  data/
    ManualDocument.kt
    SampleManualDocuments.kt
  ui/
    library/
      ManualLibraryScreen.kt
      ManualDocumentList.kt
      ManualDocumentItem.kt
    theme/
      Color.kt
      Theme.kt
      Type.kt
```

The existing `ui.theme` package is Android Studio-generated Material 3
theme infrastructure and remains intact.

## Responsibilities

### `MainActivity.kt`

- Acts as the Android application entry point.
- Uses `setContent`.
- Keeps `enableEdgeToEdge()`.
- Applies the generated `FieldManualTheme`.
- Renders `ManualLibraryScreen`.
- Supplies `sampleManualDocuments`.
- Does not contain document-card UI, sample data, navigation, or storage logic.

The active root composition is:

```kotlin
FieldManualTheme {
    ManualLibraryScreen(
        documents = sampleManualDocuments,
    )
}
```

### `data/ManualDocument.kt`

Defines the first in-memory document model:

```kotlin
data class ManualDocument(
    val id: String,
    val title: String,
    val tags: List<String>,
    val source: String,
)
```

This model is intentionally small. It supports a readable static library
without prematurely committing to database IDs, document-body storage,
file imports, bookmarks, full provenance records, or cloud metadata.

### `data/SampleManualDocuments.kt`

Provides at least five local, hard-coded demonstration entries.

Sample entries must use clearly fictional or explicit demo-only source
labels such as `Field Manual demo content`. They must not claim that
medical, legal, safety, emergency, or professional reference material
has been verified.

### `ui/library/ManualLibraryScreen.kt`

Defines the first and only application screen.

- Uses a Material 3 `Scaffold`.
- Shows a top app bar titled `Field Manual`.
- Receives `documents: List<ManualDocument>`.
- Passes that list to `ManualDocumentList`.
- Does not add navigation, persistence, imports, networking, or stateful
  bookmark behavior.

### `ui/library/ManualDocumentList.kt`

Renders the document collection.

- Receives `documents: List<ManualDocument>`.
- Uses `LazyColumn`.
- Uses each document `id` as a stable item key.
- Provides readable list padding and spacing.
- Delegates each entry to `ManualDocumentItem`.

### `ui/library/ManualDocumentItem.kt`

Renders one document entry.

- Uses a Material 3 `Card`.
- Displays the document title.
- Displays the document source.
- Displays tags as readable text.
- Has no click behavior in Milestone 1.

## Implemented behavior

- The application builds and launches successfully.
- The app opens directly to the Field Manual library screen.
- The library displays local, hard-coded sample documents.
- Each card shows a title, source, and tags.
- The list is vertically scrollable.
- The app has no account requirement.
- The app makes no network request.
- The app requests no storage permission.
- The app has no database or cloud dependency.

## Acceptance criteria

Milestone 1 is complete when all of the following are true:

1. The project builds successfully in Android Studio.
2. The application launches on an Android emulator or physical device.
3. The top app bar displays `Field Manual`.
4. At least five hard-coded local `ManualDocument` cards render.
5. Each card visibly presents a title, source, and tags.
6. The list scrolls to the final document without a crash.
7. No account, network request, storage permission, database, import
   behavior, or persistent bookmark behavior is required.
8. The working increment is committed to Git.

## Explicitly deferred

The following capabilities are intentionally outside Milestone 1:

- Document-detail screen
- Navigation between screens
- Search and filtering
- Bookmark interaction
- Persistent bookmarks
- File import
- Local `.txt` support
- Markdown rendering
- PDF opening or PDF rendering
- Database storage and Room
- Local backup and export
- Networking
- Cloud synchronization
- RSS or source updates
- Sensors
- AI assistance
- Emergency alerts
- Medical, legal, safety, or professional advice claims
- Verification that stored or imported content is current or authoritative

## Manual test checklist

- Launch the application from Android Studio.
- Confirm the top app bar says `Field Manual`.
- Confirm at least five document cards appear.
- Confirm every card has a readable title, source, and tag text.
- Scroll to the final card.
- Confirm the app remains responsive and does not crash.
- Confirm no account dialog, storage permission dialog, or network-dependent
  content appears.
- Optionally rotate the emulator and confirm the interface remains readable.

## Completion record

Milestone 1 was implemented through a reviewed, artifact-based workflow:

```text
Bounded planning artifacts
    ↓
Human review and scope control
    ↓
Small Kotlin/Compose implementation
    ↓
Android Studio build
    ↓
Android emulator validation
    ↓
Git commit
```

The milestone establishes the first working Field Manual interface and
provides a stable foundation for later local-library interaction,
persistence, document-detail views, and real content support.