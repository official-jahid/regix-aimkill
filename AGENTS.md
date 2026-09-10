# Developer & AI Agent Guidelines for REGIX AIMKILL

This document provides technical instructions, structural architecture details, operational rules, and setup procedures for AI agents and developers working on the `regix-aimkill` repository.

---

## 1. Project Architecture & Technical Stack

* **Application ID / Package Name**: `com.regix.aimkill`
* **Primary Languages**: Java (Android SDK) + C++17 (Android NDK)
* **Build System**: Gradle 8.7 with Android Gradle Plugin (AGP) `8.2.2`
* **Java Version**: JDK 17 / JDK 21 (Source & Target Compatibility: Java 8 / 1.8)
* **Target SDK**: 34 (Android 14) | **Min SDK**: 19 (Android 4.4) | **Compile SDK**: 34
* **NDK Version**: `21.0.6113669` (r21e)
* **NDK Build Engine**: `ndkBuild` configured via `app/src/main/jni/Android.mk` and `Application.mk`
* **Supported ABIs**: Primary target `armeabi-v7a` (with support for `arm64-v8a`)
* **Key Java Dependencies**:
  * Local JARs (`app/libs/classes.jar`)
  * `com.bytedance.android:shadowhook:1.1.1` (Android inline hook engine)

---

## 2. Repository Directory Structure

```
regix-aimkill/
├── .gitignore                     # Git ignore rules for Android Studio, NDK, Gradle & VS Code
├── AGENTS.md                      # AI agent and developer operational guide
├── README.md                      # Main project documentation and setup guide
├── LICENSE                        # GPL-3.0 License
├── build.gradle                   # Root Gradle project configuration
├── settings.gradle                # Gradle settings (includes ':app')
├── gradle.properties              # JVM memory & AndroidX flags
├── gradlew / gradlew.bat          # Gradle wrapper scripts
├── gradle/wrapper/                # Gradle wrapper JAR and properties (Gradle 8.7)
└── app/
    ├── build.gradle               # App module build script (NDK, SDK versions, dependencies)
    ├── proguard-rules.pro         # Proguard/R8 shrinking rules
    ├── libs/                      # Prebuilt Java JAR libraries (classes.jar)
    └── src/
        └── main/
            ├── AndroidManifest.xml # Permissions, activities, intent filters
            ├── java/com/regix/aimkill/
            │   ├── MainActivity.java     # Entry activity & splash screen UI
            │   ├── Login.java            # Key/License input UI & overlay window launcher
            │   ├── Menu.java             # Floating overlay window UI (WindowManager)
            │   ├── DrawView.java         # Overlay graphics canvas (ESP rendering)
            │   ├── ParticlesView.java    # Animated particle background effect
            │   ├── BarLoadingView.java   # Custom loading UI widget
            │   ├── SwitchStyle.java      # Custom UI switch styling component
            │   ├── CustomTypefaceSpan.java # Custom font renderer
            │   ├── LicenseAuth.java      # License key authentication handler
            │   ├── AuthHelper.java       # Remote API authentication communicator
            │   ├── IntegrityChecker.java # App integrity verification & anti-tamper
            │   ├── HWID.java             # Unique device hardware ID generator
            │   ├── ImageBase64.java      # Base64 image loader
            │   ├── ImageString.java      # Asset image string container
            │   ├── Utils.java            # General utility methods
            │   └── restart.java          # App process restart handler
            ├── jni/                      # Native C++ NDK sources
            │   ├── Android.mk            # NDK build specification file
            │   ├── Application.mk        # NDK STL and ABI configuration
            │   ├── client.cpp            # Source for libREGIX_MENU.so
            │   ├── backend.cpp           # Source for libREGIX_CORE.so
            │   ├── AutoUpdate/           # Dynamic il2cpp and linker tools
            │   │   ├── Il2Cpp.cpp / .h    # Il2Cpp memory resolver
            │   │   └── xdl/              # High-performance ELF dynamic linker
            │   ├── Hack/                 # Memory offsets & class definitions
            │   │   ├── Memory.h          # Memory read/write routines
            │   │   ├── class.h           # Native game class structures
            │   │   └── il2cpp.h          # Il2Cpp header bindings
            │   └── Tools/                # Native hooking, assembly & memory libraries
            │       ├── And64InlineHook/  # ARM64 inline hooking library
            │       ├── Canvas/           # Native ESP drawing tools & structs
            │       ├── Dobby/            # Dobby inline hook static library (libdobby.a)
            │       ├── DrawTools/        # Drawing helper classes
            │       ├── Includes/         # Logger, obfuscation (obfuscate.h), macros
            │       ├── KittyMemory/      # Memory patcher, scanner & Keystone assembler wrapper
            │       ├── SOCKET/           # IPC Socket server/client communication
            │       ├── Substrate/        # Cydia Substrate hooking engine
            │       └── Unity/            # Unity engine math headers (Vector2/3, Quaternion)
            └── jniLibs/                  # Prebuilt shared libraries organized by ABI
```

