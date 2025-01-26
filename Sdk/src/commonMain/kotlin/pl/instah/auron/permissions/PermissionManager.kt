package pl.instah.auron.permissions

import pl.instah.auron.ConfiguredPermission

expect object PermissionManager {
    suspend fun requestPermissions(
        vararg permissions: ConfiguredPermission
    ): PermissionDecisionResult

    fun goToAppSettings()

    fun checkIsPermissionGranted(permission: ConfiguredPermission): Boolean

    fun checkIsManualInterventionRequired(permission: ConfiguredPermission): Boolean
}