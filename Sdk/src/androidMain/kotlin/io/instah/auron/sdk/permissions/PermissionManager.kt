package io.instah.auron.sdk.permissions

import io.instah.auron.permissions.ConfiguredPermission
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import io.instah.auron.sdk.AuronRuntimeManager
import io.instah.auron.sdk.permissions.PermissionManagerCommon.onPermissionDecision
import io.instah.auron.sdk.runtimeManager.checkIsManualPermissionGrantRequired
import io.instah.auron.sdk.runtimeManager.checkIsPermissionGranted
import io.instah.auron.sdk.runtimeManager.goToAppSettings
import io.instah.auron.sdk.runtimeManager.permissionRequestLauncher

actual object PermissionManager {
    actual suspend fun requestPermissions(
        vararg permissions: ConfiguredPermission
    ): PermissionDecisionResult = coroutineScope {
        if (permissions.isEmpty()) throw Exception("You cannot request no permissions")
        val permissionsMappedWithConditionResults = permissions.associate {
            it.askCondition() to it
        }

        val signalAwait = async {
            onPermissionDecision.await()
        }

        AuronRuntimeManager.permissionRequestLauncher?.launch(permissionsMappedWithConditionResults.filter {
            it.key
        }.flatMap {
            it.value.underlyingPermissionNames
        }.toTypedArray())

        val result = signalAwait.await()

        return@coroutineScope result.copy(
            permissionsGranted = result.permissionsGranted + permissionsMappedWithConditionResults.filter {
                !it.key
            }.map { it.value }
        )
    }

    actual fun goToAppSettings() {
        AuronRuntimeManager.goToAppSettings?.invoke()
    }

    actual fun checkIsPermissionGranted(permission: ConfiguredPermission): Boolean {
        if (!permission.askCondition()) return true
        return !permission.underlyingPermissionNames.map {
            AuronRuntimeManager.checkIsPermissionGranted!!(it)
        }.any { !it }
    }

    actual fun checkIsManualInterventionRequired(permission: ConfiguredPermission): Boolean {
        if (!permission.askCondition()) return false

        val underlyingPermissionsToCheck = permission.underlyingPermissionNames.filter {
            !AuronRuntimeManager.checkIsPermissionGranted!!(it)
        }

        if (underlyingPermissionsToCheck.isEmpty()) return false

        return !underlyingPermissionsToCheck.mapNotNull { permissionName ->
            AuronRuntimeManager.checkIsManualPermissionGrantRequired?.invoke(permissionName)
        }.any { !it }
    }
}