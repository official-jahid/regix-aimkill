# Developer & AI Agent Guidelines for REGIX AIMKILL

This document provides technical instructions, structural architecture details, and operational rules for AI agents and developers working on the `regix-aimkill` repository.

---

## 1. Project Architecture & Stack

* **Package Name**: `com.regix.aimkill`
* **Language**: Java (Android SDK) + C++17 (Android NDK)
* **Build System**: Gradle 8.7 with Android Gradle Plugin (AGP) `8.2.2`
* **Java Version**: JDK 17 / JDK 21 (Source/Target Compatibility: Java 8)
* **Target SDK**: 34 (Android 14) | **Min SDK**: 19 (Android 4.4)
* **NDK Version**: `21.0.6113669` (`ndkBuild` via `app/src/main/jni/Android.mk`)
* **Supported ABIs**: `armeabi-v7a`, `arm64-v8a`

---

## 2. Directory Map

```
regix-aimkill/
├── app/
│   ├── build.gradle               # App module build script (Namespace, NDK, dependencies)
│   ├── libs/                      # External Java dependencies (classes.jar)
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml # Permissions, exported MainActivity, services
│           ├── java/com/regix/aimkill/
│           │   ├── MainActivity.java     # Entry point & animated splash UI
│           │   ├── Login.java            # Key/license input UI & overlay launcher
│           │   ├── Menu.java             # Floating overlay window UI
│           │   ├── DrawView.java         # Overlay canvas for ESP / graphics
│           │   ├── LicenseAuth.java      # Key validation logic
│           │   ├── AuthHelper.java       # Remote auth API communications
│           │   ├── IntegrityChecker.java # App verification checks
│           │   └── HWID.java             # Device HWID generation
│           ├── jni/                      # Native C++ source files
│           │   ├── Android.mk            # NDK build specification
│           │   ├── Application.mk        # NDK configuration (c++_static, STL)
│           │   ├── client.cpp            # libREGIX_MENU source
│           │   ├── backend.cpp           # libREGIX_CORE source
│           │   ├── AutoUpdate/           # il2cpp & xdl dynamic linker helpers
│           │   ├── Hack/                 # Memory offsets & class definitions
│           │   └── Tools/                # Substrate, Dobby, KittyMemory, Keystone
│           └── jniLibs/                  # Prebuilt native libraries organized by ABI
├── build.gradle                   # Root build script (AGP 8.2.2, mavenCentral)
├── settings.gradle                # Module inclusions (:app)
└── gradle.properties              # JVM & AndroidX properties
```

---

## 3. Build & Execution Rules

### Prerequisites
* JDK 17 or JDK 21 installed and configured in `JAVA_HOME`.
* Android SDK Platform 34 and Build-Tools `34.0.0` installed.
* Android NDK `21.0.6113669` located in `<SDK>/ndk/21.0.6113669`.

### Build Commands
* **Debug APK**:
  ```powershell
  .\gradlew assembleDebug
  ```
  *Output*: `app/build/outputs/apk/debug/app-debug.apk`

* **Release APK**:
  ```powershell
  .\gradlew assembleRelease
  ```
  *Output*: `app/build/outputs/apk/release/app-release-unsigned.apk`

* **Clean Build Environment**:
  ```powershell
  .\gradlew clean
  ```

---

## 4. Key Engineering Guidelines

### 1. Android Manifest Requirements
* All activities with `<intent-filter>` declarations MUST explicitly state `android:exported="true"`.
* Specify package/namespace in `app/build.gradle` (`namespace 'com.regix.aimkill'`), not in `AndroidManifest.xml`.

### 2. Native Library (JNI) Rules
* All native libraries must reside in valid ABI folders (`app/src/main/jniLibs/<abi>/`). Never place loose `.so` files directly under `jniLibs/`.
* When modifying `app/src/main/jni/Android.mk`, ensure all added source files are specified in `LOCAL_SRC_FILES`.

### 3. Overlay Window Permissions
* Floating window views (`Menu.java`, `DrawView.java`) require `android.permission.SYSTEM_ALERT_WINDOW`. Always verify `Settings.canDrawOverlays(context)` before attempting to spawn `WindowManager` views on Android 6.0+.

---

## 5. Security & Code Integrity

* **Obfuscation**: Native C++ strings are protected using `obfuscate.h`.
* **Integrity Checks**: `IntegrityChecker.java` provides checks against unauthorized dynamic tampering.
