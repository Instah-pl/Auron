import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish")
    signing
}

kotlin {
    jvmToolchain(17)

    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            api("androidx.activity:activity-compose:1.10.0")
        }

        commonMain.dependencies {
            api(project(":Sdk"))
            implementation(compose.foundation)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(group.toString(), "app-sdk", version.toString())

    pom {
        name = "Auron App SDK"
        description = "The application SDK for Auron"

        inceptionYear = "2023"
        url = "https://github.com/instah-pl/auron"

        developers {
            developer {
                id = "rebokdev"
                name = "rebokdev"
                email = "rebok@duck.com"
            }
        }

        licenses {
            license {
                name = "MIT license"
                url = "https://opensource.org/licenses/MIT"
            }
        }

        scm {
            connection = "scm:git:https://github.com/instah-pl/auron.git"
            developerConnection = "scm:git:git@github.com:instah-pl/auron.git"
            url = "https://github.com/instah-pl/auron"
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

signing {
    useGpgCmd()
    sign(publishing.publications)
}