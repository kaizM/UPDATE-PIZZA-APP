# Pizza Employee Dashboard - Android App Setup

## Overview
This Android app loads your web-based employee dashboard in a WebView, giving you a native mobile experience for managing pizza orders.

## Quick Setup

### 1. Update the Dashboard URL
Before building, update the URL in `MainActivity.kt` (line 48-52) to point to your deployed web app:

```kotlin
val dashboardUrl = if (replSlug != null && replOwner != null) {
    "https://$replSlug-$replOwner.replit.app/employee"
} else {
    // Replace this with your actual deployed URL
    "https://your-app-name.replit.app/employee"
}
```

### 2. Build the Android App

#### Option A: Android Studio
1. Open the `android-pizza-app` folder in Android Studio
2. Wait for Gradle sync to complete
3. Click "Run" or press Shift+F10
4. Choose your device/emulator

#### Option B: Command Line
```bash
cd android-pizza-app
./gradlew assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### 3. Install on Device
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Features

✅ **WebView Integration**: Loads your full web dashboard in a native container
✅ **Internet Permissions**: Configured for web connectivity
✅ **Back Navigation**: Android back button works with web navigation
✅ **Auto URL Detection**: Uses Replit environment variables when available
✅ **Mobile Optimized**: WebView settings optimized for mobile experience

## Configuration Details

### Permissions (AndroidManifest.xml)
- `INTERNET`: Required for loading web content
- `ACCESS_NETWORK_STATE`: For network connectivity checks

### WebView Settings
- JavaScript enabled for full dashboard functionality
- DOM storage enabled for local data persistence
- Zoom controls enabled for better mobile experience
- Mixed content allowed for flexible hosting

## Troubleshooting

### Blank Screen Issues
1. **Check URL**: Ensure the dashboard URL is correct and accessible
2. **Network**: Verify internet connection on device
3. **CORS**: Web app may need CORS headers for mobile WebView

### Development Testing
- Use `http://10.0.2.2:5000/employee` for Android emulator
- Use your computer's IP address for physical devices
- Example: `http://192.168.1.100:5000/employee`

### Build Issues
- Ensure Android SDK API 26+ is installed
- Check that Kotlin plugin is enabled
- Verify Gradle wrapper permissions: `chmod +x gradlew`

## App Details
- **Package**: com.example.pizza
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 35 (Android 15)
- **Architecture**: WebView-based hybrid app

## Next Steps
Once the app loads successfully:
1. Test order management features
2. Verify real-time updates work
3. Check mobile responsiveness
4. Test offline behavior if applicable

The app will provide the same functionality as your web dashboard but optimized for mobile use with native Android navigation.