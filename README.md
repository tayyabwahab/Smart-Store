## Smart Store

## Overview
Smart Store is a QR based mobile app that uses QR code scanning technology that helps users to buy items at grocery stores without going the line waiting line of checkout. Customers can simply enter the store using the store app in their smart phones and scan the products they want, payment will be processed online from their accounts and then they can pick their products and leave. The store is partially-automated, with customers being able to purchase products without using a cashier or checkout station.

## Features
- **Seamless Entry & Exit**: Start shopping without traditional checkouts.
- **QR/Barcode Scanning**: Scan products using the device camera.
- **Online Payments**: Orders are confirmed and charged to the user's account.
- **Account Management**: View and edit profile details, wallet/deposit info, and order confirmations.

## Tech Stack
- **Language**: Java (Android)
- **Minimum SDK**: 19
- **Target/Compile SDK**: 29
- **Core Libraries**:
  - AndroidX AppCompat, ConstraintLayout
  - Firebase Auth, Realtime Database, Storage
  - Google Play Services Auth
  - ZXing scanner (`me.dm7.barcodescanner:zxing`)
  - QR Generator (`androidmads.library.qrgenearator`)
  - Picasso (image loading)

## App Structure (key screens)
Activities declared in `app/src/main/AndroidManifest.xml`:
- `MainActivity` (launcher)
- `register`
- `QRCodegen`
- `Menu`
- `Scanqr`
- `ConfirmOrder`
- `InvalidCode`
- `AccountDetails`
- `EditInfo`
- `Deposit`

Java sources live under `app/src/main/java/com/example/fyp/`.

## Permissions
Defined in `AndroidManifest.xml`:
- `CAMERA`: Required for scanning QR/barcodes.
- `INTERNET`: Required for Firebase and online operations.
- `WRITE_EXTERNAL_STORAGE`: Used for saving generated QR codes or related assets.

## Prerequisites
- Android Studio (3.5+ recommended) with Android SDK Platform 29
- JDK 8+
- A Firebase project (for Auth, Realtime Database, and Storage)
- Google Play services on the test device/emulator

## Build & Run
- From Android Studio: Use the "Run" button and select a device/emulator.
- From terminal:
  - Linux/macOS: `./gradlew assembleDebug`
  - Windows: `gradlew.bat assembleDebug`
  - Install to a connected device: `./gradlew installDebug`

Artifacts:
- Debug/Release APKs can be found in:
  - `app/release/app-release.apk`
  - `app/release/smartstore.apk`
  - `app/debug/smartstore.apk`
  - `apk/smartstore.apk`

## Prebuilt APK
- The `apk/` folder contains a ready-to-install build: `apk/smartstore.apk`.
- Install options:
  - On device: copy the APK to your phone and open it; enable "Install unknown apps" if prompted.
  - Via ADB: `adb install -r apk/smartstore.apk`

## Usage Flow
1. Launch the app and register/sign in.
2. Enter the store and use the **Scan** feature to scan product QR/barcodes.
3. Review your cart/order and confirm payment.
4. View order confirmation and account/wallet details.

## License
No license file is provided. If you intend to open-source, add a license of your choice (e.g., MIT, Apache-2.0). 