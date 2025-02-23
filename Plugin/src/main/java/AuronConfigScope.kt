import io.instah.auron.permissions.Permission
import manifest.ManifestConfig

class AuronConfigScope {
    private var isLibrary = false
    private var applicationId: String? = null
    private var applicationName: String? = null
    private var permissions: MutableSet<Permission> = mutableSetOf()
    private var useCompose: Boolean = true
    private var additionalApplicationSectionFragments = listOf<String>()
    private var isMinificationEnabled = true

    fun library() {
        isLibrary = true
    }

    fun disableCompose() {
        useCompose = false
    }

    fun application(name: String) {
        applicationName = name
        isLibrary = false
    }

    fun application(
        name: String,
        configure: ApplicationConfigureScope.() -> Unit
    ) {
        val scope = ApplicationConfigureScope()
        applicationName = name
        isLibrary = false
        configure(scope)
        val appConfig = scope.build()
        applicationId = appConfig.appId
    }

    fun disableMinification() {
        isMinificationEnabled = false
    }

    fun manifest(
        configure: ManifestConfigureScope.() -> Unit
    ) {
        val scope = ManifestConfigureScope()
        configure(scope)
        val result = scope.build()
        additionalApplicationSectionFragments = result.applicationSectionAdditions
    }

    data class ManifestConfigureScopeResult(
        val applicationSectionAdditions: List<String>
    )

    class ManifestConfigureScope() {
        private var applicationSectionAdditions = mutableListOf<String>()

        fun addToApplicationSection(
            text: String
        ) {
            applicationSectionAdditions.add(text)
        }

        fun build(): ManifestConfigureScopeResult {
            return ManifestConfigureScopeResult(applicationSectionAdditions)
        }
    }

    class ApplicationConfigureScope() {
        private var appId: String? = null

        fun appId(id: String) {
            appId = id
        }

        class ApplicationConfiguration(
            val appId: String?
        )

        fun build(): ApplicationConfiguration {
            return ApplicationConfiguration(appId)
        }
    }

    data class AuronConfig(
        val isLibrary: Boolean,
        val applicationId: String?,
        val manifestConfig: ManifestConfig,
        val useCompose: Boolean,
        val isMinificationEnabled: Boolean
    )

    fun build(): AuronConfig {
        val appNameNotNull = applicationName ?: "My Application"

        return AuronConfig(
            isLibrary = isLibrary,
            applicationId = applicationId ?: appNameNotNull.lowercase().replace(" ", "_")
                .filter { (('0'..'9') + ('A'..'Z') + ('a'..'z') + '_').contains(it) },
            manifestConfig = ManifestConfig(
                permissions = permissions.flatMap { it.underlyingPermissionNames },
                applicationConfig = if (isLibrary) null
                else ManifestConfig.ManifestApplicationConfig(
                    name = appNameNotNull,
                    additionalApplicationSectionFragments = additionalApplicationSectionFragments
                ), usedFeatures = permissions.flatMap { it.underlyingUsedFeatureNames }
            ), useCompose = useCompose, isMinificationEnabled = isMinificationEnabled
        )
    }

    @Deprecated("Replaced with the unary plus operator (`+ Permission`)")
    operator fun Permission.invoke() {
        permissions.add(this)
    }

    operator fun Permission.unaryPlus() {
        permissions.add(this)
    }
}