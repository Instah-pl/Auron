import com.android.build.gradle.BaseExtension
import korlibs.io.file.std.get
import manifest.generateManifest
import org.jetbrains.kotlin.gradle.ExternalKotlinTargetApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@OptIn(ExternalKotlinTargetApi::class)
fun KotlinMultiplatformExtension.auron(
    auronConfiguration: AuronConfigScope.() -> Unit = {}
) {
    this.jvmToolchain(17)
    if (project.group.toString().isEmpty()) throw Exception("You must specify a group.")

    val scope = AuronConfigScope()
    auronConfiguration(scope)
    val config = scope.build()

    androidTarget()

    val androidExtension = project.extensions.findByName("android") as? BaseExtension
    androidExtension?.namespace = project.group.toString()
    androidExtension?.compileSdkVersion = "android-35"


    androidExtension?.defaultConfig {
        it.minSdk = 24
        it.targetSdk = 35

        if (!config.isALibrary) {
            it.applicationId = "${project.group}.${config.applicationId}"
        }
    }

    androidExtension?.buildFeatures?.compose = true

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
            implementation("androidx.activity:activity-compose:1.9.3")
        }
    }

    val generatedCodeDir = auronGeneratedMain?.kotlin?.srcDirs?.first()

    if (!config.isALibrary) {
        generatedCodeDir?.mkdirs()
        generatedCodeDir?.get("mainLink.kt")?.writeText(
            text = """
                    package pl.instah.auron
                
                    class MainLink {
                        val link: () -> Unit = { ${project.group}.main() }
                    }
                """.trimIndent()
        )
    }

    val sourceSetDirectory = auronGeneratedMain?.kotlin?.srcDirs?.first()?.parentFile
    sourceSetDirectory?.mkdirs()

    sourceSetDirectory?.get("AndroidManifest.xml")?.writeText(
        text = config.manifestConfig.generateManifest()
    )

    sourceSetDirectory?.get("AndroidManifest.xml")?.let { manifestFileNotNull ->
        androidExtension?.sourceSets?.getByName("main")
            ?.manifest?.srcFile(manifestFileNotNull.absolutePath)
    }

    sourceSets.getByName("androidMain") {
        it.dependsOn(auronMain)
        it.dependsOn(auronGeneratedMain!!)

        it.dependencies {
            if (config.isALibrary) {
                implementation(auron.sdk)
            } else {
                implementation(auron.appSdk)
            }
        }
    }
}