# Field Manual

> An offline-first Android library for keeping useful reference material
> available, organized, and readable when a network connection is not.

Field Manual is a local reference-library application built with Kotlin
and Jetpack Compose.

Its purpose is simple:

> Keep important documents available on the device, present them clearly,
> and make them useful without requiring an account, a subscription, or a
> permanent internet connection.

The project begins as a small static library screen containing local
sample documents. It will grow deliberately from that stable foundation
rather than trying to solve importing, storage, search, rendering,
synchronization, sensors, and AI assistance in one opaque first version.

---

## Project stance

Field Manual is designed around a practical assumption:

> Reference material should remain available when connectivity is absent,
> unreliable, expensive, unwanted, or irrelevant.

An application can be modern without requiring a cloud account. It can
be useful without collecting activity data. It can improve over time
without making the user dependent on a remote service.

The application should eventually support a personal library of
documents and field references while keeping the user in control of
their data.

```text
Local material
    ↓
Clear library
    ↓
Readable document
    ↓
Useful reference
    ↓
No required network
```

---

## Governing principles

### Offline-first, not offline-only

The core library should be useful with no network connection.

A future network feature may help retrieve optional information, update
a source, or synchronize a deliberately chosen library. It must not be
required merely to open existing documents, read reference material, or
access saved bookmarks.

```text
No network
    ≠
No application
```

### The device belongs to the user

Field Manual should treat local content and user choices as belonging to
the user.

The application should not require an account for its basic purpose.
It should not make locally stored documents inaccessible because a
service is unavailable. It should not turn personal reference material
into a captive cloud product.

### Start with a useful library

The first successful version is intentionally modest:

- A recognizable Field Manual library screen.
- A short list of local sample documents.
- Clear titles, tags, and source information.
- A layout that works on a phone and remains comfortable on a tablet.
- No account.
- No server.
- No mandatory network access.
- No database before persistent storage is actually needed.

The first goal is not feature count. The first goal is a reliable,
legible, runnable application.

### Preserve provenance

Reference material is more valuable when its origin and review status
are visible.

A document should eventually be able to identify:

```text
Title
Source or author
Content type
Tags
Date added
Last reviewed date
Version or edition, when known
Local file or import origin
```

Field Manual should distinguish between:

```text
A document stored locally
    ≠
A document verified as current
    ≠
A document suitable for emergency guidance
```

The application can organize material. It must not silently imply that
every document is complete, current, authoritative, or safe for every
situation.

### Honest capability claims

Field Manual is a reference library, not an emergency-response system.

It must not claim to:

- Provide official emergency alerts.
- Replace local emergency services.
- Verify the medical, legal, safety, or operational correctness of
  imported material.
- Guarantee that stored instructions are current.
- Provide professional advice merely because information appears in the
  library.

A future emergency-oriented category can present vetted local reference
material, but it should remain explicit about its sources, limits, and
review status.

### Small milestones preserve clarity

Each stage of development should have one visible outcome that can be
built, run, understood, and committed.

```text
One goal
    ↓
One working increment
    ↓
One testable result
    ↓
One Git commit
```

This keeps the project understandable while its architecture grows.

### Local data before cloud synchronization

Cloud synchronization may eventually be useful. It is not the
foundation.

Before considering synchronization, Field Manual should establish:

- A clear local document model.
- A local storage format or database.
- Import and export behavior.
- Conflict expectations.
- User-visible ownership of data.
- A way to function completely without a remote service.

A cloud feature should extend a solid local library; it should not become
the only place the library exists.

### AI assistance must remain optional

AI may later help with optional tasks such as tagging, summarizing,
extracting metadata, suggesting related material, or assisting with
search.

AI-generated output must remain distinguishable from source material.
It should not silently rewrite or replace a user’s documents. It should
not make factual, medical, legal, safety, or emergency claims without
visible provenance and human review.

```text
Source material
    ≠
AI summary
    ≠
Verified instruction
```

---

## Current status

Field Manual is in its foundation stage.

The project currently has:

- A native Android project generated with Android Studio.
- Kotlin as its implementation language.
- Jetpack Compose and Material 3 as its UI foundation.
- A successful local Gradle build.
- A Git repository with `main` as its primary branch.
- A GitHub remote for durable project history.

The first application feature is now a static local library screen.

---

## First data model

The first screen uses a small in-memory representation of a manual
document.

```kotlin
data class ManualDocument(
    val id: String,
    val title: String,
    val tags: List<String>,
    val source: String,
    val lastReviewedDate: String? = null,
    val isBookmarked: Boolean = false
)
```

