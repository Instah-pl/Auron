package io.instah.auron.gradlePlugin.util

import io.instah.auron.gradlePlugin.config.AuronConfigScope.DependencyType
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun KotlinDependencyHandler.addDependencies(
    dependencies: Set<Pair<DependencyType, Any>>
) = dependencies.forEach { dependency ->
    when (dependency.first) {
        DependencyType.Api -> api(dependency.second)
        DependencyType.Implementation -> implementation(dependency.second)
        DependencyType.CompileOnly ->  compileOnly(dependency.second)
    }
}