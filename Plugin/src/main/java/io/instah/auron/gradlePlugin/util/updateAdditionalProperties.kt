package io.instah.auron.gradlePlugin.util

import io.instah.auron.gradlePlugin.AuronPlugin
import io.instah.auron.gradlePlugin.config.AuronAdditionalProperties
import io.instah.auron.gradlePlugin.config.AuronConfigScope
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.gradle.api.Project

internal fun Project.updateAdditionalProperties(
    config: AuronConfigScope
) = runBlocking {
    val newAdditionalProperties = AuronAdditionalProperties(
        useCompose = config.useCompose,
        isLibrary = config.isLibrary,
        targets = config.targets
    )

    val previousAdditionalProperties = if (AuronPlugin.additionalPropertiesFile.exists()) {
        Json.decodeFromString<AuronAdditionalProperties>(
            AuronPlugin.additionalPropertiesFile.readString()
        )
    } else null

    if (newAdditionalProperties != previousAdditionalProperties) {
        AuronPlugin.additionalPropertiesFile.writeString(
            AuronPlugin.prettyJson.encodeToString(
                newAdditionalProperties
            )
        )

        project.gradle.projectsEvaluated { }
    }
}