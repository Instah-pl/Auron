package pl.instah.auron

sealed class ConfiguredPermission(
    val permission: Permission,
    val underlyingPermissionNames: Set<String> = permission.underlyingPermissionNames
) {
    internal var initialUnderlyingPermissions: Set<String> = underlyingPermissionNames

    data object CAMERA : ConfiguredPermission(Permission.CAMERA)

    data class LOCATION(val precise: Boolean) : ConfiguredPermission(
        permission = Permission.LOCATION,
        underlyingPermissionNames = setOf("android.permission.ACCESS_COARSE_LOCATION")
                + (if (precise) setOf("android.permission.ACCESS_FINE_LOCATION") else emptySet())
    )

    @Deprecated("Depracted in favor of the permission property", ReplaceWith("permission"))
    fun permission() = when (this) {
        is CAMERA -> Permission.CAMERA
        is LOCATION -> Permission.LOCATION
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

            return result.toSet()
        }
    }
}