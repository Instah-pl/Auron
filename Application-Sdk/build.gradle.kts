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

    sourceSets {
        androidMain.dependencies {
            api("androidx.activity:activity-compose:1.9.3")
            api(project(":Sdk"))
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