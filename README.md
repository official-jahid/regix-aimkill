# REGIX AIMKILL

![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android)
![Gradle](https://img.shields.io/badge/Gradle-8.7-blue.svg?style=for-the-badge&logo=gradle)
![AGP](https://img.shields.io/badge/AGP-8.2.2-orange.svg?style=for-the-badge&logo=android)
![NDK](https://img.shields.io/badge/NDK-21.0.6113669-red.svg?style=for-the-badge&logo=cplusplus)
![JDK](https://img.shields.io/badge/JDK-17%20%7C%2021-brightgreen.svg?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg?style=for-the-badge)

An advanced Android application featuring an interactive floating overlay menu, custom particle animations, real-time ESP canvas drawing, and a high-performance C++ native memory management core built with Android SDK and NDK.

---

## 📖 Table of Contents

- [Features & Highlights](#-features--highlights)
- [Tech Stack & System Requirements](#-tech-stack--system-requirements)
- [Project Architecture & Directory Map](#-project-architecture--directory-map)
- [Prerequisites Setup Guide](#-prerequisites-setup-guide)
  - [1. JDK Download & Environment Configuration](#1-jdk-download--environment-configuration)
  - [2. Android SDK & NDK Installation](#2-android-sdk--ndk-installation)
  - [3. Setting up local.properties](#3-setting-up-localproperties)
- [IDE Setup Guides](#-ide-setup-guides)
  - [Option A: Android Studio Setup](#option-a-android-studio-setup-recommended)
  - [Option B: Visual Studio Code (VS Code) Setup](#option-b-visual-studio-code-vs-code-setup)
- [Dependencies & Installation Commands](#-dependencies--installation-commands)
- [Build Commands](#-build-commands)
- [Troubleshooting & Common Fixes](#-troubleshooting--common-fixes)
- [License](#-license)

---

## 🚀 Features & Highlights

* **Animated Splash & Particle UI**: Red rain particle animation system (`ParticlesView.java`) with custom staggered loading text and custom loading bars (`BarLoadingView.java`).
* **Interactive Floating Overlay Menu**:
  * Draggable overlay interface powered by Android `WindowManager` overlay service (`Menu.java`).
  * Custom styled switch controls (`SwitchStyle.java`) and custom typeface rendering.
* **Real-Time ESP Canvas**: Smooth 60FPS overlay rendering canvas (`DrawView.java`) interacting with native graphics structs.
* **High-Performance Native C++ Core**:
  * Dual shared libraries compiled via NDK `ndkBuild`: `libREGIX_MENU.so` and `libREGIX_CORE.so`.
  * Integrated **KittyMemory** memory scanning, patching, and reading/writing process memory.
  * **Dobby Hook** and **Substrate** inline function hooking framework.
  * **Keystone Assembler** for dynamic ARM/ARM64 runtime assembly generation.
  * **xdl** high-performance ELF dynamic linker for symbol resolution.
  * Native IPC socket server/client system for inter-process communication between menu and core.
* **Security & Authentication System**:
  * Hardware ID generator (`HWID.java`) for device binding.
  * Online key/license validation (`LicenseAuth.java`, `AuthHelper.java`).
  * Anti-tampering and integrity checker (`IntegrityChecker.java`).

---

## 🛠️ Tech Stack & System Requirements

| Component | Required Version / Specification |
| :--- | :--- |
| **Operating System** | Windows 10/11 (64-bit), macOS 12+, or Linux (Ubuntu 20.04+) |
| **Java Development Kit (JDK)** | JDK 17 or JDK 21 (Java 8 compatibility mode) |
| **Android SDK** | Compile SDK **34** / Target SDK **34** / Minimum SDK **19** |
| **Android NDK** | Version `21.0.6113669` (r21e) |
| **Build System** | Gradle **8.7** with Android Gradle Plugin (AGP) **8.2.2** |
| **Native Compiler** | Clang C++17 (`ndkBuild` engine) |
| **Supported Target ABIs** | `armeabi-v7a` (32-bit ARM), `arm64-v8a` (64-bit ARM) |

---

## 📂 Project Architecture & Directory Map

```
regix-aimkill/
├── .gitignore                     # Git ignore rules for Android Studio, NDK & VS Code
├── AGENTS.md                      # AI agent & developer guidelines
├── README.md                      # Project documentation and setup guide
├── LICENSE                        # GPL-3.0 License
├── build.gradle                   # Root Gradle build script
├── settings.gradle                # Gradle settings (includes ':app')
├── gradle.properties              # JVM arguments & AndroidX flags
├── gradlew / gradlew.bat          # Gradle wrapper scripts for Linux/macOS & Windows
├── .vscode/                       # VS Code workspace settings, launch & tasks configurations
│   ├── settings.json
│   ├── tasks.json
│   └── launch.json
└── app/
    ├── build.gradle               # App module build script (Namespace, NDK, dependencies)
    ├── proguard-rules.pro         # Proguard rules
    ├── libs/                      # External Java dependencies (classes.jar)
    └── src/
        └── main/
            ├── AndroidManifest.xml # Permissions & activity declarations
            ├── java/com/regix/aimkill/
            │   ├── MainActivity.java     # Entry point & animated splash UI
            │   ├── Login.java            # Key/license authentication UI
            │   ├── Menu.java             # Floating overlay window UI
            │   ├── DrawView.java         # Overlay ESP canvas rendering
            │   ├── ParticlesView.java    # Particle background effect
            │   ├── BarLoadingView.java   # Custom loading UI widget
            │   ├── SwitchStyle.java      # Custom switch component
            │   ├── LicenseAuth.java      # Auth handler
            │   ├── AuthHelper.java       # Remote server API communication
            │   ├── IntegrityChecker.java # App verification checks
            │   └── HWID.java             # Device HWID generation
            ├── jni/                      # Native C++ NDK source files
            │   ├── Android.mk            # NDK build specification file
            │   ├── Application.mk        # NDK STL configuration (c++_static)
            │   ├── client.cpp            # Menu native backend source (libREGIX_MENU.so)
            │   ├── backend.cpp           # Engine native core source (libREGIX_CORE.so)
            │   ├── AutoUpdate/           # il2cpp & xdl dynamic linker helpers
            │   ├── Hack/                 # Memory offsets & class definitions
            │   └── Tools/                # Substrate, Dobby, KittyMemory, Keystone, SOCKET
            └── jniLibs/                  # Prebuilt native libraries organized by ABI
```

---

## ⚙️ Prerequisites Setup Guide

Follow this step-by-step guide to configure your environment before opening or building the project.

### 1. JDK Download & Environment Configuration

Gradle 8.7 and AGP 8.2.2 require **JDK 17** or **JDK 21**.

#### Download JDK
* **OpenJDK 17 / 21**: Download from [Eclipse Temurin (Adoptium)](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/).

#### Setting Up `JAVA_HOME`

##### Windows:
1. Open **Start Menu** -> Search **"Edit the system environment variables"**.
2. Click **Environment Variables...**.
3. Under **System variables**, click **New...**:
   * **Variable name**: `JAVA_HOME`
   * **Variable value**: `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot` (or your JDK installation folder path).
4. Select the `Path` variable under **System variables**, click **Edit...**, click **New**, and add `%JAVA_HOME%\bin`.
5. Open a new Command Prompt or PowerShell and verify:
   ```cmd
   java -version
   javac -version
   ```

##### macOS:
1. Edit your shell profile (`~/.zshrc` or `~/.bash_profile`):
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   export PATH=$JAVA_HOME/bin:$PATH
   ```
2. Reload shell configuration: `source ~/.zshrc`
3. Verify: `java -version`

##### Linux (Ubuntu/Debian):
1. Install JDK 17:
   ```bash
   sudo apt update
   sudo apt install openjdk-17-jdk -y
   ```
2. Set `JAVA_HOME` in `~/.bashrc`:
   ```bash
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
   export PATH=$JAVA_HOME/bin:$PATH
   ```
3. Reload shell configuration: `source ~/.bashrc`

---

### 2. Android SDK & NDK Installation

This project specifically requires **Android SDK Platform 34** and **Android NDK version `21.0.6113669` (r21e)**.

#### Installing via Android Studio SDK Manager:
1. Open Android Studio -> **Tools** -> **SDK Manager**.
2. Under **SDK Platforms**:
   * Check **Android 14.0 ("UpsideDownCake") / API Level 34**.
3. Under **SDK Tools**:
   * Check **Show Package Details** at the bottom right.
   * Expand **Android SDK Build-Tools** -> Check **34.0.0**.
   * Expand **NDK (Side by side)** -> Check version **`21.0.6113669`**.
   * Check **Android SDK Command-line Tools (latest)**.
   * Check **CMake**.
4. Click **Apply** and wait for download and installation to complete.

#### Direct NDK Download (Alternative):
If NDK `21.0.6113669` is not visible in your SDK manager, download Android NDK r21e directly from the [Android NDK Revision History](https://developer.android.com/ndk/downloads/revision_history) and extract it to `<ANDROID_SDK>/ndk/21.0.6113669`.

---

### 3. Setting up `local.properties`

Create a file named `local.properties` in the root directory of the project (`D:/GitHub/regix-aimkill/local.properties`).

Add the paths to your Android SDK and NDK installations:

#### Windows Example:
```properties
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
ndk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk\\ndk\\21.0.6113669
```

#### macOS Example:
```properties
sdk.dir=/Users/YourUsername/Library/Android/sdk
ndk.dir=/Users/YourUsername/Library/Android/sdk/ndk/21.0.6113669
```

#### Linux Example:
```properties
sdk.dir=/home/YourUsername/Android/Sdk
ndk.dir=/home/YourUsername/Android/Sdk/ndk/21.0.6113669
```

---

## 💻 IDE Setup Guides

You can build and develop this project using either **Android Studio** or **Visual Studio Code (VS Code)**.

---

### Option A: Android Studio Setup (Recommended)

1. **Download & Install**: Download the latest version of [Android Studio](https://developer.android.com/studio).
2. **Open Project**:
   * Launch Android Studio.
   * Click **Open**.
   * Navigate to the project folder `regix-aimkill` and click **OK**.
3. **Configure Gradle JDK**:
   * Go to **Settings** (or **Preferences** on macOS) -> **Build, Execution, Deployment** -> **Build Tools** -> **Gradle**.
   * Under **Gradle JDK**, select **JDK 17** or **JDK 21**.
4. **Sync Project**:
   * Click **File** -> **Sync Project with Gradle Files** (or the elephant icon in the top toolbar).
   * Ensure the build output window shows `BUILD SUCCESSFUL`.
5. **Run & Debug**:
   * Connect an Android device with USB Debugging enabled (or start an emulator).
   * Select `app` in the run configuration dropdown and click **Run** (`Shift + F10`).

---

### Option B: Visual Studio Code (VS Code) Setup

1. **Download & Install**: Download [VS Code](https://code.visualstudio.com/).
2. **Install Required VS Code Extensions**:
   Open VS Code Extensions view (`Ctrl+Shift+X` or `Cmd+Shift+X`) and install:
   * 📦 **Extension Pack for Java** (`vscjava.vscode-java-pack`)
   * 🐘 **Gradle for Java** (`vscjava.vscode-gradle`)
   * ⚡ **C/C++** (`ms-vscode.cpptools`)
   * 🛠️ **C/C++ Extension Pack** (`ms-vscode.cpptools-extension-pack`)
   * 📱 **Android Tools** (or **Android**)

3. **Configure Workspace Settings**:
   The workspace comes pre-configured with `.vscode/settings.json`, `.vscode/tasks.json`, and `.vscode/launch.json`. Ensure your `JAVA_HOME` environment variable is set so VS Code detects Java automatically.

4. **Open Workspace**:
   * Open VS Code -> **File** -> **Open Folder...** -> Select `regix-aimkill`.

5. **Building the App from VS Code**:
   * Press `Ctrl+Shift+B` (or `Cmd+Shift+B` on macOS) to run the default build task (`Gradle: assembleDebug`).
   * Alternatively, press `Ctrl+Shift+P` -> type `Tasks: Run Task` -> select `Gradle: assembleDebug`.

6. **Installing to Device via VS Code**:
   * Press `Ctrl+Shift+P` -> `Tasks: Run Task` -> select `ADB Install Debug APK`.

---

## 📥 Dependencies & Installation Commands

All dependencies are defined in `build.gradle` and `app/build.gradle`.

### Key Dependencies Summary
* `com.android.tools.build:gradle:8.2.2` (AGP)
* `com.bytedance.android:shadowhook:1.1.1` (Android inline hook engine)
* Prebuilt JAR files in `app/libs/classes.jar`
* Native NDK C++ dependencies: Keystone, Dobby Hook, Substrate, KittyMemory, xdl.

### Fetching & Refreshing Dependencies
Run the following command to download and verify all required dependencies:

#### Windows (PowerShell / CMD):
```powershell
.\gradlew dependencies --refresh-dependencies
```

#### Linux / macOS:
```bash
./gradlew dependencies --refresh-dependencies
```

---

## 🔨 Build Commands

Execute build commands using the provided Gradle Wrapper (`gradlew` or `gradlew.bat`).

### 1. Build Debug APK
Compiles the application with debug symbols and output:

#### Windows (PowerShell):
```powershell
.\gradlew assembleDebug
```

#### Linux / macOS:
```bash
./gradlew assembleDebug
```
*Output location*: `app/build/outputs/apk/debug/app-debug.apk`

---

### 2. Build Release APK
Compiles an optimized release APK:

#### Windows (PowerShell):
```powershell
.\gradlew assembleRelease
```

#### Linux / macOS:
```bash
./gradlew assembleRelease
```
*Output location*: `app/build/outputs/apk/release/app-release-unsigned.apk`

---

### 3. Clean Project Workspace
Removes build artifacts, cached native outputs, and intermediate files:

```bash
./gradlew clean
```

---

### 4. Install APK on Connected Device via ADB
Make sure your Android device is connected via USB with Developer Options and USB Debugging enabled:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 🐛 Troubleshooting & Common Fixes

### Error 1: `NDK at ... did not have a version matching 21.0.6113669`
* **Cause**: The Android NDK version `21.0.6113669` is not installed in your SDK directory.
* **Fix**:
  1. Open Android Studio -> **SDK Manager** -> **SDK Tools**.
  2. Check **Show Package Details** -> Expand **NDK (Side by side)**.
  3. Select version **`21.0.6113669`** and click **Apply**.
  4. Ensure your `local.properties` file points to the installed NDK version:
     `ndk.dir=<SDK_LOCATION>/ndk/21.0.6113669`

---

### Error 2: `JAVA_HOME is set to an invalid directory` or Incompatible Java Version
* **Cause**: `JAVA_HOME` points to Java 8 or an incorrect Java installation directory.
* **Fix**:
  1. Install JDK 17 or JDK 21.
  2. Update `JAVA_HOME` environment variable to point to your JDK 17/21 installation folder.
  3. Verify by running `java -version`.
  4. In Android Studio, go to **Settings -> Build, Execution, Deployment -> Build Tools -> Gradle** and set **Gradle JDK** to JDK 17 or JDK 21.

---

### Error 3: `SDK location not found. Define location with an ANDROID_SDK_ROOT environment variable`
* **Cause**: `local.properties` file is missing or contains an invalid path.
* **Fix**:
  1. Create a `local.properties` file in the project root directory.
  2. Add the path to your Android SDK:
     ```properties
     sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
     ```

---

### Error 4: Floating Menu Overlay Fails to Appear / Permission Crash
* **Cause**: On Android 6.0+ (API 23+), `SYSTEM_ALERT_WINDOW` permission must be explicitly granted by the user at runtime.
* **Fix**:
  1. Go to Android Device **Settings** -> **Apps** -> **REGIX AIMKILL**.
  2. Enable **Display over other apps** (or "Draw over other apps").
  3. Re-launch the app.

---

### Error 5: Native Compilation Failure or Unresolved JNI Symbols
* **Cause**: NDK build artifacts or cached `.cxx` output are corrupted or out of sync.
* **Fix**:
  1. Clean the build directory:
     ```bash
     ./gradlew clean
     ```
  2. Delete the `.cxx` folder located inside `app/` if present.
  3. Sync Gradle and rebuild:
     ```bash
     ./gradlew assembleDebug
     ```

---

### Error 6: Gradle Daemon Lock or Memory Error
* **Cause**: Gradle daemon process is hanging or out of memory.
* **Fix**:
  1. Stop all Gradle daemons:
     ```bash
     ./gradlew --stop
     ```
  2. Re-run the build command.

---

## 📄 License

This project is open source and released under the **[GNU General Public License v3.0 (GPL-3.0)](LICENSE)**.
