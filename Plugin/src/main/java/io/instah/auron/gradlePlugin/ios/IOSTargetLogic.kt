package io.instah.auron.gradlePlugin.ios

import io.instah.auron.gradlePlugin.framework.AuronTargetLogic
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

//Maybe in the future
private class IOSTargetLogic : AuronTargetLogic("mobileMain") {
    override fun executeInKotlinBlock(kotlinExtension: KotlinMultiplatformExtension): KotlinSourceSet {
        TODO("Not yet implemented")
    }

    override fun executeInProject(project: Project) {
        TODO("Not yet implemented")
    }
}