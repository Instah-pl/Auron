package pl.instah.auron.permissions

import pl.instah.auron.ConfiguredPermission

@Deprecated("PermissionManager on desktop is not functional (Every permission is granted)")
actual object PermissionManager {
    actual suspend fun requestPermissions(
        vararg permissions: ConfiguredPermission
    ): PermissionDecisionResult = PermissionDecisionResult(
        permissions.toSet()
    )

    actual fun goToAppSettings() {}

    actual fun checkIsPermissionGranted(permission: ConfiguredPermission) = true

    actual fun checkIsManualInterventionRequired(permission: ConfiguredPermission) = false
}