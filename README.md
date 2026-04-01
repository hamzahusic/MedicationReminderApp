# MedPulse - Medication Reminder App

A mobile health application built with Jetpack Compose that helps users manage their medication schedules, track adherence, and stay on top of their health routines.

---

## Features

- **Authentication** — Register and log in with email and password
- **Medication Management** — Add, view, edit, and delete medications
- **Dosage & Scheduling** — Set dosage amounts and reminder times (24-hour format)
- **Home Dashboard** — View upcoming medications and adherence statistics at a glance
- **Medication Details** — Mark medications as taken, edit, or delete them
- **Adherence History** — Browse past medication history by date with a week calendar selector

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.2.10 |
| UI Framework | Jetpack Compose + Material Design 3 |
| Architecture | MVVM + Clean Architecture |
| Build System | Gradle 9.1.0 (KTS) |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |
| Java Version | 11 |

### Key Dependencies

- `androidx.compose.bom` — Jetpack Compose BOM (2024.09.00)
- `androidx.compose.material3` — Material 3 UI components
- `androidx.lifecycle:lifecycle-runtime-ktx` — Lifecycle-aware components
- `androidx.activity:activity-compose` — Compose Activity integration

---

## Project Structure

```
app/src/main/java/com/example/medicationreminderapp/
├── MainActivity.kt
├── data/
│   ├── di/                  # Dependency injection
│   ├── repository/          # Data repositories
│   ├── service/             # Data sources / services
│   └── util/                # Data utilities
└── presentation/
    ├── theme/               # Colors, typography, Material 3 theme
    ├── navigation/          # Navigation logic
    ├── view_model/          # ViewModels (state management)
    ├── util/                # Presentation utilities
    └── ui/
        ├── components/      # Reusable composables
        └── screens/
            ├── home/
            ├── login/
            ├── register/
            ├── medications/
            ├── add_medication/
            ├── medication_details/
            └── history/
```

---

## Architecture

The app follows **MVVM (Model-View-ViewModel)** with **Clean Architecture** principles:

- **Presentation Layer** — Compose screens, ViewModels, navigation
- **Data Layer** — Repositories, services, and dependency injection

State flows from ViewModels down to composables. UI events flow back up through ViewModel functions, keeping the UI layer stateless.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11 or later
- Android SDK 36

### Clone the Repository

```bash
git clone https://github.com/hamzahusic/MedicationReminderApp.git
cd MedicationReminderApp
```

### Build and Run

Open the project in Android Studio and click **Run**, or use the Gradle wrapper from the terminal:

```bash
# Build the project
./gradlew build

# Build a debug APK
./gradlew assembleDebug

# Build a release APK
./gradlew assembleRelease
```

The debug APK will be output to:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Screens

| Screen | Description |
|---|---|
| Login | Sign in with email and password |
| Register | Create a new account |
| Home | Dashboard with adherence stats and upcoming medications |
| My Medications | Full list of active medications |
| Add Medication | Form to add a new medication with dosage and time |
| Medication Details | View details, mark as taken, edit, or delete |
| History | Browse past adherence by date |

---

## Design

The app uses a **dark theme** by default with a Material 3 color scheme:

- **Primary:** Light Blue (`#60A5FA`)
- **Background:** Dark Navy (`#0D1421`)
- **Surface:** Slate (`#1E293B`)
- **Taken (success):** Green (`#4ADE80`)
- **Missed (error):** Red (`#F87171`)

A light theme is defined and available to enable.

---

## License

This project is for educational purposes.