---

## 3. Native JNI Architecture & Shared Libraries

The native layer compiles two core shared libraries via `app/src/main/jni/Android.mk`:

1. **`libREGIX_MENU.so`**:
   * **Source Files**: `client.cpp`, `Tools/SOCKET/client.cpp`
   * **Libraries Linked**: `-llog`, `-landroid`, `-lGLESv2`
   * **Purpose**: Handles overlay UI state, client-side IPC socket communications, and rendering primitives.

2. **`libREGIX_CORE.so`**:
   * **Source Files**: `backend.cpp`, `AutoUpdate/Il2Cpp.cpp`, `AutoUpdate/xdl/*.c`, `Tools/KittyMemory/*.cpp`, `Tools/SOCKET/server.cpp`, `Tools/Substrate/*.cpp`
   * **Static Libraries Linked**: `libdobby.a`, `libkeystone.a`
   * **Libraries Linked**: `-llog`, `-landroid`, `-lGLESv2`
   * **Purpose**: Core game memory scanning, function hooking, assembly execution, and runtime patch management.

---

## 4. Build Rules & Execution Commands

### Environment Prerequisites
* **JDK**: JDK 17 or JDK 21 (configured in `JAVA_HOME`)
* **Android SDK**: Platform 34 (Android 14) and Build-Tools `34.0.0`
* **Android NDK**: Version `21.0.6113669` (r21e) located under `<ANDROID_SDK>/ndk/21.0.6113669`

### Build Commands

* **Build Debug APK**:
  * *Windows (PowerShell)*:
    ```powershell
    .\gradlew assembleDebug
    ```
  * *Linux / macOS*:
    ```bash
    ./gradlew assembleDebug
    ```
  * *Output Location*: `app/build/outputs/apk/debug/app-debug.apk`

* **Build Release APK**:
  * *Windows (PowerShell)*:
    ```powershell
    .\gradlew assembleRelease
    ```
  * *Linux / macOS*:
    ```bash
    ./gradlew assembleRelease
    ```
  * *Output Location*: `app/build/outputs/apk/release/app-release-unsigned.apk`

* **Clean Build Workspace**:
  ```bash
  ./gradlew clean
  ```

* **Force Refresh Dependencies & Sync**:
  ```bash
  ./gradlew --refresh-dependencies
  ```

---

## 5. Key Engineering Guidelines & Best Practices

### 1. Android Manifest Requirements
* Every activity declaring an `<intent-filter>` MUST explicitly set `android:exported="true"` (required for Android 12+ / Target SDK 31+).
* Do NOT declare `package` in `AndroidManifest.xml`. Package/Namespace MUST be defined strictly in `app/build.gradle` (`namespace 'com.regix.aimkill'`).

### 2. Native C++ Code Modifications
* String literals in native C++ files (`client.cpp`, `backend.cpp`) should use string obfuscation where sensitive (e.g. `AY_OBFUSCATE("string")` via `obfuscate.h`).
* When adding new C++ source files to `app/src/main/jni/`, you **MUST** update `LOCAL_SRC_FILES` in `app/src/main/jni/Android.mk`.
* Maintain C++17 compatibility (`-std=c++17`).

### 3. System Overlay Permissions (`SYSTEM_ALERT_WINDOW`)
* Floating menu views (`Menu.java`, `DrawView.java`) require `SYSTEM_ALERT_WINDOW` permission.
* Always check `Settings.canDrawOverlays(context)` before attempting to spawn floating `WindowManager` overlays on Android 6.0+ (API 23+).

---

## 6. AI Agent Operational Instructions

1. **File Modification Protocol**:
   * Always prefer targeted built-in file tools (`replace_file_content`, `multi_replace_file_content`, `write_file`) over shell scripts.
   * Never execute destructive shell operations or overwrite files via `sed` or `cat`.
2. **Context Preservation**:
   * Inspect existing JNI source files and Java files using `read_file` before suggesting structural refactoring.
3. **Verification**:
   * After making modifications to `build.gradle` or native JNI build files, run `./gradlew assembleDebug` to verify compilation.