This model is intentionally simple.

It provides enough structure to render a useful library list while
avoiding premature commitment to a database schema, file format, cloud
service, PDF engine, or import pipeline.

The initial document list is hard-coded local sample data. It is a UI
foundation, not the final content-storage system.

---

## Planned development path

### Milestone 0 — Android foundation

**Status: complete**

```text
# Field Manual

> A local-first Android reference library for keeping personally selected
> documents organized, readable, and available without requiring a network
> connection, account, subscription, or cloud service.

Field Manual is a native Android application built with Kotlin and
Jetpack Compose.

Its immediate purpose is simple:

> Keep useful reference text on the device, present it as a clear local
> library, and allow the user to read it while offline.

The application begins with bundled demonstration manuals and grows one
working layer at a time. It does not begin by assuming a remote service,
user account, cloud synchronization, or permanent connectivity.

---

## Current capability

Field Manual currently provides a complete local reading loop:

```text
Bundled .txt file in app assets
    ↓
ManualDocument metadata links to the asset path
    ↓
Field Manual library displays document cards
    ↓
User selects a document card
    ↓
Android AssetManager opens the local text asset
    ↓
Kotlin reads the file into a String
    ↓
Jetpack Compose displays the document in a scrollable reader
    ↓
Back returns the user to the local library
```

The current application requires:

```text
No account
No network request
No cloud service
No subscription
No storage permission
No external server
```

This is a deliberately small, local-first foundation.

---

## Project stance

Field Manual is designed around a practical constraint:

> Reference material should remain available when connectivity is absent,
> unreliable, expensive, unwanted, or irrelevant.

A modern application does not need to make core information conditional
on a remote service.

```text
No network
    ≠
No application
```

The device belongs to the user. Locally packaged or later user-imported
material should remain accessible because it is present on the device,
not because a server remains available.

---

## Current feature flow

The current user-facing flow is:

```text
Launch application
    ↓
View Field Manual library
    ↓
Scroll local document cards
    ↓
Tap a document
    ↓
Read its bundled .txt file
    ↓
Scroll document text
    ↓
Tap Back
    ↓
Return to library
```

Each bundled manual is represented by `ManualDocument` metadata:

```kotlin
data class ManualDocument(
    val id: String,
    val title: String,
    val tags: List<String>,
    val source: String,
    val assetPath: String,
)
```

The metadata is separate from document content.

```text
ManualDocument
    ├─ id
    ├─ title
    ├─ tags
    ├─ source
    └─ assetPath
            ↓
assets/manuals/<document>.txt
            ↓
Document body text
```

This separation makes it possible to display a fast library list without
loading every document body at launch.

---

## Current architecture

```text
MainActivity
    ↓
FieldManualTheme
    ↓
ManualLibraryScreen
    ├─ selectedDocument == null
    │      ↓
    │   ManualDocumentList
    │      ↓
    │   ManualDocumentItem
    │      ↓
    │   User taps a card
    │
    └─ selectedDocument != null
           ↓
        AssetManualReader
           ↓
        Android AssetManager
           ↓
        assets/manuals/<file>.txt
           ↓
        ManualReaderScreen
           ↓
        Scrollable Compose Text
```

The app uses a small in-memory selection state rather than a navigation
library during this early stage:

```text
No selected document
    → show library

Selected document exists
    → show reader

Back pressed
    → clear selected document
    → show library
```

This keeps the first working reader flow understandable before adding
navigation graphs, ViewModels, databases, or persistence.

---

## Package layout

```text
app/src/main/
├── assets/
│   └──
```

The first milestone uses only the top portion:

```text
Hard-coded document list
    ↓
Compose library screen
    ↓
Rendered local interface
```

As the application needs durable state, the lower layers can be added
without rewriting the meaning of the screen.

---

## Project values

Field Manual favors:

- Local availability over mandatory connectivity.
- Clear provenance over implied authority.
- Small working milestones over speculative architecture.
- Accessible reading over visual novelty.
- User ownership over account dependency.
- Explicit capability boundaries over marketing claims.
- Durable Git history over unrepeatable changes.
- A calm, dependable library over an attention-seeking feed.

```text
Useful when disconnected.

Clear about what it knows.

Honest about what it cannot guarantee.

Built one dependable layer at a time.
```

---

## Status and scope

Field Manual is an early project under active construction.

The project will change as the first real screen reveals what the
application actually needs. This README records the direction and
constraints that should remain stable while implementation details
evolve.