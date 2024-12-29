package pl.instah.auron.utils
import pl.instah.auron.ConfiguredPermission

fun permissionNamesToConfiguredPermissions(permissionNames: Set<String>): List<ConfiguredPermission> {
    var result = mutableListOf<ConfiguredPermission>()

    if (permissionNames.contains("android.permission.ACCESS_COARSE_LOCATION")) {
        result.add(
            ConfiguredPermission.LOCATION(permissionNames.contains("android.permission.ACCESS_FINE_LOCATION"))
        )
    }

    if (permissionNames.contains("android.permission.CAMERA")) result.add(ConfiguredPermission.CAMERA)

    return result
}