# JakMall NestedList Assignment

This project demonstrates a Jetpack Compose application architecture using the Model-View-ViewModel (MVVM) pattern, dependency injection with Hilt Dagger, and network communication with Retrofit.

## Architecture

This application follows the MVVM architecture to separate concerns and improve testability.

-   **View:** Implemented using Jetpack Compose, responsible for displaying the UI and handling user interactions.
-   **ViewModel:** Manages the UI-related data and logic, interacting with the data layer and exposing data to the View.
-   **Model (Data Layer):** Handles data retrieval and manipulation, including network requests and local storage.

## Technologies

-   **Jetpack Compose:** Modern UI toolkit for building native Android UI.
-   **MVVM (Model-View-ViewModel):** Architectural pattern for separating UI logic from business logic.
-   **Hilt Dagger:** Dependency injection library for Android, simplifying dependency management.
-   **Retrofit:** Type-safe HTTP client for Android and Java, used for network communication.

## Key Features

-   Clean architecture with separation of concerns.
-   Dependency injection using Hilt Dagger for improved testability and maintainability.
-   Network data fetching using Retrofit.
-   Data management and UI updates using ViewModel and Compose state.
-   UI components with enhanced functionality, including expandable items, swipe-to-refresh, and styled text.

## Dependencies

-   Jetpack Compose
-   Hilt Dagger
-   Retrofit
-   ViewModel
-   Coroutines

## How to Use

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/zakuby/jakmall-nestedlist
    ```

2.  **Open in Android Studio:**
    Open the project in Android Studio.

3.  **Build and Run:**
    Build and run the application on an emulator or physical device.
