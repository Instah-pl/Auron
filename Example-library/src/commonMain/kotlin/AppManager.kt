@file:Suppress("DEPRECATION")

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.sync.Mutex
import pl.instah.auron.ConfiguredPermission
import pl.instah.auron.permissions.PermissionManager

object AppManager {
    var state by mutableStateOf(0)
    val mutex = Mutex()

    suspend fun escalateGrantPermission() {
        if (mutex.isLocked) return
        mutex.lock()

        if (PermissionManager.checkIsPermissionGranted(ConfiguredPermission.LOCATION(true))) {
            state = 2
            mutex.unlock()
            return
        }

        if (PermissionManager.checkIsManualInterventionRequired(ConfiguredPermission.LOCATION(true))) {
            PermissionManager.goToAppSettings()
            mutex.unlock()
            return
        }

        val permissionResult = PermissionManager.requestPermissions(ConfiguredPermission.LOCATION(true))

        if (permissionResult.permissionsGranted.contains(ConfiguredPermission.LOCATION(true))) {
            state = 2
        }

        mutex.unlock()
    }

    suspend fun attemptGrantPermission() {
        if (mutex.isLocked) return
        mutex.lock()
        val lowPrecisionLocation = PermissionManager.checkIsPermissionGranted(ConfiguredPermission.LOCATION(false))
        if (PermissionManager.checkIsPermissionGranted(ConfiguredPermission.LOCATION(true))) {
            state = 2
            mutex.unlock()
            return
        }

        if (lowPrecisionLocation) {
            if (!PermissionManager.checkIsManualInterventionRequired(ConfiguredPermission.LOCATION(true))) {
                val permissionResult = PermissionManager.requestPermissions(ConfiguredPermission.LOCATION(true))
                state = if (permissionResult.permissionsGranted.contains(ConfiguredPermission.LOCATION(true))) 2 else 1
            } else {
                state = 1
            }


            mutex.unlock()
            return
        }

        if (PermissionManager.checkIsManualInterventionRequired(ConfiguredPermission.LOCATION(false))) {
            PermissionManager.goToAppSettings()
            mutex.unlock()
            return
        }

        val permissionResult = PermissionManager.requestPermissions(ConfiguredPermission.LOCATION(true))
        if (permissionResult.permissionsGranted.contains(ConfiguredPermission.LOCATION(true))) {
            state = 2
        }

        if (permissionResult.permissionsGranted.contains(ConfiguredPermission.LOCATION(false))) {
            state = 1
        }

        mutex.unlock()
    }
}