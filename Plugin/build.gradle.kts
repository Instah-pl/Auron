import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    id("com.vanniktech.maven.publish") version "0.30.0"
    signing
}

gradlePlugin {
    plugins {
        create("Gradle") {
            id = "pl.instah.Auron-Gradle"
            implementationClass = "AuronPlugin"
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(group.toString(), "gradle-plugin", version.toString())

    pom {
        name = "Auron Gradle Plugin"
        description = "The Auron Gradle Plugin"

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

dependencies {
    implementation("org.jetbrains.kotlin.multiplatform:org.jetbrains.kotlin.multiplatform.gradle.plugin:2.1.0")
    implementation("org.gradle.kotlin:gradle-kotlin-dsl-plugins:latest.release")
    implementation("com.android.library:com.android.library.gradle.plugin:8.5.0")
    implementation("com.android.application:com.android.application.gradle.plugin:8.5.0")
    implementation("com.soywiz.korge:korge-core:latest.release")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.7.1")
    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.1.0")
    implementation(project(":Permissions"))
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

