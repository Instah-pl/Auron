import korlibs.io.file.std.localVfs
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.gradle.api.Plugin
import org.gradle.api.Project

class AuronPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        var useCompose = true

        val auronPropertiesFile = localVfs(target.projectDir.absolutePath)["auron.json"]
        runBlocking {
            if (auronPropertiesFile.exists()) {
                val additionalProperties = Json.decodeFromString<AuronAdditionalProperties>(
                    auronPropertiesFile.readString()
                )

                useCompose = additionalProperties.useCompose

                if (additionalProperties.isALibrary) {
                    target.pluginManager.apply("com.android.library")
                } else {
                    target.pluginManager.apply("com.android.application")
                }
            }
        }

        if (useCompose) {
            target.pluginManager.apply("org.jetbrains.compose")
            target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        }

        target.repositories.google()
        target.repositories.mavenCentral()
    }
}