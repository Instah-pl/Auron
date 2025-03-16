package io.instah.auron.gradlePlugin.android.manifest

data class AndroidManifestConfig(
    val permissions: List<String>,
    val usedFeatures: List<String>,
    val applicationConfig: AndroidManifestApplicationConfig? = null
) {
    data class AndroidManifestApplicationConfig(
        val name: String,
        val additionalApplicationSectionFragments: List<String>
    )
}
