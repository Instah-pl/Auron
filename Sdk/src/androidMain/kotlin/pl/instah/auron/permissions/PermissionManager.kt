package pl.instah.auron.permissions

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import pl.instah.auron.AuronRuntimeManager
import pl.instah.auron.ConfiguredPermission
import pl.instah.auron.permissions.PermissionManagerCommon.onPermissionDecision
import pl.instah.auron.runtimeManager.checkIsManualPermissionGrantRequired
import pl.instah.auron.runtimeManager.checkIsPermissionGranted
import pl.instah.auron.runtimeManager.goToAppSettings
import pl.instah.auron.runtimeManager.permissionRequestLauncher

actual object PermissionManager {
    actual suspend fun requestPermissions(
        vararg permissions: ConfiguredPermission
    ): PermissionDecisionResult = coroutineScope {
        if (permissions.isEmpty()) throw Exception("You cannot request no permissions")

        val signalAwait = async {
            onPermissionDecision.await()
        }

        AuronRuntimeManager.permissionRequestLauncher?.launch(permissions.flatMap {
            it.underlyingPermissionNames
        }.toTypedArray())

        val result = signalAwait.await()

        return@coroutineScope result
    }

    actual fun goToAppSettings() {
        AuronRuntimeManager.goToAppSettings?.invoke()
    }

    actual fun checkIsPermissionGranted(permission: ConfiguredPermission): Boolean {
        return !permission.underlyingPermissionNames.map {
            AuronRuntimeManager.checkIsPermissionGranted!!(it)
        }.any { !it }
    }

    actual fun checkIsManualInterventionRequired(permission: ConfiguredPermission): Boolean {
        val underlyingPermissionsToCheck = permission.underlyingPermissionNames.filter {
            !AuronRuntimeManager.checkIsPermissionGranted!!(it)
        }

        if (underlyingPermissionsToCheck.isEmpty()) return false

        return !underlyingPermissionsToCheck.mapNotNull { permissionName ->
            AuronRuntimeManager.checkIsManualPermissionGrantRequired?.invoke(permissionName)
        }.any { !it }
    }
}