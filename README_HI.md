# REGIX AIMKILL (हिंदी संस्करण)

![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android)
![Gradle](https://img.shields.io/badge/Gradle-8.7-blue.svg?style=for-the-badge&logo=gradle)
![AGP](https://img.shields.io/badge/AGP-8.2.2-orange.svg?style=for-the-badge&logo=android)
![NDK](https://img.shields.io/badge/NDK-21.0.6113669-red.svg?style=for-the-badge&logo=cplusplus)
![JDK](https://img.shields.io/badge/JDK-17%20%7C%2021-brightgreen.svg?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-GPL--3.0-blue.svg?style=for-the-badge)

[English](README.md) | [বাংলা](README_BN.md) | **हिंदी** | [Arabic](README_AR.md)

एक उन्नत एंड्रॉइड एप्लिकेशन जिसमें इंटरएक्टिव फ्लोटिंग ओवरले मेनू, कस्टम पार्टिकल एनिमेशन, रियल-टाइम ESP कैनवास ड्राइंग, और Android SDK व NDK द्वारा निर्मित उच्च-प्रदर्शन C++ नेटिव मेमोरी मैनेजमेंट कोर शामिल है।

---

## 📖 विषय सूची

- [विशेषताएं और मुख्य बिंदु](#-विशेषताएं-और-मुख्य-बिंदु)
- [टेक स्टैक और सिस्टम आवश्यकताएं](#-टेक-स्टैक-और-सिस्टम-आवश्यकताएं)
- [प्रोजेक्ट आर्किटेक्चर और निर्देशिका मानचित्र](#-प्रोजेक्ट-आर्किटेक्चर-और-निर्देशिका-मानचित्र)
- [पूर्वापेक्षा सेटअप गाइड](#-पूर्वापेक्षा-सेटअप-गाइड)
  - [1. JDK डाउनलोड और पर्यावरण कॉन्फ़िगरेशन](#1-jdk-डाउनलोड-और-पर्यावरण-कॉन्फ़िगरेशन)
  - [2. Android SDK और NDK स्थापना](#2-android-sdk-और-ndk-स्थापना)
  - [3. local.properties कॉन्फ़िगरेशन](#3-localproperties-कॉन्फ़िगरेशन)
- [IDE सेटअप गाइड](#-ide-सेटअप-गाइड)
  - [विकल्प A: Android Studio सेटअप](#विकल्प-a-android-studio-सेटअप-अनुशंसित)
  - [विकल्प B: Visual Studio Code (VS Code) सेटअप](#विकल्प-b-visual-studio-code-vs-code-सेटअप)
- [निर्भरता और स्थापना आदेश](#-निर्भरता-और-स्थापना-आदेश)
- [बिल्ड कमांड](#-बिल्ड-कमांड)
- [प्रमाणीकरण कॉन्फ़िगरेशन सेटअप गाइड](#-प्रमाणीकरण-कॉन्फ़िगरेशन-सेटअप-गाइड)
- [लोगो और आइकन कस्टमाइज़ेशन गाइड](#-लोगो-और-आइकन-कस्टमाइज़ेशन-गाइड)
- [संपूर्ण ब्रांड नाम और पैकेज रीब्रांडिंग गाइड](#-संपूर्ण-ब्रांड-नाम-और-पैकेज-रीब्रांडिंग-गाइड)
- [मेमोरी ऑफसेट और AOB पैटर्न स्कैनिंग गाइड](#-मेमोरी-ऑफसेट-और-aob-पैटर्न-स्कैनिंग-गाइड)
- [समस्या निवारण और सामान्य समाधान](#-समस्या-निवारण-और-सामान्य-समाधान)
- [लाइसेंस जानकारी](#-लाइसेंस-जानकारी)

---

## 🚀 विशेषताएं और मुख्य बिंदु

* **एनिमेटेड स्प्लैश और पार्टिकल UI**: कस्टम रेन पार्टिकल एनिमेशन सिस्टम (`ParticlesView.java`) और कस्टम लोडिंग बार (`BarLoadingView.java`)।
* **इंटरएक्टिव फ्लोटिंग ओवरले मेनू**:
  * एंड्रॉइड `WindowManager` ओवरले सेवा द्वारा संचालित ड्रैग करने योग्य ओवरले इंटरफेस (`Menu.java`)।
  * कस्टम स्टाइल वाले स्विच कंट्रोल (`SwitchStyle.java`) और टाइपफेस रेंडरिंग।
* **रियल-टाइम ESP कैनवास**: नेटिव ग्राफिक्स स्ट्रक्चर के साथ स्मूथ 60FPS ओवरले रेंडरिंग कैनवास (`DrawView.java`)।
* **उच्च-प्रदर्शन नेटिव C++ कोर**:
  * NDK `ndkBuild` द्वारा संकलित दोहरी साझा लाइब्रेरी: `libREGIX_MENU.so` और `libREGIX_CORE.so`|
  * एकीकृत **KittyMemory** मेमोरी स्कैनिंग, पैचिंग और प्रोसेस मेमोरी रीड/राइट।
  * **Dobby Hook** और **Substrate** इनलाइन फ़ंक्शन हुकिंग फ्रेमवर्क।
  * डायनामिक ARM/ARM64 रनटाइम असेंबली जनरेशन के लिए **Keystone Assembler**|
  * सिंबल रिज़ॉल्यूशन के लिए **xdl** उच्च-प्रदर्शन ELF डायनामिक लिंकर।
* **सुरक्षा और प्रमाणीकरण प्रणाली**:
  * डिवाइस बाइंडिंग के लिए हार्डवेयर आईडी जनरेटर (`HWID.java`)|
  * ऑनलाइन कुंजी/लाइसेंस सत्यापन (`LicenseAuth.java`, `AuthHelper.java`)|
  * एंटी-टैंपरिंग और इंटीग्रिटी चेकर (`IntegrityChecker.java`)|

---

## 🛠️ टेक स्टैक और सिस्टम आवश्यकताएं

| घटक | आवश्यक संस्करण / विनिर्देश |
| :--- | :--- |
| **ऑपरेटिंग सिस्टम** | Windows 10/11 (64-bit), macOS 12+, या Linux (Ubuntu 20.04+) |
| **Java Development Kit (JDK)** | JDK 17 या JDK 21 (Java 8 संगतता मोड) |
| **Android SDK** | Compile SDK **34** / Target SDK **34** / Minimum SDK **19** |
| **Android NDK** | संस्करण `21.0.6113669` (r21e) |
| **बिल्ड सिस्टम** | Gradle **8.7** के साथ Android Gradle Plugin (AGP) **8.2.2** |
| **नेटिव कंपाइलर** | Clang C++17 (`ndkBuild` इंजन) |
| **समर्थित लक्ष्य ABI** | `armeabi-v7a` (32-bit ARM), `arm64-v8a` (64-bit ARM) |

---

## 📂 प्रोजेक्ट आर्किटेक्चर और निर्देशिका मानचित्र

```
regix-aimkill/
├── .gitignore                     # गिट अनदेखा नियम
├── AGENTS.md                      # AI एजेंट और डेवलपर दिशानिर्देश
├── README.md                      # मुख्य प्रोजेक्ट दस्तावेज़ (अंग्रेज़ी)
├── README_BN.md                   # बंगाली दस्तावेज़
├── README_HI.md                   # हिंदी दस्तावेज़
├── README_AR.md                   # अरबी दस्तावेज़
├── LICENSE                        # GPL-3.0 लाइसेंस
├── build.gradle                   # रूट ग्रेडिल बिल्ड स्क्रिप्ट
├── settings.gradle                # ग्रेडिल सेटिंग्स
└── app/
    ├── build.gradle               # ऐप मॉड्यूल बिल्ड स्क्रिप्ट
    └── src/main/
        ├── AndroidManifest.xml    # अनुमति और गतिविधि घोषणाएं
        ├── java/com/regix/aimkill/ # जावा स्रोत फ़ाइलें
        └── jni/                   # नेटिव C++ NDK स्रोत फ़ाइलें
```

---

## ⚙️ पूर्वापेक्षा सेटअप गाइड

### 1. JDK डाउनलोड और एनवायरमेंट कॉन्फ़िगरेशन
Gradle 8.7 और AGP 8.2.2 के लिए **JDK 17** या **JDK 21** आवश्यक है।

* **डाउनलोड**: [Eclipse Temurin (Adoptium)](https://adoptium.net/) से JDK 17 डाउनलोड करें।
* **`JAVA_HOME` सेट करना**:
  * Windows: System Properties -> Environment Variables -> New -> `JAVA_HOME` = `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot`
  * Path में `%JAVA_HOME%\bin` जोड़ें।

---

### 2. Android SDK और NDK स्थापना
इस प्रोजेक्ट के लिए विशेष रूप से **Android SDK Platform 34** और **Android NDK संस्करण `21.0.6113669` (r21e)** आवश्यक है।

* Android Studio -> **Tools** -> **SDK Manager** -> **SDK Tools** -> Check Show Package Details.
* **NDK (Side by side)** के तहत **`21.0.6113669`** चुनें और इंस्टॉल करें।

---

### 3. `local.properties` कॉन्फ़िगरेशन
प्रोजेक्ट की रूट डायरेक्टरी में `local.properties` फ़ाइल बनाएं:

```properties
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
ndk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk\\ndk\\21.0.6113669
```

---

## 🔨 बिल्ड कमांड

#### Windows (PowerShell):
```powershell
$env:ANDROID_PREFS_ROOT=$null; .\gradlew.bat assembleDebug
```

#### Linux / macOS:
```bash
./gradlew assembleDebug
```
*आउटपुट APK स्थान*: `app/build/outputs/apk/debug/app-debug.apk`

---

## 🔐 प्रमाणीकरण कॉन्फ़िगरेशन सेटअप गाइड

### 1. लाइसेंस सर्वर साख
* **फ़ाइल पथ**: [`app/src/main/java/com/regix/aimkill/Login.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/Login.java#L64-L69)
  * **पंक्ति 64**: `private static final String APPNAME = "regix aimkill";`
  * **पंक्ति 65**: `private static final String OWNERID = "RTgStl6UQK";`
  * **पंक्ति 66**: `private static final String SECRET = "edf522cb0077ae716c23b64d0f8f7f12b96db899b2be04a657c274ec86a5fac1";`
  * **पंक्ति 67**: `private static final String VERSION = "1.0";`
  * **पंक्ति 68**: `private static final String[] ALLOWED_PACKAGES = { "REGIXCOVER" };`
  * **पंक्ति 69**: `private static final String LICENSEAUTH_URL = "https://licenseauth.help/api/1.3/";`

---

### 2. कस्टम रिमोट ऑथेंटिकेशन API (`AuthHelper`)
* **फ़ाइल पथ**: [`app/src/main/java/com/regix/aimkill/AuthHelper.java`](file:///D:/GitHub/regix-aimkill/app/src/main/java/com/regix/aimkill/AuthHelper.java#L27-L28)
  * **पंक्ति 27**: `private static final String VALIDATE_URL = "https://your-domain.com/api/validate.php";`
  * **पंक्ति 28**: `private static final String SECRET_KEY = "YourSecretKey";`

---

## 🎨 लोगो और आइकन कस्टमाइज़ेशन गाइड

1. **ऐप लॉन्चर आइकन**:
   * [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L12) (पंक्ति 12: `android:icon="@mipmap/ic_launcher"`)
   * `app/src/main/res/mipmap-*/ic_launcher.png` डायरेक्टरी की फ़ाइलों को बदलें।

2. **नेटिव फ्लोटिंग मेनू Base64 लोगो (C++)**:
   * [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp#L39-L40) (पंक्ति 39–40: `Java_com_regix_aimkill_Menu_imageBase64` फ़ंक्शन में नई Base64 स्ट्रिंग दर्ज करें)।

---

## 🏷️ संपूर्ण ब्रांड नाम और पैकेज रीब्रांडिंग गाइड

1. **ग्रेडिल आईडी बदलें**:
   * [`app/build.gradle`](file:///D:/GitHub/regix-aimkill/app/build.gradle#L4-L12) (पंक्ति 4 और 12: `namespace 'com.yourbrand.app'`, `applicationId 'com.yourbrand.app'`)
2. **एंड्रॉइड मेनिफेस्ट और स्ट्रिंग्स**:
   * [`app/src/main/AndroidManifest.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/AndroidManifest.xml#L13-L20) (पंक्ति 13: `android:label`, पंक्ति 20: `android:name`)
   * [`app/src/main/res/values/strings.xml`](file:///D:/GitHub/regix-aimkill/app/src/main/res/values/strings.xml#L2) (पंक्ति 2: `app_name`)
3. **जावा पैकेज डायरेक्टरी और हेडर**:
   * डायरेक्टरी का नाम बदलें: `com/yourbrand/app/`
   * सभी 18 जावा फ़ाइलों की पंक्ति 1 में `package com.yourbrand.app;` अपडेट करें।
4. **JNI C++ नेटिव फ़ंक्शन नाम**:
   * [`app/src/main/jni/client.cpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/client.cpp) (पंक्ति 28, 39, 113, 121, 170, 441, 450, 456, 477) को `Java_com_yourbrand_app_Menu_...` में बदलें।

---

## 🎯 मेमोरी ऑफसेट और AOB पैटर्न स्कैनिंग गाइड

* **डायनामिक Il2Cpp ऑटो-रिज़ॉल्यूशन**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L15-L60)
* **स्टैटिक ऑफसेट सूची**: [`app/src/main/jni/Hack/class.h`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Hack/class.h#L274-L1081) (`getRealOffset(0xHEX)`)
* **AOB पैटर्न स्कैनिंग**: [`app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/KittyScanner.hpp#L15-L60) (`KittyScanner::findIDAFirst`)
* **मेमोरी पैचिंग**: [`app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp`](file:///D:/GitHub/regix-aimkill/app/src/main/jni/Tools/KittyMemory/MemoryPatch.hpp#L20-L50) (`MemoryPatch::createWithHex`)

---

## 📄 लाइसेंस जानकारी

यह प्रोजेक्ट **[GNU General Public License v3.0 (GPL-3.0)](LICENSE)** की शर्तों के तहत वितरित एक ओपन-सोर्स सॉफ़्टवेयर है।
