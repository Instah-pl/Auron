plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
}

gradlePlugin {
    plugins {
        create("Gradle") {
            id = "pl.instah.Auron-Gradle"
            implementationClass = "AuronPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
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

