# Nav3Test

POC demonstrating navigation using Jetpack Compose and Navigation 3, focusing on state management, top/bottom bars, deep navigation, argument passing, and ViewModel injection with Koin.

## 🔍 Index

* [🌐 Project Description](#-project-description)
* [👁️ Screen Structure](#-screen-structure)
* [📊 Key Technologies and Libraries](#-key-technologies-and-libraries)
* [⚖️ Installation & Execution](#-installation--execution)
* [📹 Preview (video)](#-preview-video)
* [🖊️ To Do](#-to-do)
* [🎓 Key Concepts about Navigation 3](#-key-concepts-about-navigation-3)
* [📂 File Overview](#-file-overview)

## 🌐 Project Description

This is a proof of concept (POC) project showcasing the use of **Jetpack Navigation 3** with **Jetpack Compose**. It highlights key features such as:

* Using `NavHost` with the new Navigation 3 API.
* State handling with `rememberSaveable` and `@Parcelize`.
* Deep navigation with argument passing.
* Integration of `TopAppBar` and `BottomNavigationBar`.
* Dependency injection with **Koin**, instantiating ViewModels per screen.
* Organized structure with scenes: Home, Notes, and Settings.

## 👁️ Screen Structure

### Main Tabs:

* **Home**: Contains a profile section with deep navigation.
* **Notes**: Displays a list of notes, with navigation to note details.

### Others:

* **Settings**: Accessible via the global TopBar.

Each section maintains its own navigation stack, leveraging `rememberSaveable` with `@Parcelize` to preserve and restore UI state effectively.

## 📊 Key Technologies and Libraries

* **Jetpack Compose**
* **Navigation Compose 3**
* **Koin** for Dependency Injection and ViewModel management
* `@Parcelize` to serialize arguments and help with state saving
* `rememberSaveable` to retain state across recompositions

## ⚖️ Installation & Execution

1. Clone the repository:

```bash
git clone https://github.com/mfa645/Nav3Test.git
```

2. Open the project with Android Studio Flamingo or newer.

3. Sync the Gradle project and run it on an emulator or physical device.

## 📹 Preview (video)

Short video showcase of the POC 

<div align= "center">
  <video width="100" src="https://gist.github.com/user-attachments/assets/f3a066ac-17ce-44ec-b4b2-97dd34b0687c" alt ="">
</video>
  </div>

## 🎓 Key Concepts about Navigation 3

Navigation 3 is a modern, Compose-native navigation library that reshapes how navigation is handled in Android apps. Below are its essential principles and features:

### 1. 🔐 Developer-Owned Back Stack

Unlike Navigation 2, Navigation 3 gives **full control over the navigation stack**. You manage your own `SnapshotStateList<NavKey>` with `rememberNavBackStack()`, making it extremely flexible and customizable. Adding a screen is as simple as calling `.add(destination)`, and going back is `.removeLast()`.

### 2. 📄 Reference-Based Destinations

Destinations are not strings or URIs; they're **serializable data objects** (via `@Serializable` or `@Parcelize`). This provides:

* **Type safety** for arguments.
* **Compile-time guarantees**.
* Easy state preservation using `rememberSaveable`.

### 3. 🔄 Reactive UI with NavDisplay

`NavDisplay` is the central UI component. It observes the back stack and dynamically displays composables linked to each `NavEntry`.

* Each `NavEntry` is mapped via an `entryProvider` lambda.
* You can attach **metadata** to `NavEntry`, enabling behavior customization (e.g. for scene strategies).

### 4. 📊 Scoped State and ViewModels

With Navigation 3, each `NavEntry` can use **decorators** like `rememberSavedStateNavEntryDecorator()` and `rememberViewModelStoreNavEntryDecorator()`.

* This ensures ViewModels are created **per entry**, and automatically cleared when the entry is removed.
* Helps prevent memory leaks and improves lifecycle alignment.

### 5. 🌍 Scene and SceneStrategy

Navigation 3 introduces **Scenes** to enable multi-pane UIs. A `Scene` allows rendering **multiple navigation entries at once** (e.g., a list-detail layout).

* Use `SceneStrategy` to determine if and how scenes should be created.
* Enables adaptive UIs depending on screen size or other conditions.

### 6. 🚀 Metadata and Entry Extensibility

Each `NavEntry` can include metadata via key-value pairs. This metadata can drive:

* Scene layout decisions.
* UI animations.
* Logging or analytics tagging.

### 7. 🎨 Custom Animations

Navigation 3 supports custom transitions with `ContentTransform` and `transitionSpec`, `popTransitionSpec`, and `predictivePopTransitionSpec`, allowing refined animation control.

### 8. 🏢 DSL-Driven Entry Providers

`entryProvider { ... }` uses a DSL-style approach to register which composables should respond to each `NavKey`, keeping navigation declarations readable and concise.

---

## 📂 File Overview

### `NavigationRoot.kt`

Defines the app's main navigation structure using `Scaffold`. Integrates top and bottom bars, manages a navigation back stack with `rememberNavBackStack`, and dynamically renders screens based on the current destination.

### `Destination.kt`

Sealed interface representing all destinations/screens. Each screen (like `Home`, `NoteListScreen`, `NoteDetailScreen`, etc.) is defined as a serializable object or data class for state saving with `@Parcelize`.

### `TwoPaneScene.kt` and `TwoPaneSceneStrategy.kt`

Implements a custom scene and strategy for two-pane layouts. Used in `NoteGraph` to display both list and detail side-by-side on large screens.

### `HomeGraph.kt`

Contains the navigation logic for the Home section. Sets up a `NavDisplay` with entries like `Home`, `NameEditDialog`, and `NameEditScreen`.

### `NoteGraph.kt`

Defines the Notes section navigation. Uses `TwoPaneSceneStrategy` and supports note list/detail navigation. ViewModels are injected with Koin.

### `NoteDetailViewModel.kt`

ViewModel to fetch and expose the state of a selected note. Simulates network loading with delay and handles success/error cases using a `ResourceState` wrapper.

### `NoteDetailScreen.kt`

UI component for displaying the detailed view of a note. Reacts to `NoteState` changes and renders different UI states: loading, error, or success.

### `NoteListScreen.kt`

Displays a list of notes with lazy loading. Clicking a note triggers navigation to the corresponding detail screen.

### `ProfileScreen.kt`

Composable for the user's profile. Displays user info and provides navigation to screens to change name or photo. Demonstrates state retention with `rememberSaveable`.

---

Made with \:heart: using Kotlin and Jetpack Compose
