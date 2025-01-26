package pl.instah.auron.permissions

import pl.instah.auron.ConfiguredPermission
import pl.instah.auron.Permission

data class PermissionDecisionResult(
    val permissionsGranted: Set<ConfiguredPermission> = emptySet(),
    //key == does the permission require manual intervention
    val permissionsDeniedAdvanced: Map<Boolean, ConfiguredPermission> = emptyMap()
) {
    val permissionDenied = permissionsDeniedAdvanced.values.toSet()

    fun checkIsManualInterventionRequired(permission: Permission): Boolean = permissionsDeniedAdvanced.toList()
        .firstOrNull {
            it.second.permission == permission
        }?.first == true

    fun getPermissionDenied(
        manualInterventionRequired: Boolean
    ) = permissionsDeniedAdvanced.filter { it.key == !manualInterventionRequired }
}