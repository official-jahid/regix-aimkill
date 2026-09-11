# REGIX AIMKILL (বাংলা সংস্করণ)

![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android)
![Gradle](https://img.shields.io/badge/Gradle-8.7-blue.svg?style=for-the-badge&logo=gradle)
![AGP](https://img.shields.io/badge/AGP-8.2.2-orange.svg?style=for-the-badge&logo=android)
![NDK](https://img.shields.io/badge/NDK-21.0.6113669-red.svg?style=for-the-badge&logo=cplusplus)
![JDK](https://img.shields.io/badge/JDK-17%20%7C%2021-brightgreen.svg?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg?style=for-the-badge)

[English](README.md) | **বাংলা** | [Hindi](README_HI.md) | [Arabic](README_AR.md)

একটি উন্নত অ্যান্ড্রয়েড অ্যাপ্লিকেশন যাতে রয়েছে ইন্টারেক্টিভ ভাসমান ওভারলে মেনু, কাস্টম পার্টিকেল অ্যানিমেশন, রিয়েল-টাইম ESP ক্যানভাস অঙ্কন, এবং Android SDK ও NDK দ্বারা নির্মিত উচ্চ-ক্ষমতাসম্পন্ন C++ নেটিভ মেমরি ম্যানেজমেন্ট কোর।

---

## 📖 সূচিপত্র

- [বৈশিষ্ট্য ও হাইলাইটসমূহ](#-বৈশিষ্ট্য-ও-হাইলাইটসমূহ)
- [টেক স্ট্যাক ও সিস্টেমের প্রয়োজনীয়তা](#-টেক-স্ট্যাক-ও-সিস্টেমের-প্রয়োজনীয়তা)
- [প্রজেক্ট আর্কিটেকচার ও ডিরেক্টরি ম্যাপ](#-প্রজেক্ট-আর্কিটেকচার-ও-ডিরেক্টরি-ম্যাপ)
- [পূর্বশর্ত সেটআপ গাইড](#-পূর্বশর্ত-সেটআপ-গাইড)
  - [১. JDK ডাউনলোড ও এনভায়রনমেন্ট কনফিগারেশন](#১-jdk-ডাউনলোড-ও-এনভায়রনমেন্ট-কনফিগারেশন)
  - [২. Android SDK ও NDK ইনস্টলেশন](#২-android-sdk-ও-ndk-ইনস্টলেশন)
  - [৩. local.properties কনফিগারেশন](#৩-localproperties-কনফিগারেশন)
- [IDE সেটআপ গাইড](#-ide-সেটআপ-গাইড)
  - [অপশন A: Android Studio সেটআপ](#অপশন-a-android-studio-সেটআপ-সুপারিশকৃত)
  - [অপশন B: Visual Studio Code (VS Code) সেটআপ](#অপশন-b-visual-studio-code-vs-code-সেটআপ)
- [ডিপেনডেন্সি ও ইনস্টলেশন কমান্ড](#-ডিপেনডেন্সি-ও-ইনস্টলেশন-কমান্ড)
- [বিল্ড কমান্ডসমূহ](#-বিল্ড-কমান্ডসমূহ)
- [অথেন্টিকেশন কনফিগারেশন সেটআপ গাইড](#-অথেন্টিকেশন-কনফিগারেশন-সেটআপ-গাইড)
- [লোগো ও আইকন কাস্টমাইজেশন গাইড](#-লোগো-ও-আইকন-কাস্টমাইজেশন-গাইড)
- [সম্পূর্ণ ব্র্যান্ড নাম ও প্যাকেজ রিব্র্যান্ডিং গাইড](#-সম্পূর্ণ-ব্র্যান্ড-নাম-ও-প্যাকেজ-রিব্র্যান্ডিং-গাইড)
- [মেমরি অফসেট ও AOB প্যাটার্ন স্ক্যানিং গাইড](#-মেমরি-অফসেট-ও-aob-প্যাটার্ন-স্ক্যানিং-গাইড)
- [সমস্যা সমাধান ও সাধারণ সমাধানসমূহ](#-সমস্যা-সমাধান-ও-সাধারণ-সমাধানসমূহ)
- [লাইসেন্স সংক্রান্ত তথ্য](#-লাইসেন্স-সংক্রান্ত-তথ্য)

---

## 🚀 বৈশিষ্ট্য ও হাইলাইটসমূহ

* **অ্যানিমেটেড স্প্ল্যাশ ও পার্টিকেল UI**: কাস্টম রেইন পার্টিকেল অ্যানিমেশন সিস্টেম (`ParticlesView.java`) এবং কাস্টম লোডিং বার (`BarLoadingView.java`)।
* **ইন্টারেক্টিভ ভাসমান ওভারলে মেনু**:
  * অ্যান্ড্রয়েড `WindowManager` ওভারলে সার্ভিস দ্বারা চালিত ড্র্যাগেবল মেনু ইন্টারফেস (`Menu.java`)।
  * কাস্টম স্টাইলযুক্ত সুইচ কন্ট্রোল (`SwitchStyle.java`) এবং টাইপফেস রেন্ডারিং।
* **রিয়েল-টাইম ESP ক্যানভাস**: নেটিভ গ্রাফিক্স স্ট্রাক্টের সাথে স্মুথ 60FPS ওভারলে রেন্ডারিং ক্যানভাস (`DrawView.java`)।
* **উচ্চ-ক্ষমতাসম্পন্ন নেটিভ C++ কোর**:
  * NDK `ndkBuild` দ্বারা কম্পাইলকৃত দ্বৈত শেয়ার্ড লাইব্রেরি: `libREGIX_MENU.so` এবং `libREGIX_CORE.so`।
  * সমন্বিত **KittyMemory** মেমরি স্ক্যানিং, প্যাচিং এবং প্রসেস মেমরি রিড/রাইট।
  * **Dobby Hook** এবং **Substrate** ইনলাইন ফাংশন হুকিং ফ্রেমওয়ার্ক।
  * ডায়নামিক ARM/ARM64 রানটাইম অ্যাসেম্বলি জেনারেশনের জন্য **Keystone Assembler**।
  * সিম্বল রেজোলিউশনের জন্য **xdl** উচ্চ-ক্ষমতাসম্পন্ন ELF ডায়নামিক লিঙ্কার।
* **সিকিউরিটি ও অথেন্টিকেশন সিস্টেম**:
  * ডিভাইস বাইন্ডিংয়ের জন্য হার্ডওয়্যার আইডি জেনারেটর (`HWID.java`)।
  * অনলাইন কি/লাইসেন্স ভ্যালিডেশন (`LicenseAuth.java`, `AuthHelper.java`)।
  * অ্যান্টি-ট্যাম্পারিং ও ইন্টিগ্রিটি চেকার (`IntegrityChecker.java`)।

---

## 🛠️ টেক স্ট্যাক ও সিস্টেমের প্রয়োজনীয়তা

| উপাদান | প্রয়োজনীয় সংস্করণ / স্পেসিফিকেশন |
| :--- | :--- |
| **অপারেটিং সিস্টেম** | Windows 10/11 (64-bit), macOS 12+, অথবা Linux (Ubuntu 20.04+) |
| **Java Development Kit (JDK)** | JDK 17 অথবা JDK 21 (Java 8 সামঞ্জস্যপূর্ণ মোড) |
| **Android SDK** | Compile SDK **34** / Target SDK **34** / Minimum SDK **19** |
| **Android NDK** | সংস্করণ `21.0.6113669` (r21e) |
| **বিল্ড সিস্টেম** | Gradle **8.7** সহ Android Gradle Plugin (AGP) **8.2.2** |
| **নেটিভ কম্পাইলার** | Clang C++17 (`ndkBuild` ইঞ্জিন) |
| **সমর্থিত টার্গেট ABI** | `armeabi-v7a` (32-bit ARM), `arm64-v8a` (64-bit ARM) |

---

## 📂 প্রজেক্ট আর্কিটেকচার ও ডিরেক্টরি ম্যাপ

```
regix-aimkill/
├── .gitignore                     # গিট ইগনোর রুলস
├── AGENTS.md                      # AI এজেন্ট ও ডেভেলপার নির্দেশিকা
├── README.md                      # প্রধান প্রজেক্ট ডকুমেন্টেশন
├── README_BN.md                   # বাংলা ডকুমেন্টেশন
├── README_HI.md                   # হিন্দি ডকুমেন্টেশন
├── README_AR.md                   # আরবি ডকুমেন্টেশন
├── LICENSE                        # GPL-3.0 লাইসেন্স
├── build.gradle                   # রুটের গ্রেডল বিল্ড স্ক্রিপ্ট
├── settings.gradle                # গ্রেডল সেটিংসে ':app' অন্তর্ভুক্ত
├── gradle.properties              # JVM ও AndroidX ফ্ল্যাগ
├── gradlew / gradlew.bat          # গ্রেডল র্যাপার স্ক্রিপ্ট
└── app/
    ├── build.gradle               # অ্যাপ মডিউল বিল্ড স্ক্রিপ্ট (NDK, SDK সংস্করণ)
    └── src/main/
        ├── AndroidManifest.xml    # পারমিশন ও অ্যাক্টিভিটি ঘোষণা
        ├── java/com/regix/aimkill/ # জাভা সোর্স ফাইল
        └── jni/                   # নেটিভ C++ NDK সোর্স ফাইল
```

---

## ⚙️ পূর্বশর্ত সেটআপ গাইড

### ১. JDK ডাউনলোড ও এনভায়রনমেন্ট কনফিগারেশন
Gradle 8.7 এবং AGP 8.2.2 এর জন্য **JDK 17** অথবা **JDK 21** প্রয়োজন।

* **ডাউনলোড**: [Eclipse Temurin (Adoptium)](https://adoptium.net/) অথবা [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) থেকে JDK 17 ডাউনলোড করুন।
* **`JAVA_HOME` এনভায়রনমেন্ট ভেরিয়েবল সেটআপ**:
  * Windows: System Properties -> Environment Variables -> New System Variable -> `JAVA_HOME` = `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot`
  * Path ভেরিয়েবলে `%JAVA_HOME%\bin` যোগ করুন।

---

### ২. Android SDK ও NDK ইনস্টলেশন
এই প্রজেক্টের জন্য বিশেষভাব **Android SDK Platform 34** এবং **Android NDK সংস্করণ `21.0.6113669` (r21e)** প্রয়োজন।

* Android Studio -> **Tools** -> **SDK Manager** -> **SDK Tools**-এ গিয়ে Show Package Details চেক করুন।
* **NDK (Side by side)**-এর অধীনে **`21.0.6113669`** নির্বাচন করে ইনস্টল করুন।

---

### ৩. `local.properties` কনফিগারেশন
প্রজেক্টের রুট ডিরেক্টরিতে `local.properties` ফাইল তৈরি করুন:

```properties
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
ndk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk\\ndk\\21.0.6113669
```

---

## 🔨 বিল্ড কমান্ডসমূহ

#### Windows (PowerShell):
```powershell
$env:ANDROID_PREFS_ROOT=$null; .\gradlew.bat assembleDebug
```

#### Linux / macOS:
```bash
./gradlew assembleDebug
```
*আউটপুট APK অবস্থান*: `app/build/outputs/apk/debug/app-debug.apk`

---

## 🔐 অথেন্টিকেশন কনফিগারেশন সেটআপ গাইড

### ১. লাইসেন্স সার্ভার ক্রেডেনশিয়াল
* **ফাইল পাথ**: [`app/src/main/java/com/regix/aimkill/Login.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Login.java#L64-L69)
  * **লাইন ৬৪**: `private static final String APPNAME = "regix aimkill";`
  * **লাইন ৬৫**: `private static final String OWNERID = "RTgStl6UQK";`
  * **লাইন ৬৬**: `private static final String SECRET = "edf522cb0077ae716c23b64d0f8f7f12b96db899b2be04a657c274ec86a5fac1";`
  * **লাইন ৬৭**: `private static final String VERSION = "1.0";`
  * **লাইন ৬৮**: `private static final String[] ALLOWED_PACKAGES = { "REGIXCOVER" };`
  * **লাইন ৬৯**: `private static final String LICENSEAUTH_URL = "https://licenseauth.help/api/1.3/";`

---

### ২. কাস্টম রিমোট অথেন্টিকেশন API (`AuthHelper`)
* **ফাইল পাথ**: [`app/src/main/java/com/regix/aimkill/AuthHelper.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/AuthHelper.java#L27-L28)
  * **লাইন ২৭**: `private static final String VALIDATE_URL = "https://your-domain.com/api/validate.php";`
  * **লাইন ২৮**: `private static final String SECRET_KEY = "YourSecretKey";`

---

## 🎨 লোগো ও আইকন কাস্টমাইজেশন গাইড

1. **অ্যাপ লঞ্চার আইকন**:
   * [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L12) (লাইন ১২: `android:icon="@mipmap/ic_launcher"`)
   * `app/src/main/res/mipmap-*/ic_launcher.png` ডিরেক্টরির ফাইল পরিবর্তন করুন।

2. **নেটিভ ভাসমান মেনু Base64 লোগো (C++)**:
   * [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp#L39-L40) (লাইন ৩৯–৪০: `Java_com_regix_aimkill_Menu_imageBase64` ফাংশনে নতুন Base64 জেনারেট করে বসান)।

---

## 🏷️ সম্পূর্ণ ব্র্যান্ড নাম ও প্যাকেজ রিব্র্যান্ডিং গাইড

1. **গ্রেডল আইডি পরিবর্তন**:
   * [`app/build.gradle`](file:///D:/GitHub/regix-aimkill/app/build.gradle#L4-L12) (লাইন ৪ ও ১২: `namespace 'com.yourbrand.app'`, `applicationId 'com.yourbrand.app'`)
2. **অ্যান্ড্রয়েড ম্যানিফেস্ট ও স্ট্রিংস**:
   * [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L13-L20) (লাইন ১৩: `android:label`, লাইন ২০: `android:name`)
   * [`app/src/main/res/values/strings.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/res/values/strings.xml#L2) (লাইন ২: `app_name`)
3. **জাভা প্যাকেজ ডিরেক্টরি ও হেডার**:
   * ডিরেক্টরি পুনঃনামকরণ করুন: `com/yourbrand/app/`
   * সমস্ত ১৮টি জাভা ফাইলের লাইন ১-এ `package com.yourbrand.app;` আপডেট করুন।
4. **JNI C++ নেটিভ ফাংশন নাম**:
   * [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp) (লাইন ২৮, ৩৯, ১১৩, ১২১, ১৭০, ৪৪১, ৪৫০, ৪৫৬, ৪৭৭) `Java_com_yourbrand_app_Menu_...` আপডেট করুন।

---

## 🎯 মেমরি অফসেট ও AOB প্যাটার্ন স্ক্যানিং গাইড

* **ডায়নামিক Il2Cpp অটো-রেজোলিউশন**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L15-L60)
* **স্ট্যাটিক অফসেট তালিকা**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L274-L1081) (`getRealOffset(0xHEX)`)
* **AOB প্যাটার্ন স্ক্যানিং**: [`app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp#L15-L60) (`KittyScanner::findIDAFirst`)
* **মেমরি প্যাচিং**: [`app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp#L20-L50) (`MemoryPatch::createWithHex`)

---

## 📄 লাইসেন্স সংক্রান্ত তথ্য

এই প্রজেক্টটি **[GNU General Public License v3.0 (GPL-3.0)](LICENSE)**-এর অধীনে প্রকাশিত মুক্ত সফটওয়্যার।
