@file:Suppress("EqualsOrHashCode")

package io.instah.auron.gradlePlugin.config

import AuronTarget
import kotlinx.serialization.Serializable

@Serializable
data class AuronAdditionalProperties(
    val targets: Set<AuronTarget> = setOf(),
    val useCompose: Boolean = false,
    val isLibrary: Boolean = true
)