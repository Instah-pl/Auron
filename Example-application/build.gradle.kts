@file:OptIn(ExperimentalKotlinGradlePluginApi::class)
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("io.instah.Auron-Gradle") version "1.1.0-SNAPSHOT"
}

group = "com.example"

auron {
    dependencies {
        implementation(auron.voyager.navigator)
        implementation(project(":Example-library"))
    }
}
