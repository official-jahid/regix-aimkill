# REGIX AIMKILL

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Gradle](https://img.shields.io/badge/Gradle-8.7-blue.svg)
![AGP](https://img.shields.io/badge/AGP-8.2.2-orange.svg)
![NDK](https://img.shields.io/badge/NDK-21.0.6113669-red.svg)
![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg)

An advanced Android application featuring an interactive overlay menu, custom particle animations, and native C++ memory management built with Android SDK and NDK.

---

## 🚀 Features

* **Animated Splash Screen**: Custom red rain particle system (`RainView`) with staggered text animations.
* **Floating Overlay Window**: Draggable floating menu UI powered by Android `WindowManager` overlay service.
* **License & HWID Verification**: Built-in authentication mechanism with hardware ID binding (`HWID.java`).
* **High-Performance Native Core**:
  * Dual native C++ shared libraries (`libREGIX_MENU.so` and `libREGIX_CORE.so`).
  * Integrates **KittyMemory**, **Dobby Hook**, **Keystone Assembler**, and **xdl** dynamic linker tools.
* **Custom ESP Overlay Canvas**: Smooth 60FPS overlay rendering view (`DrawView.java`).

---

## 🛠️ Project Structure

```
regix-aimkill/
├── app/
│   ├── build.gradle               # App module Gradle configuration
│   ├── libs/                      # Dependency JAR files (classes.jar)
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml # Permissions & activity declarations
│           ├── java/com/regix/aimkill/
│           │   ├── MainActivity.java     # Entry point & splash screen
│           │   ├── Login.java            # Key authentication UI
│           │   ├── Menu.java             # Overlay floating menu
│           │   ├── DrawView.java         # Overlay drawing canvas
│           │   ├── LicenseAuth.java      # Auth handler
│           │   ├── AuthHelper.java       # Network communications
│           │   └── HWID.java             # HWID generator
│           ├── jni/                      # C++ Native NDK sources
│           │   ├── Android.mk            # NDK build file
│           │   ├── Application.mk        # NDK STL configuration
│           │   ├── client.cpp            # Menu native backend
│           │   ├── backend.cpp           # Core native engine
│           │   └── Tools/                # Hooking & memory tools
│           └── jniLibs/                  # Prebuilt native libraries
├── build.gradle                   # Root Gradle build script
└── gradle/wrapper/                # Gradle wrapper configuration
```

---

## 🧰 Tech Stack & Prerequisites

* **JDK**: Version 17 or 21
* **Android SDK**: Target SDK 34 / Minimum SDK 19
* **Android NDK**: Version `21.0.6113669`
* **Gradle**: 8.7
* **Android Gradle Plugin (AGP)**: 8.2.2

---

## 📦 How to Build

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/regix-aimkill.git
cd regix-aimkill
```

### 2. Build Debug APK
```bash
./gradlew assembleDebug
```
The output APK will be located at:
`app/build/outputs/apk/debug/app-debug.apk`

### 3. Build Release APK
```bash
./gradlew assembleRelease
```
The output APK will be located at:
`app/build/outputs/apk/release/app-release-unsigned.apk`

---

## 📄 License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.
