#!/bin/bash

# Pizza Employee Dashboard - Android Build Script
echo "🍕 Building Pizza Employee Dashboard Android App..."

# Check if we're in the right directory
if [ ! -f "build.gradle.kts" ]; then
    echo "❌ Error: Please run this script from the android-pizza-app directory"
    exit 1
fi

# Make gradlew executable
chmod +x gradlew

echo "📱 Building Android APK..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "📦 APK location: app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "🚀 To install on device:"
    echo "   adb install app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "📋 Next steps:"
    echo "   1. Update the dashboard URL in MainActivity.kt if needed"
    echo "   2. Install the APK on your Android device"
    echo "   3. Open the Pizza Dashboard app"
else
    echo "❌ Build failed. Check the error messages above."
    exit 1
fi