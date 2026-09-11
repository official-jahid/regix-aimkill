# REGIX AIMKILL

![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android)
![Gradle](https://img.shields.io/badge/Gradle-8.7-blue.svg?style=for-the-badge&logo=gradle)
![AGP](https://img.shields.io/badge/AGP-8.2.2-orange.svg?style=for-the-badge&logo=android)
![NDK](https://img.shields.io/badge/NDK-21.0.6113669-red.svg?style=for-the-badge&logo=cplusplus)
![JDK](https://img.shields.io/badge/JDK-17%20%7C%2021-brightgreen.svg?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg?style=for-the-badge)

**English** | [বাংলা (Bengali)](README_BN.md) | [हिंदी (Hindi)](README_HI.md) | [العربية (Arabic)](README_AR.md)

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
- [Authentication Configuration Setup Guide](#-authentication-configuration-setup-guide)
- [Logos & Icons Customization Guide](#-logos--icons-customization-guide)
- [Complete Brand Name & Package Rebranding Guide](#-complete-brand-name--package-rebranding-guide)
- [Memory Offsets & AOB Pattern Scanning Guide](#-memory-offsets--aob-pattern-scanning-guide)
- [Troubleshooting & Common Fixes](#-troubleshooting--common-fixes)
- [License Information](#-license-information)

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
            │   │   ├── Il2Cpp.cpp        # Dynamic il2cpp method & field resolver
            │   │   └── Il2Cppp.h        # Il2Cpp data structures & headers
            │   ├── Hack/                 # Memory offsets & class definitions
            │   │   ├── class.h           # Offsets and method definitions
            │   │   ├── Memory.h          # Memory base and address calculators
            │   │   └── il2cpp.h          # Il2Cpp definitions
            │   └── Tools/                # Substrate, Dobby, KittyMemory, Keystone, SOCKET
            │       ├── KittyMemory/      # KittyScanner (AOB), MemoryPatch, KittyUtils
            │       ├── Dobby/            # Dobby inline hooking engine
            │       └── Substrate/        # Cydia Substrate hooking engine
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
$env:ANDROID_PREFS_ROOT=$null; .\gradlew.bat assembleDebug
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
$env:ANDROID_PREFS_ROOT=$null; .\gradlew.bat assembleRelease
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

## 🔐 Authentication Configuration Setup Guide

This project includes a dual-tier remote license authentication system (`LicenseAuth.java` and `AuthHelper.java`) paired with hardware ID binding (`HWID.java`) and anti-tamper verification (`IntegrityChecker.java`).

### 1. LicenseAuth Server Credentials Configuration
Configure your key authentication provider credentials in `Login.java`:

* **File Path**: [`app/src/main/java/com/regix/aimkill/Login.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Login.java#L64-L69)
  * **Line 64**: `private static final String APPNAME = "regix aimkill";`
  * **Line 65**: `private static final String OWNERID = "RTgStl6UQK";`
  * **Line 66**: `private static final String SECRET = "edf522cb0077ae716c23b64d0f8f7f12b96db899b2be04a657c274ec86a5fac1";`
  * **Line 67**: `private static final String VERSION = "1.0";`
  * **Line 68**: `private static final String[] ALLOWED_PACKAGES = { "REGIXCOVER" };`
  * **Line 69**: `private static final String LICENSEAUTH_URL = "https://licenseauth.help/api/1.3/";`

* **File Path**: [`app/src/main/java/com/regix/aimkill/LicenseAuth.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/LicenseAuth.java#L30-L82)
  * **Lines 30–38**: `LicenseAuth` constructor and session key initialization.
  * **Line 53**: Handles `"LicenseAuth_Invalid"` response parsing.
  * **Lines 79–82**: Encodes and appends the device HWID (`&hwid=`) to validation requests.

---

### 2. Custom Remote Auth API Configuration (AuthHelper)
To connect the application to a custom web API endpoint:

* **File Path**: [`app/src/main/java/com/regix/aimkill/AuthHelper.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/AuthHelper.java#L27-L28)
  * **Line 27**: Set `VALIDATE_URL` to your custom authentication PHP/Node API:
    ```java
    private static final String VALIDATE_URL = "https://your-custom-domain.com/api/validate.php";
    ```
  * **Line 28**: Set `SECRET_KEY` for API request signing:
    ```java
    private static final String SECRET_KEY = "YourCustomSecretKey123";
    ```
  * **Line 197**: Custom `User-Agent` HTTP header configuration (`Android-AuthHelper/2.0`).
  * **Lines 281 & 419**: Hardware ID (`hwid`) payload insertion into JSON requests.

---

### 3. Hardware ID (HWID) Fingerprinting Logic
* **File Path**: [`app/src/main/java/com/regix/aimkill/HWID.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/HWID.java#L11-L25)
  * **Line 15**: `getHWID()` returns a SHA-256 hexadecimal string generated from device parameters (`Build.BOARD`, `Build.BRAND`, `Build.DEVICE`, `Build.HARDWARE`, `Build.MODEL`).

---

### 4. Integrity Checker & Anti-Tamper Signatures
To enforce APK signature verification and detect unauthorized modifications:

* **File Path**: [`app/src/main/java/com/regix/aimkill/IntegrityChecker.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/IntegrityChecker.java#L91-L418)
  * **Lines 91–92**: Signature Hash validation (`EXPECTED_SIGNATURE_HASH`).
  * **Lines 151–152**: Executable DEX file hash check (`EXPECTED_DEX_HASH`).
  * **Lines 317–319**: Individual APK asset files hash check (`EXPECTED_APK_FILES_HASH`).
  * **Lines 414–418**: Overall APK File Hash and File Size validation (`EXPECTED_APK_HASH`, `EXPECTED_APK_SIZE`).
  * *First Run Workflow*: Run the app once in debug mode, copy the generated hashes from Logcat, and paste them into `IntegrityChecker.java`.

---

## 🎨 Logos & Icons Customization Guide

Follow this guide to update all visual assets, icons, splash screen elements, and native floating menu graphics.

### 1. App Launcher Icon
To change the app icon displayed on the Android home screen:

* **File Path**: [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L12)
  * **Line 12**: Specified by `android:icon="@mipmap/ic_launcher"`
* **Resource File Directories**:
  Replace the `ic_launcher.png` image files in the following density directories:
  * `app/src/main/res/mipmap-hdpi/ic_launcher.png` (72x72)
  * `app/src/main/res/mipmap-mdpi/ic_launcher.png` (48x48)
  * `app/src/main/res/mipmap-xhdpi/ic_launcher.png` (96x96)
  * `app/src/main/res/mipmap-xxhdpi/ic_launcher.png` (144x144)
  * `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png` (192x192)
  * `app/src/main/ic_launcher-playstore.png` (512x512)

---

### 2. Native Floating Menu Base64 Icon (C++)
The floating overlay menu displays a Base64-encoded PNG image embedded directly in the native shared library:

* **File Path**: [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp#L39-L40)
  * **Lines 39–40**:
    ```cpp
    Java_com_regix_aimkill_Menu_imageBase64(JNIEnv *env, jclass thiz) {
        return env->NewStringUTF(OBFUSCATE("iVBORw0KGgoAAAANSUhEUgAA..."));
    }
    ```
  * *How to update*: Convert your new 200x200 PNG logo to a Base64 string and replace the string inside `OBFUSCATE("...")`.

* **File Path**: [`app/src/main/jni/icon.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/icon.h#L15)
  * **Line 15**: Header declaration for `Java_com_regix_aimkill_Menu_imageBase64`.

---

### 3. Java Base64 UI Image Containers
* **File Path**: [`app/src/main/java/com/regix/aimkill/ImageString.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/ImageString.java#L5)
  * **Line 5**: `public String icon_image = "...";` (Paste Base64 encoded PNG here).
* **File Path**: [`app/src/main/java/com/regix/aimkill/ImageBase64.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/ImageBase64.java#L16-L23)
  * **Lines 16–23**: Decodes Base64 strings into `Bitmap` instances for `ImageView` UI elements.

---

### 4. Splash Screen & Animated UI Canvas Components
* **File Path**: [`app/src/main/java/com/regix/aimkill/ParticlesView.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/ParticlesView.java#L10-L60)
  * Custom particle rain effect canvas (modify particle colors, speed, and density).
* **File Path**: [`app/src/main/java/com/regix/aimkill/BarLoadingView.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/BarLoadingView.java#L20-L50)
  * Custom progress loading bar drawing logic (modify color palette and border radii).

---

## 🏷️ Complete Brand Name & Package Rebranding Guide

This section provides a complete step-by-step guide to rename the app brand and refactor the Java package `com.regix.aimkill` to a new package (e.g. `com.yourbrand.app`).

### Step 1: Update Application ID & Namespace (Gradle)
* **File Path**: [`app/build.gradle`](file:///D:/GitHub/regix-aimkill/app/build.gradle#L4-L12)
  * **Line 4**: Update namespace:
    ```groovy
    namespace 'com.yourbrand.app'
    ```
  * **Line 12**: Update application ID:
    ```groovy
    applicationId 'com.yourbrand.app'
    ```

---

### Step 2: Update Android Manifest & Resource Strings
* **File Path**: [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L13-L20)
  * **Line 13**: Change app label: `android:label="YOUR BRAND NAME"`
  * **Line 20**: Change activity FQN: `android:name="com.yourbrand.app.MainActivity"`
* **File Path**: [`app/src/main/res/values/strings.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/res/values/strings.xml#L2)
  * **Line 2**: `<string name="app_name">YOUR BRAND NAME</string>`
* **File Path**: [`app/src/main/res/layout/activity_main.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/res/layout/activity_main.xml#L8)
  * **Line 8**: `tools:context="com.yourbrand.app.MainActivity"`
* **File Path**: [`app/proguard-rules.pro`](file:///D:/GitHub/regix-aimkill/app/proguard-rules.pro#L26)
  * **Line 26**: `-keep public class com.yourbrand.app.MainActivity`

---

### Step 3: Refactor Java Package Directory & Class Headers
1. Rename directory `app/src/main/java/com/regix/aimkill/` to `app/src/main/java/com/yourbrand/app/`.
2. Update **Line 1** (`package com.yourbrand.app;`) across all 18 Java source files:
   * [`AuthHelper.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/AuthHelper.java#L1) (Line 1)
   * [`BarLoadingView.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/BarLoadingView.java#L1) (Line 1)
   * [`CustomTypefaceSpan.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/CustomTypefaceSpan.java#L1) (Line 1)
   * [`DrawView.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/DrawView.java#L1) (Line 1)
   * [`FakeLibsReferences.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/FakeLibsReferences.java#L3) (Line 3)
   * [`HWID.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/HWID.java#L1) (Line 1)
   * [`ImageBase64.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/ImageBase64.java#L1) (Line 1)
   * [`ImageString.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/ImageString.java#L1) (Line 1)
   * [`IntegrityChecker.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/IntegrityChecker.java#L1) (Line 1)
   * [`LicenseAuth.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/LicenseAuth.java#L1) (Line 1)
   * [`Login.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Login.java#L1) (Line 1)
   * [`MainActivity.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/MainActivity.java#L1) (Line 1)
   * [`Menu.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Menu.java#L1) (Line 1)
   * [`ParticlesView.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/ParticlesView.java#L1) (Line 1)
   * [`SwitchStyle.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/SwitchStyle.java#L1) (Line 1)
   * [`Utils.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Utils.java#L1) (Line 1)
   * [`restart.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/restart.java#L1) (Line 1)

---

### Step 4: Update Java In-App Branding Strings
* **File Path**: [`app/src/main/java/com/regix/aimkill/Login.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Login.java#L64-L448)
  * **Line 64**: `private static final String APPNAME = "YOUR BRAND NAME";`
  * **Lines 118 & 448**: `String brandShort = "YOURBRAND";`
  * **Line 363**: `String subscription = "YOUR BRAND COVER";`
* **File Path**: [`app/src/main/java/com/regix/aimkill/MainActivity.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/MainActivity.java#L104)
  * **Line 104**: `String brandShort = "YOURBRAND";`
* **File Path**: [`app/src/main/java/com/regix/aimkill/Menu.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Menu.java#L299-L1782)
  * **Line 299**: `String brandShort = "YOURBRAND";`
  * **Lines 1772 & 1782**: SharedPreferences name `"YOURBRAND_Config"`

---

### Step 5: Update Native C++ JNI Functions & FindClass Calls
JNI function names MUST match the new package path (`Java_com_yourbrand_app_Menu_...`):

* **File Path**: [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp)
  * **Line 28**: `Java_com_yourbrand_app_Menu_getMatchTimerStatus`
  * **Line 39**: `Java_com_yourbrand_app_Menu_imageBase64`
  * **Line 113**: `Java_com_yourbrand_app_Menu_Init`
  * **Line 121**: `Java_com_yourbrand_app_Menu_Functions`
  * **Line 170**: `Java_com_yourbrand_app_Menu_ChangesID`
  * **Line 441**: `env->FindClass("com/yourbrand/app/Menu")`
  * **Line 450**: `Java_com_yourbrand_app_Menu_LockPlayer`
  * **Line 456**: `Java_com_yourbrand_app_Menu_getEnemyList`
  * **Line 477**: `Java_com_yourbrand_app_Menu_OnDrawLoad`
* **File Path**: [`app/src/main/jni/icon.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/icon.h#L15)
  * **Line 15**: `Java_com_yourbrand_app_Menu_imageBase64`
* **Widget Header Files (`env->FindClass`)**:
  * [`app/src/main/jni/Widgets/Category.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Widgets/Category.h#L5) (**Line 5**)
  * [`app/src/main/jni/Widgets/ImportWidgets.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Widgets/ImportWidgets.h#L34) (**Lines 34, 40, 50**)
  * [`app/src/main/jni/Widgets/SeekBar.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Widgets/SeekBar.h#L5) (**Line 5**)
  * [`app/src/main/jni/Widgets/Switch.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Widgets/Switch.h#L5) (**Line 5**)
  * [`app/src/main/jni/Widgets/Tab.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Widgets/Tab.h#L5) (**Line 5**)

---

### Step 6: Native Shared Library Renaming (Optional)
If renaming the compiled `.so` libraries (e.g. from `libREGIX_MENU.so` / `libREGIX_CORE.so` to `libYOURBRAND_MENU.so` / `libYOURBRAND_CORE.so`):

1. Update NDK Build Specs:
   * **File Path**: [`app/src/main/jni/Android.mk`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Android.mk#L11-L40)
     * **Line 11**: `LOCAL_MODULE := YOURBRAND_MENU`
     * **Line 40**: `LOCAL_MODULE := YOURBRAND_CORE`
2. Update Java Native Loading Calls:
   * [`MainActivity.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/MainActivity.java#L26) (**Line 26**): `System.loadLibrary("YOURBRAND_MENU");`
   * [`Menu.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Menu.java#L223) (**Line 223**): `System.loadLibrary("YOURBRAND_MENU");`
   * [`IntegrityChecker.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/IntegrityChecker.java#L33) (**Line 33**): `System.loadLibrary("YOURBRAND_MENU");`
   * [`restart.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/restart.java#L15) (**Line 15**): `System.loadLibrary("YOURBRAND_MENU");`
   * [`Menu.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Menu.java#L535-L537) (**Lines 535 & 537**): `InjectX32("libYOURBRAND_CORE.so");` / `InjectX86("libYOURBRAND_CORE.so");`

---

### Step 7: C++ Log Tags & Core Branding Strings
* **File Path**: [`app/src/main/jni/backend.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/backend.cpp#L69-L7035)
  * **Lines 69–72**: Log file path (`/sdcard/DCIM/yourbrand_log.txt`)
  * **Line 116**: Logcat Tag: `__android_log_print(ANDROID_LOG_INFO, "YOURBRAND", ...)`
  * **Line 874**: Menu title string: `"[b][FF0000]YOURBRAND [ffffff] CORE[B][C]"`
  * **Line 5353**: Overlay title text: `sprintf(buffer, " YOURBRAND - %s [ %d ] ", ...)`
  * **Line 7035**: IPC greeting token: `"YOURBRAND_READY"`
* **File Path**: [`app/src/main/jni/AutoUpdate/Il2Cpp.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/AutoUpdate/Il2Cpp.cpp#L8)
  * **Line 8**: `#define g_LogTag "Created By YOURBRAND"`
* **File Path**: [`app/src/main/jni/Unity/Unity.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Unity/Unity.h#L9)
  * **Line 9**: `#define LOG_TAG "www.yourbrand.shop"`

---

## 🎯 Memory Offsets & AOB Pattern Scanning Guide

This section explains how memory offsets, Array of Bytes (AOB) pattern scanning, dynamic Il2Cpp method resolvers, and C++ memory patches are implemented in this codebase, and how to update them.

### 1. Dynamic Il2Cpp Method & Field Resolvers (Auto-Update Engine)
Instead of relying strictly on hardcoded memory addresses, the C++ native core uses an automatic Il2Cpp metadata resolver (`AutoUpdate/Il2Cpp.cpp` and `Hack/class.h`).

* **Header File**: [`app/src/main/jni/AutoUpdate/Il2Cppp.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/AutoUpdate/Il2Cppp.h)
* **Resolver Implementation**: [`app/src/main/jni/AutoUpdate/Il2Cpp.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/AutoUpdate/Il2Cpp.cpp#L97-L298)
* **Offset Definition File**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L15-L60)

#### Resolving Method Offsets Dynamically:
In `app/src/main/jni/Hack/class.h`:
```cpp
// Syntax: Il2CppGetMethodOffset("Image.dll", "Namespace", "ClassName", "MethodName", ParameterCount)
#define offset_WeaponOnHand (uintptr_t) Il2CppGetMethodOffset( \
    OBFUSCATE("Assembly-CSharp.dll"), \
    OBFUSCATE("COW.GamePlay"), \
    OBFUSCATE("Player"), \
    OBFUSCATE("GetWeaponOnHand"), \
    0)
```

#### Resolving Field Offsets Dynamically:
```cpp
// Syntax: Il2CppGetFieldOffset("Image.dll", "Namespace", "ClassName", "FieldName")
#define offset_NoReload (uintptr_t) Il2CppGetFieldOffset( \
    OBFUSCATE("Assembly-CSharp.dll"), \
    OBFUSCATE("COW.GamePlay"), \
    OBFUSCATE("PlayerAttributes"), \
    OBFUSCATE("ShootNoReload"))
```

---

### 2. Updating Hardcoded Static Offsets
If the auto-resolver is disabled or a target function is obfuscated without metadata names, update static hex offsets in `class.h` and `Memory.h`:

* **Target Shared Library**: [`app/src/main/jni/Hack/Memory.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/Memory.h#L8-L101)
  * **Line 8**: `const char* libName = OBFUSCATE("libil2cpp.so");`
  * **Line 101**: `getRealOffset(DWORD address)` calculates `libBase + address`.

* **Static Offsets List**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L274-L1081)
  * **Line 274**: `ToString`: `getRealOffset(0x8A64560)`
  * **Line 275**: `EncryptFunc`: `getRealOffset(0x6ACA1F4)`
  * **Line 277**: `il2cpp_new_object`: `getRealOffset(0x430D95C)`
  * **Line 280**: `List_ctor`: `getRealOffset(0x821E9B0)`
  * **Line 282**: `listKlassPtr`: `getRealOffset(0x991B294)`
  * **Line 1081**: `fn`: `getRealOffset(0x61E9DA8)`

*How to update*: Extract the new offset hex values using IDA Pro / Ghidra / Il2CppDumper and replace the hex value inside `getRealOffset(0xHEX)`.

---

### 3. AOB Pattern Scanning Guide (`KittyScanner`)
To scan memory for byte patterns (Array of Bytes) dynamically instead of using fixed offsets, use the built-in `KittyScanner` tool:

* **Scanner Header**: [`app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp#L15-L60)
* **Scanner Implementation**: [`app/src/main/jni/Tools/KittyMemory/KittyScanner.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/KittyScanner.cpp)

#### IDA-Style AOB Pattern Search Example:
```cpp
#include "Tools/KittyMemory/KittyScanner.hpp"
#include "Tools/KittyMemory/KittyMemory.hpp"

// 1. Get memory map of target library
ProcMap targetLibMap = KittyMemory::getElfBaseMap("libil2cpp.so");

// 2. Scan for IDA pattern with wildcards (??)
uintptr_t foundAddress = KittyScanner::findIDAFirst(targetLibMap, "2D E9 F0 4F 85 B0 00 AF ?? ?? ?? ??");

if (foundAddress != 0) {
    // AOB match found at runtime!
    LOGI("AOB Pattern found at: %p", (void*)foundAddress);
}
```

#### Byte & Mask AOB Search Example:
```cpp
// Search using byte array and mask (x = match, ? = wildcard)
uintptr_t match = KittyScanner::findBytesFirst(
    targetLibMap.startAddr,
    targetLibMap.endAddr,
    "\x2D\xE9\xF0\x4F\x85\xB0",
    "xxxxxx"
);
```

---

### 4. C++ Memory Patching Guide (`MemoryPatch`)
To write hex patches or NOP instructions directly into game memory:

* **Patch Header**: [`app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp#L20-L50)
* **Patch Implementation**: [`app/src/main/jni/Tools/KittyMemory/MemoryPatch.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/MemoryPatch.cpp)

#### Hex Patching Example:
```cpp
#include "Tools/KittyMemory/MemoryPatch.hpp"

// NOP out instructions (00 BF = ARM NOP) at a target offset
MemoryPatch patch = MemoryPatch::createWithHex(getRealOffset(0x123456), "00 BF 00 BF");

// Apply memory patch
patch.Modify();

// Restore original unpatched bytes when feature is toggled off
patch.Restore();
```

---

### 5. Inline Function Hooking Guide (`Dobby` & `Substrate`)
To hook native functions in C++:

* **Dobby Static Library**: [`app/src/main/jni/Tools/Dobby/libdobby.a`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/Dobby/libdobby.a)
* **Substrate Header**: [`app/src/main/jni/Tools/Substrate/CydiaSubstrate.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/Substrate/CydiaSubstrate.h)

#### Hooking Example (`backend.cpp`):
```cpp
// Target function signature
void (*old_PlayerFire)(void *instance, int weaponId);

void my_PlayerFire_Hook(void *instance, int weaponId) {
    // Custom logic before execution
    LOGI("Player Fire Triggered: Weapon ID = %d", weaponId);
    
    // Call original function
    old_PlayerFire(instance, weaponId);
}

// In initialization method:
DobbyHook((void*)offset_WeaponOnHand, (void*)my_PlayerFire_Hook, (void**)&old_PlayerFire);
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
* **Cause**: NDK build artifacts or cached `.cxx` output are corrupted or out of sync after renaming packages.
* **Fix**:
  1. Clean the build directory:
     ```bash
     ./gradlew clean
     ```
  2. Delete the `.cxx` folder located inside `app/` if present.
  3. Sync Gradle and rebuild:
     ```bash
     $env:ANDROID_PREFS_ROOT=$null; .\gradlew.bat assembleDebug
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

## 📄 License Information

This project is open-source software distributed under the terms of the **[GNU General Public License v3.0 (GPL-3.0)](LICENSE)**.

### Summary of Rights and Conditions
* 🟢 **Commercial Use**: You are permitted to use this software commercially.
* 🟢 **Modification**: You may modify, refactor, rebrand, or adapt the source code.
* 🟢 **Distribution**: You may distribute original or modified copies of the software.
* ⚠️ **Source Disclosures**: If you distribute a modified version of this software, you **must** make your modified source code available under the same GPL-3.0 license.
* ⚠️ **License & Copyright Preservation**: All copies must retain the original copyright notice and GPL-3.0 license text.
