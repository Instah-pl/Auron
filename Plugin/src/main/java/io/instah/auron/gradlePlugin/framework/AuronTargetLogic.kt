package io.instah.auron.gradlePlugin.framework

import io.instah.auron.gradlePlugin.config.AuronConfigScope
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

abstract class AuronTargetLogic(
    val belongsToSourceSetGroup: String? = null
) {
    lateinit var config: AuronConfigScope
    abstract fun executeInKotlinBlock(kotlinExtension: KotlinMultiplatformExtension) : KotlinSourceSet
    open fun executeInProject(project: Project) {}
}