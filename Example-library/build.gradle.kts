import pl.instah.auron.Permission

plugins {
    id("pl.instah.Auron-Gradle") version "1.0.6A"
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    auron {
        library()

        +Permission.LOCATION
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(auron.sdk)
        }
    }
}
