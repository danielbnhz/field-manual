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
Android Studio project created
    ↓
Kotlin and Jetpack Compose configured
    ↓
Gradle build succeeds
    ↓
Git repository initialized
    ↓
Baseline pushed to GitHub
```#### Milestone 1 acceptance criteria

Milestone 1 is complete when:

1. The application builds and launches successfully from Android Studio.
2. Launching the app opens directly to the Field Manual library screen.
3. The screen renders at least five hard-coded local `ManualDocument` entries.
4. Each entry presents a readable title, source, and one or more tags.
5. The document collection is vertically scrollable on a typical phone-sized screen.
6. The screen uses no network requests, account system, database, file import,
   cloud synchronization, or persistent bookmark behavior.
7. The completed increment is manually tested and committed to Git.

Milestone 1 intentionally does not open documents, import files, persist
state, or provide search. It establishes the local library interface first.

### Milestone 2 — Library interaction

Add small interactions that work entirely in memory.

Potential work:

- Search and filtering across titles, tags, and sources.
- A deliberate empty-state message.
- In-memory bookmark toggling.
- Basic document-detail navigation.
- Better phone and tablet layout behavior.

At this stage, interaction matters more than storage permanence.

### Milestone 3 — Durable local library

Add persistence only when the static/in-memory screen has proven what
needs to be stored.

Potential work:

- Local structured storage for document metadata.
- Persistent bookmarks.
- A clear distinction between bundled sample content and user content.
- Application restart behavior.
- Local backup/export design.

A Room database may be appropriate here for document records and
bookmarks. The decision should follow the actual data requirements, not
arrive as premature architecture.

### Milestone 4 — Real content support

Allow Field Manual to contain material beyond hard-coded sample entries.

Potential work:

- Import selected files.
- Support a defined initial content format.
- Render Markdown or another deliberately selected format.
- Launch compatible external viewers for documents such as PDFs.
- Preserve source and import metadata.
- Add document-detail views.

Content support should be introduced one format at a time.

### Milestone 5 — Larger-screen field use

Refine the application for tablets and practical reading.

Potential work:

- Responsive layouts.
- A two-pane library-and-detail view on suitable screens.
- Larger touch targets.
- Better typography for prolonged reading.
- Accessibility review.
- Orientation and offline-use testing.

### Milestone 6 — Optional extensions

Only after the local library is solid should Field Manual consider
optional additions such as:

- Deliberate cloud synchronization.
- RSS or selected-source updates.
- Device sensor integrations.
- AI-assisted metadata, tagging, summaries, or local search.
- Carefully sourced specialist collections.

These features must extend the local-first core rather than replace it.

---

## Explicit deferrals

The following are intentionally **not** part of the first library
screen:

| Deferred capability | Why it waits |
|---|---|
| File import | Requires a clear content policy, format decision, metadata handling, and error states |
| Markdown rendering | Requires an intentional renderer, styling policy, and link/security behavior |
| PDF opening | Requires a choice between an in-app viewer and a compatible external viewer |
| Room database | The first static screen does not yet prove the final persistent data shape |
| Cloud sync | Local ownership, export, conflict behavior, and privacy expectations come first |
| RSS | A source-selection and update policy should exist before adding network retrieval |
| Sensors | Must correspond to a real field-use case rather than exist as a novelty |
| AI features | Must be optional, attributable, constrained, and clearly separated from source material |
| Emergency-alert claims | Requires official integrations, jurisdictional scope, reliability guarantees, and careful safety review |

Deferral does not mean rejection.

It means a capability must earn its complexity by following a stable,
working foundation.

---

## Architecture direction

Field Manual begins small and should remain understandable.

```text
User action
    ↓
Compose screen
    ↓
Screen state and application logic
    ↓
Document repository
    ↓
Local data source
    ↓
Persistent storage or local files
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