import org.gradle.api.Plugin
import org.gradle.api.Project

class AuronPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("org.jetbrains.compose")
        target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        target.repositories.google()
        target.repositories.mavenCentral()
    }
}