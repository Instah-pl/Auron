plugins {
    id("pl.instah.Auron-Gradle") version "1.0.2A"
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    auron {
        library()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(auron.sdk)
        }
    }
}
