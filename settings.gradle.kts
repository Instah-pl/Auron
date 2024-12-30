include(":Sdk")
include(":Plugin")
include(":Example-application")
include(":Example-library")
include(":Application-Sdk")
include(":Permissions")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        google()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    kotlin("multiplatform") version "2.1.0" apply false
    kotlin("jvm") version "2.1.0" apply false
    kotlin("plugin.compose") version "2.1.0" apply false
    id("org.jetbrains.compose") version "1.7.3" apply false
    id("com.android.application") version "8.7.3" apply false
    id("com.android.library") version "8.7.3" apply false
    id("com.vanniktech.maven.publish") version "0.30.0" apply false
}

rootProject.name = "Auron"

