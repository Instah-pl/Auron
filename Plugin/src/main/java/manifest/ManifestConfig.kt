package manifest

data class ManifestConfig(
    val permissions: List<String>,
    val usedFeatures: List<String>,

    val applicationConfig: ManifestApplicationConfig? = null
) {
    data class ManifestApplicationConfig(
        val name: String,
        val additionalApplicationSectionFragments: List<String>
    )
}
