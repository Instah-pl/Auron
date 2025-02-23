import io.instah.auron.permissions.Permission

plugins {
    id("io.instah.Auron-Gradle") version "1.1.0A"
    kotlin("multiplatform")
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
