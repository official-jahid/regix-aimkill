# REGIX AIMKILL (النسخة العربية)

![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android)
![Gradle](https://img.shields.io/badge/Gradle-8.7-blue.svg?style=for-the-badge&logo=gradle)
![AGP](https://img.shields.io/badge/AGP-8.2.2-orange.svg?style=for-the-badge&logo=android)
![NDK](https://img.shields.io/badge/NDK-21.0.6113669-red.svg?style=for-the-badge&logo=cplusplus)
![JDK](https://img.shields.io/badge/JDK-17%20%7C%2021-brightgreen.svg?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg?style=for-the-badge)

[English](README.md) | [বাংলা](README_BN.md) | [Hindi](README_HI.md) | **العربية**

تطبيق أندرويد متقدم يضم قائمة تراكب عائمة تفاعلية، ورسوم متحركة مخصصة للجزيئات، ورسم لوحة ESP في الوقت الفعلي، ونواة إدارة ذاكرة C++ عالية الأداء مبنية باستخدام Android SDK و NDK.

---

## 📖 جدول المحتويات

- [الميزات والميزات الرئيسية](#-الميزات-والميزات-الرئيسية)
- [المتطلبات التقنية ومتطلبات النظام](#-المتطلبات-التقنية-ومتطلبات-النظام)
- [هيكلية المشروع وخريطة المجلدات](#-هيكلية-المشروع-وخريطة-المجلدات)
- [دليل إعداد المتطلبات الأساسية](#-دليل-إعداد-المتطلبات-الأساسية)
  - [1. تنزيل JDK وتكوين البيئة](#1-تنزيل-jdk-وتكوين-البيئة)
  - [2. تثبيت Android SDK و NDK](#2-تثبيت-android-sdk-و-ndk)
  - [3. إعداد ملف local.properties](#3-إعداد-ملف-localproperties)
- [أدلة إعداد بيئة التطوير IDE](#-أدلة-إعداد-بيئة-التطوير-ide)
  - [الخيار أ: إعداد Android Studio](#الخيار-أ-إعداد-android-studio-موصى-به)
  - [الخيار ب: إعداد Visual Studio Code (VS Code)](#الخيار-ب-إعداد-visual-studio-code-vs-code)
- [أوامر التبعيات والتثبيت](#-أوامر-التبعيات-والتثبيت)
- [أوامر البناء](#-أوامر-البناء)
- [دليل إعداد مصادقة الترخيص والـ API](#-دليل-إعداد-مصادقة-الترخيص-والـ-api)
- [دليل تخصيص الشعار والأيقونات](#-دليل-تخصيص-الشعار-والأيقونات)
- [دليل تغيير اسم العلامة التجارية واسم الحزمة](#-دليل-تغيير-اسم-العلامة-التجارية-واسم-الحزمة)
- [دليل إزاحة الذاكرة والبحث عن أنماط AOB](#-دليل-إزاحة-الذاكرة-والبحث-عن-أنماط-aob)
- [استكشاف الأخطاء وإصلاحها](#-استكشاف-الأخطاء-وإصلاحها)
- [معلومات الترخيص](#-معلومات-الترخيص)

---

## 🚀 الميزات والميزات الرئيسية

* **شاشة بدء متحركة ورسوم جزيئية**: نظام رسوم متحركة للمطر الأحادي (`ParticlesView.java`) وأشرطة تحميل مخصصة (`BarLoadingView.java`).
* **قائمة تراكب عائمة تفاعلية**:
  * واجهة تراكب قابلة للسحب تعمل بواسطة خدمة `WindowManager` في أندرويد (`Menu.java`).
  * مفاتيح تحكم مخصصة (`SwitchStyle.java`) ورسوم نصوص مطورة.
* **لوحة ESP في الوقت الفعلي**: لوحة تراكب تعمل بسلاسة 60 إطارًا في الثانية (`DrawView.java`) تتفاعل مع برمجيات C++ الأصلية.
* **نواة C++ عالية الأداء**:
  * مكتبتان مشتركتان تم تجميعهما بواسطة NDK `ndkBuild`: `libREGIX_MENU.so` و `libREGIX_CORE.so`.
  * مدمج بـ **KittyMemory** لفحص وقراءة وكتابة الذاكرة.
  * إطار عمل **Dobby Hook** و **Substrate** لربط الدوال الأصلية.
  * **Keystone Assembler** لإنشاء التعليمات البرمجية لتجميع ARM/ARM64 ديناميكيًا.
  * **xdl** لرابط ELF الديناميكي عالي الأداء.
* **نظام الأمان والمصادقة**:
  * مولد معرف الأجهزة (`HWID.java`) لربط التطبيق بالجهاز.
  * التحقق من المفاتيح والتراخيص عبر الإنترنت (`LicenseAuth.java`, `AuthHelper.java`).
  * أداة فحص النزاهة والحماية من التعديل (`IntegrityChecker.java`).

---

## 🛠️ المتطلبات التقنية ومتطلبات النظام

| المكون | الإصدار / المواصفات المطلوبة |
| :--- | :--- |
| **نظام التشغيل** | Windows 10/11 (64-bit), macOS 12+, أو Linux (Ubuntu 20.04+) |
| **حزمة تطوير الجافا (JDK)** | JDK 17 أو JDK 21 (وضع توافق Java 8) |
| **Android SDK** | Compile SDK **34** / Target SDK **34** / Minimum SDK **19** |
| **Android NDK** | الإصدار `21.0.6113669` (r21e) |
| **نظام البناء** | Gradle **8.7** مع Android Gradle Plugin (AGP) **8.2.2** |
| **المترجم الأصلي** | Clang C++17 (محرك `ndkBuild`) |
| **المعمارية المدعومة** | `armeabi-v7a` (32-bit ARM), `arm64-v8a` (64-bit ARM) |

---

## 📂 هيكلية المشروع وخريطة المجلدات

```
regix-aimkill/
├── .gitignore                     # قواعد تجاهل Git
├── AGENTS.md                      # دليل المطور ووكيل الذكاء الاصطناعي
├── README.md                      # وثيقة المشروع الرئيسية (الإنجليزية)
├── README_BN.md                   # الوثيقة باللغة البنغالية
├── README_HI.md                   # الوثيقة باللغة الهندية
├── README_AR.md                   # الوثيقة باللغة العربية
├── LICENSE                        # ترخيص GPL-3.0
├── build.gradle                   # نص بناء Gradle الرئيسي
├── settings.gradle                # إعدادات Gradle
└── app/
    ├── build.gradle               # نص بناء وحدة التطبيق
    └── src/main/
        ├── AndroidManifest.xml    # تصريحات الأذونات والأنشطة
        ├── java/com/regix/aimkill/ # ملفات المصدر لجافا
        └── jni/                   # ملفات المصدر لـ C++ NDK
```

---

## ⚙️ دليل إعداد المتطلبات الأساسية

### 1. تنزيل JDK وتكوين البيئة
يتطلب Gradle 8.7 و AGP 8.2.2 تثبيت **JDK 17** أو **JDK 21**.

* **التنزيل**: قم بتنزيل JDK 17 من [Eclipse Temurin (Adoptium)](https://adoptium.net/) أو [Oracle JDK](https://www.oracle.com/java/technologies/downloads/).
* **إعداد متغير `JAVA_HOME`**:
  * Windows: System Properties -> Environment Variables -> New System Variable -> `JAVA_HOME` = `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot`
  * أضف `%JAVA_HOME%\bin` إلى المتغير `Path`.

---

### 2. تثبيت Android SDK و NDK
يتطلب هذا المشروع تحديدًا **Android SDK Platform 34** وإصدار **Android NDK `21.0.6113669` (r21e)**.

* Android Studio -> **Tools** -> **SDK Manager** -> **SDK Tools** -> حدد Show Package Details.
* قم بتحديد وتثبيت الإصدار **`21.0.6113669`** تحت قائمة **NDK (Side by side)**.

---

### 3. إعداد ملف `local.properties`
قم بإنشاء ملف باسم `local.properties` في المجلد الرئيسي للمشروع:

```properties
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
ndk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk\\ndk\\21.0.6113669
```

---

## 🔨 أوامر البناء

#### Windows (PowerShell):
```powershell
$env:ANDROID_PREFS_ROOT=$null; .\gradlew.bat assembleDebug
```

#### Linux / macOS:
```bash
./gradlew assembleDebug
```
*مسار ملف APK الناتج*: `app/build/outputs/apk/debug/app-debug.apk`

---

## 🔐 دليل إعداد مصادقة الترخيص والـ API

### 1. بيانات اعتماد سيرفر الترخيص
* **مسار الملف**: [`app/src/main/java/com/regix/aimkill/Login.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Login.java#L64-L69)
  * **السطر 64**: `private static final String APPNAME = "regix aimkill";`
  * **السطر 65**: `private static final String OWNERID = "RTgStl6UQK";`
  * **السطر 66**: `private static final String SECRET = "edf522cb0077ae716c23b64d0f8f7f12b96db899b2be04a657c274ec86a5fac1";`
  * **السطر 67**: `private static final String VERSION = "1.0";`
  * **السطر 68**: `private static final String[] ALLOWED_PACKAGES = { "REGIXCOVER" };`
  * **السطر 69**: `private static final String LICENSEAUTH_URL = "https://licenseauth.help/api/1.3/";`

---

### 2. إعداد الـ API الخاص بـ AuthHelper
* **مسار الملف**: [`app/src/main/java/com/regix/aimkill/AuthHelper.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/AuthHelper.java#L27-L28)
  * **السطر 27**: `private static final String VALIDATE_URL = "https://your-domain.com/api/validate.php";`
  * **السطر 28**: `private static final String SECRET_KEY = "YourSecretKey";`

---

## 🎨 دليل تخصيص الشعار والأيقونات

1. **أيقونة التطبيق الرئيسية**:
   * [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L12) (السطر 12: `android:icon="@mipmap/ic_launcher"`)
   * قم باستبدال الملفات في مجلدات `app/src/main/res/mipmap-*/ic_launcher.png`.

2. **شعار القائمة العائمة في C++ (Base64)**:
   * [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp#L39-L40) (السطران 39–40: في الدالة `Java_com_regix_aimkill_Menu_imageBase64` ضع نص Base64 الجديد داخل `OBFUSCATE("...")`).

---

## 🏷️ دليل تغيير اسم العلامة التجارية واسم الحزمة

1. **تحديث معرّف التطبيق في Gradle**:
   * [`app/build.gradle`](file:///D:/GitHub/regix-aimkill/app/build.gradle#L4-L12) (السطران 4 و 12: `namespace 'com.yourbrand.app'`, `applicationId 'com.yourbrand.app'`)
2. **ملف AndroidManifest والنصوص**:
   * [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L13-L20) (السطر 13: `android:label`, السطر 20: `android:name`)
   * [`app/src/main/res/values/strings.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/res/values/strings.xml#L2) (السطر 2: `app_name`)
3. **مجلد حزمة الجافا وإعلان الحزمة**:
   * تغيير اسم المجلد: `com/yourbrand/app/`
   * تحديث السطر 1 في جميع ملفات Java الـ 18 إلى `package com.yourbrand.app;`.
4. **أسماء دوال JNI في C++**:
   * [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp) (الأسطر 28, 39, 113, 121, 170, 441, 450, 456, 477) قم بتحديثها إلى `Java_com_yourbrand_app_Menu_...`.

---

## 🎯 دليل إزاحة الذاكرة والبحث عن أنماط AOB

* **محلل Il2Cpp الديناميكي**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L15-L60)
* **قائمة الإزاحات الثابتة**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L274-L1081) (`getRealOffset(0xHEX)`)
* **البحث عن أنماط AOB**: [`app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp#L15-L60) (`KittyScanner::findIDAFirst`)
* **تعديل الذاكرة والترقيع**: [`app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp#L20-L50) (`MemoryPatch::createWithHex`)

---

## 📄 معلومات الترخيص

هذا المشروع هو برنامج مفتوح المصدر تم توزيعه بموجب شروط **[ترخيص GNU العام v3.0 (GPL-3.0)](LICENSE)**.
