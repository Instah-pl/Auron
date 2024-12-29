@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("pl.instah.Auron-Gradle") version "1.0.2A"
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
        }
    }
}
