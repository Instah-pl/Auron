import io.instah.auron.permissions.Permission

plugins {
    id("io.instah.Auron-Gradle") version "1.1.0-SNAPSHOT"
}

//TODO: Separate SDK into SDK and SDK-COMPOSE
auron {
    library()

    dependencies {
        implementation(auron.voyager.navigator)
    }

    +Permission.LOCATION
}
