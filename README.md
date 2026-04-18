# TaskFlow - To-Do List App

An offline-first task management Android application built with Jetpack Compose and Material Design 3.

## Features

- **Create Tasks**: Add tasks with title, description, due date, and priority (High/Medium/Low)
- **Edit Tasks**: Modify existing tasks
- **Complete Tasks**: Mark tasks as completed with a checkbox
- **Delete Tasks**: Swipe to delete with undo functionality
- **Organized Views**: Tasks organized into Today, Upcoming, and Completed tabs
- **Search**: Search tasks by title or description

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material Design 3
- **Database**: Room (SQLite)
- **Architecture**: MVVM + Repository Pattern
- **State Management**: Kotlin Flows + StateFlow
- **Navigation**: Jetpack Navigation Compose

## Project Structure

```
app/src/main/java/com/taskflow/app/
├── data/
│   ├── local/           # Room database layer
│   │   ├── TaskEntity.kt
│   │   ├── TaskDao.kt
│   │   └── TaskDatabase.kt
│   └── repository/
│       └── TaskRepository.kt
├── domain/model/
│   └── Task.kt          # Domain model
├── ui/
│   ├── components/
│   │   └── TaskCard.kt  # Reusable task card
│   ├── navigation/
│   │   └── Navigation.kt
│   ├── screens/
│   │   ├── home/
│   │   ├── addedit/
│   │   └── settings/
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── MainActivity.kt
└── TaskFlowApplication.kt
```

## Requirements

- Android Studio Hedgehog or later
- Android SDK 34
- Kotlin 1.9.23
- Gradle 8.6+

## Building

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Run on emulator or device

## Testing

- Unit Tests: 14 tests covering Task model and Repository
- Instrumentation Tests: 18 tests covering UI flows

## License

MIT License
