plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    androidTarget()

    jvmToolchain(17)
}

android {
    compileSdkVersion = "android-35"
    namespace = "io.instah.auron"

    defaultConfig {
        minSdk = 24
    }
}