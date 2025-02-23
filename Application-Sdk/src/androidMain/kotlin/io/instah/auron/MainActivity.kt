package io.instah.auron

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import io.instah.auron.sdk.App
import io.instah.auron.sdk.AuronRuntimeManager
import io.instah.auron.permissions.ConfiguredPermission
import io.instah.auron.sdk.permissions.PermissionDecisionResult
import io.instah.auron.sdk.permissions.PermissionManager
import io.instah.auron.sdk.permissions.PermissionManagerCommon
import io.instah.auron.sdk.runtimeManager.checkIsManualPermissionGrantRequired
import io.instah.auron.sdk.runtimeManager.checkIsPermissionGranted
import io.instah.auron.sdk.runtimeManager.executeInActivity
import io.instah.auron.sdk.runtimeManager.getSharedPreferences
import io.instah.auron.sdk.runtimeManager.goToAppSettings
import io.instah.auron.sdk.runtimeManager.permissionRequestLauncher
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AuronRuntimeManager.checkIsPermissionGranted = {
            this.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }

        AuronRuntimeManager.getSharedPreferences = { getSharedPreferences(it, MODE_PRIVATE) }

        AuronRuntimeManager.checkIsManualPermissionGrantRequired = lambda@{
            if (
                getSharedPreferences("auron:permission-data", MODE_PRIVATE)
                    .getBoolean("initial-permission-grant-${it}", true)
            ) {
                return@lambda false
            }

            return@lambda !shouldShowRequestPermissionRationale(it)
        }

        AuronRuntimeManager.goToAppSettings = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${baseContext.packageName}")
            startActivity(intent)
        }

        val keys = intent.extras?.keySet() ?: setOf()
        AuronRuntimeManager.initialIntention = keys.mapNotNull { key ->
            intent.getStringExtra(key)?.let { value ->
                key to value
            }
        }.toMap()

        AuronRuntimeManager.permissionRequestLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            CoroutineScope(Dispatchers.Default).launch {
                permissions.map { it.key }.forEach { permissionName ->
                    if (
                        getSharedPreferences("auron:permission-data", MODE_PRIVATE)
                            .getBoolean("initial-permission-grant-$permissionName", true)
                    ) {
                        AuronRuntimeAppManager.permissionDataPreferencesMutex.withLock {
                            getSharedPreferences("auron:permission-data", MODE_PRIVATE).edit().putBoolean(
                                "initial-permission-grant-$permissionName", false
                            ).apply()
                        }
                    }
                }

                PermissionManagerCommon.onPermissionDecision.emit(
                    PermissionDecisionResult(
                        permissionsGranted = ConfiguredPermission.getPermissionInstances(
                            permissions.filter { it.value }.map { it.key }
                        ), permissionsDeniedAdvanced = ConfiguredPermission
                            .getPermissionInstancesBasedOnPartialPermissionNames(
                                permissions.filter { !it.value }.map { it.key }
                            ).associate { permission ->
                                PermissionManager.checkIsManualInterventionRequired(permission) to permission
                            }
                    )
                )
            }
        }

        AuronRuntimeManager.executeInActivity = {
            it(this@MainActivity)
        }

        AuronRuntimeAppManager.initSetContentLambda = { content ->
            setContent(
                content = content
            )
        }

        AuronRuntimeManager.quitApp = {
            this.finish()
            exitProcess(0)
        }

        MainLink().link()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }

    override fun onResume() {
        super.onResume()
        App.Callbacks.resume.registered.forEach {
            it.value()
        }
    }

    //TODO: Handle invalid Intents
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val keys = intent.extras?.keySet() ?: setOf()
        if (keys.isEmpty()) return

        val values = keys.associate { it to intent.getStringExtra(it)!! }

        App.Callbacks.newIntention.registered.forEach { registeredHandler ->
            registeredHandler.value(values)
        }
    }
}
