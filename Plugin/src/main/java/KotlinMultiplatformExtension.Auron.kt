import com.android.build.gradle.BaseExtension
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import korlibs.io.file.std.get
import korlibs.io.file.std.localVfs
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import manifest.generateManifest
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.ExternalKotlinTargetApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

@OptIn(ExternalKotlinTargetApi::class)
fun KotlinMultiplatformExtension.auron(
    auronConfiguration: AuronConfigScope.() -> Unit = {}
) {
    val auronPropertiesFile = localVfs(project.projectDir.absolutePath)["auron.json"]

    if (project.group.toString().isEmpty()) throw Exception("You must specify a group.")

    val scope = AuronConfigScope()
    auronConfiguration(scope)
    val config = scope.build()

    val newAdditionalProperties = AuronAdditionalProperties(
        useCompose = config.useCompose,
        isALibrary = config.isLibrary
    )

    val json = Json {
        prettyPrint = true
    }

    var stopExecution = false

    runBlocking {
        if (!auronPropertiesFile.exists()) {
            auronPropertiesFile.writeString(
                json.encodeToString(
                    newAdditionalProperties
                )
            )

            stopExecution = true
            project.gradle.projectsEvaluated { }
        } else {
            if (Json.decodeFromString<AuronAdditionalProperties>(auronPropertiesFile.readString())
                != newAdditionalProperties
            ) {
                auronPropertiesFile.writeString(
                    json.encodeToString(
                        newAdditionalProperties
                    )
                )

                stopExecution = true
                project.gradle.projectsEvaluated { }
            }
        }
    }

    if (stopExecution) {
        return
    }

    this.jvmToolchain(17)
    androidTarget()

    introducePropertyIfNotPresent(
        file = localVfs(this.project.rootProject.projectDir.absolutePath)["local.properties"],
        property = "sdk.dir", value = getSdkDir()
    )

    introducePropertyIfNotPresent(
        file = localVfs(this.project.rootProject.projectDir.absolutePath)["gradle.properties"],
        property = "android.useAndroidX", value = "true"
    )

    val androidExtension = project.extensions.findByName("android") as? BaseExtension
    androidExtension?.namespace = project.group.toString()
    androidExtension?.compileSdkVersion = "android-35"

    androidExtension?.defaultConfig {
        it.minSdk = 24
        it.targetSdk = 35

        if (!config.isLibrary) {
            it.applicationId = "${project.group}.${config.applicationId}"
        }
    }

    if (!config.isLibrary) {
        androidExtension?.buildTypes {
            it.getByName("release") {
                it.setMinifyEnabled(config.isMinificationEnabled)
                it.isShrinkResources = true
                it.proguardFiles(
                    getDefaultProguardFile("proguard-android.txt", project.layout.buildDirectory),
                    File(localVfs(project.projectDir.absolutePath)["auron.pro"].absolutePath)
                )
            }
        }
    }

    androidExtension?.compileOptions {
        it.sourceCompatibility = JavaVersion.VERSION_17
        it.targetCompatibility = JavaVersion.VERSION_17
    }

    if (config.useCompose) {
        androidExtension?.buildFeatures?.compose = true
    }

    val auronMain = sourceSets.create("auronMain") {
        it.kotlin.srcDir("src/auronMain/kotlin")
        it.resources.srcDir("src/auronMain/resources")

        it.dependsOn(sourceSets.getByName("commonMain"))
    }

    val auronGeneratedMain: KotlinSourceSet? = sourceSets.create("auronGeneratedMain") {
        it.kotlin.srcDir("src/auronGeneratedMain/kotlin")
        it.resources.srcDir("src/auronGeneratedMain/resources")
        it.dependsOn(auronMain)

        it.dependencies {
            implementation("androidx.activity:activity-compose:1.10.0")
        }
    }

    val generatedCodeDir = auronGeneratedMain?.kotlin?.srcDirs?.first()

    if (!config.isLibrary) {
        generatedCodeDir?.mkdirs()
        generatedCodeDir?.get("mainLink.kt")?.writeText(
            text = """
                    package io.instah.auron
                
                    class MainLink {
                        val link: () -> Unit = { ${project.group}.main() }
                    }
                """.trimIndent()
        )
    }

    val sourceSetDirectory = auronGeneratedMain?.kotlin?.srcDirs?.first()?.parentFile
    sourceSetDirectory?.mkdirs()

    sourceSetDirectory?.get("AndroidManifest.xml")?.writeText(
        text = config.manifestConfig.generateManifest(localVfs(project.projectDir.absolutePath))
    )

    sourceSetDirectory?.get("AndroidManifest.xml")?.let { manifestFileNotNull ->
        androidExtension?.sourceSets?.getByName("main")
            ?.manifest?.srcFile(manifestFileNotNull.absolutePath)
    }

    sourceSets.getByName("androidMain") {
        it.dependsOn(auronMain)
        it.dependsOn(auronGeneratedMain!!)

        it.dependencies {
            if (config.isLibrary) {
                implementation(auron.sdk)
            } else {
                implementation(auron.appSdk)
            }
        }
    }

    //TODO: Finish this
    /*//TODO: support SVG
    val generateAppIconTask = project.task("generateAppIcon")

    generateAppIconTask.doLast {
        val commonMain = sourceSets.getByName("commonMain")
        val auronMainResourceNames = auronMain.resources.map { it.name }
        val commonMainResourceNames = commonMain.resources.map { it.name }

        val iconFiles = if (auronMainResourceNames.contains("icon-foreground.png")
            && auronMainResourceNames.contains("icon-background.png")
        ) {
            auronMain.resources.files.first { it.name == "icon-foreground.png" } to
                    auronMain.resources.files.first { it.name == "icon-background.png" }
        } else if (commonMainResourceNames.contains("icon-foreground.png")
            && commonMainResourceNames.contains("icon-background.png")
        ) {
            commonMain.resources.files.first { it.name == "icon-foreground.png" } to
                    commonMain.resources.files.first { it.name == "icon-background.png" }
        } else null

        val resDir = localVfs(auronGeneratedMain?.kotlin?.first()?.absolutePath!!).parent["res"]
        runBlocking { resDir.mkdirs() }
    }*/

    //project.tasks.getByName("build").dependsOn(generateAppIconTask)
}