plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.santanas.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.santanas.app"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "2.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}
