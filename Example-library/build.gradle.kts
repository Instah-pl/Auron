import pl.instah.auron.Permission

plugins {
    id("pl.instah.Auron-Gradle") version "1.0.0A"
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    auron {
        library()

        Permission.LOCATION()
    }
}
