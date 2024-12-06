import pl.instah.auron.Permission

plugins {
    id("pl.instah.Auron-Gradle") version "1.0.0A"
    kotlin("multiplatform")
    id("com.android.application")
}

group = "com.example"

kotlin {
    auron {
        application("Example Application")

        Permission.CAMERA()
        Permission.LOCATION()
    }

    sourceSets {
        auronMain.dependencies {
            implementation(compose.material3)
            implementation(project(":Example-library"))
        }
    }
}
