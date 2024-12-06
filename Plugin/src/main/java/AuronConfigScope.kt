import pl.instah.auron.Permission
import manifest.ManifestConfig

class AuronConfigScope {
    private var isLibrary = false
    private var applicationId: String? = null
    private var applicationName: String? = null
    private var permissions: MutableSet<Permission> = mutableSetOf()

    fun library() {
        isLibrary = true
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
        val isALibrary: Boolean,
        val applicationId: String?,
        val manifestConfig: ManifestConfig
    )

    fun build(): AuronConfig {
        val appNameNotNull = applicationName?: "My Application"

        return AuronConfig(
            isALibrary  = isLibrary,
            applicationId = applicationId?: appNameNotNull.lowercase().replace(" ", "_")
                .filter { (('0'..'9') + ('A'..'Z') + ('a'..'z') + '_').contains(it) },
            manifestConfig = ManifestConfig(
                permissions = permissions.flatMap { it.underlyingPermissionNames },
                applicationConfig = if (isLibrary) null
                    else ManifestConfig.ManifestApplicationConfig(
                        name = appNameNotNull
                ), usedFeatures = permissions.flatMap { it.underlyingUsedFeatureNames }
            )
        )
    }

    operator fun Permission.invoke() {
        permissions.add(this)
    }
}