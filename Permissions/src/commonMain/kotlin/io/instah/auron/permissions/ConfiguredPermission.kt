package io.instah.auron.permissions

sealed class ConfiguredPermission(
    val permission: Permission,
    val underlyingPermissionNames: Set<String> = permission.underlyingPermissionNames,
    val askCondition: () -> Boolean = { true }
) {
    data object CAMERA : ConfiguredPermission(Permission.CAMERA)

    data class LOCATION(val precise: Boolean) : ConfiguredPermission(
        permission = Permission.LOCATION,
        underlyingPermissionNames = setOf("android.permission.ACCESS_COARSE_LOCATION")
                + (if (precise) setOf("android.permission.ACCESS_FINE_LOCATION") else emptySet())
    )

    data object USE_NOTIFICATIONS : ConfiguredPermission(
        permission = Permission.USE_NOTIFICATIONS,
        askCondition = { PlatformSpecificImplementations.minSdk(33) }
    )

    data object REQUEST_APP_INSTALL : ConfiguredPermission(Permission.REQUEST_APP_INSTALL)

    @Deprecated("Depracted in favor of the permission property", ReplaceWith("permission"))
    fun permission() = when (this) {
        is CAMERA -> Permission.CAMERA
        is LOCATION -> Permission.LOCATION
        is USE_NOTIFICATIONS -> Permission.USE_NOTIFICATIONS
        is REQUEST_APP_INSTALL -> Permission.REQUEST_APP_INSTALL
    }

    companion object {
        fun getPermissionInstances(
            permissionNames: List<String>
        ): Set<ConfiguredPermission> {
            val result = mutableSetOf<ConfiguredPermission>()

            if (permissionNames.contains("android.permission.CAMERA")) {
                result.add(CAMERA)
            }

            if (permissionNames.contains("android.permission.ACCESS_COARSE_LOCATION")) {
                result.add(LOCATION(permissionNames.contains("android.permission.ACCESS_FINE_LOCATION")))
            }

            if (permissionNames.contains("android.permission.POST_NOTIFICATIONS")) {
                result.add(USE_NOTIFICATIONS)
            }

            if (permissionNames.contains("android.permission.REQUEST_INSTALL_PACKAGES")) {
                result.add(REQUEST_APP_INSTALL)
            }

            return result.toSet()
        }

        fun getPermissionInstancesBasedOnPartialPermissionNames(
            permissionNames: List<String>
        ): Set<ConfiguredPermission> {
            val result = mutableSetOf<ConfiguredPermission>()

            if (permissionNames.contains("android.permission.ACCESS_FINE_LOCATION")) {
                result.add(LOCATION(true))
            }

            if (permissionNames.contains("android.permission.CAMERA")) {
                result.add(CAMERA)
            }

            if (permissionNames.contains("android.permission.POST_NOTIFICATIONS")) {
                result.add(USE_NOTIFICATIONS)
            }

            if (permissionNames.contains("android.permission.REQUEST_INSTALL_PACKAGES")) {
                result.add(REQUEST_APP_INSTALL)
            }

            return result.toSet()
        }
    }
}