plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
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
            api(project(":Permissions"))
        }

        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.runtime)
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