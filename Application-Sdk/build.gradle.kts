plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("org.jetbrains.compose")
    `maven-publish`
}

kotlin {
    jvmToolchain(17)

    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            api("androidx.activity:activity-compose:1.9.3")

        }

        commonMain.dependencies {
            api(project(":Sdk"))
            implementation(compose.foundation)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
}

android {
    compileSdkVersion = "android-35"
    namespace = "pl.instah.auron"

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }
}