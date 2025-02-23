@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("io.instah.Auron-Gradle") version "1.1.0A"
    kotlin("multiplatform")
    id("com.android.application")
}

group = "com.example"

kotlin {
    jvm {
        mainRun {
            mainClass = "com.example.MainKt"
        }
    }

    auron {
        application("Example Application")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.material3)
            implementation(project(":Example-library"))
            implementation(auron.appSdk)
            implementation(auron.voyager.navigator)
            implementation(auron.voyager.transitions)
        }
    }
}
