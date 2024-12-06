package pl.instah.auron

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object PermissionManager {
    data class PermissionDecisionResult(
        val permissionsGranted: Set<ConfiguredPermission> = emptySet(),
        val permissionsDenied: Set<Permission> = emptySet()
    )

    var onPermissionDecision = Signal<PermissionDecisionResult>()

    suspend fun requestPermission(vararg permissions: Permission): PermissionDecisionResult = coroutineScope {
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

    fun goToAppSettings() {
        TODO()
    }

    fun checkIsPermissionGranted() {
        TODO()
    }
}