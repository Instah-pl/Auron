package pl.instah.auron

import android.content.Context
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
import pl.instah.auron.permissions.PermissionDecisionResult
import pl.instah.auron.permissions.PermissionManager
import pl.instah.auron.permissions.PermissionManagerCommon
import pl.instah.auron.runtimeManager.checkIsManualPermissionGrantRequired
import pl.instah.auron.runtimeManager.checkIsPermissionGranted
import pl.instah.auron.runtimeManager.goToAppSettings
import pl.instah.auron.runtimeManager.permissionRequestLauncher
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLinkClass = Class.forName("pl.instah.auron.MainLink")
        val field = mainLinkClass.getDeclaredField("link")
        val mainLinkClassInstance = mainLinkClass.getDeclaredConstructor().newInstance()
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val fieldValue = field.get(mainLinkClassInstance) as () -> Unit

        AuronRuntimeManager.checkIsPermissionGranted = {
            this.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }

        AuronRuntimeManager.checkIsManualPermissionGrantRequired = lambda@{
            if (
                getSharedPreferences("auron:app-data", MODE_PRIVATE)
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

        AuronRuntimeManager.permissionRequestLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            CoroutineScope(Dispatchers.Default).launch {
                permissions.map { it.key }.forEach { permissionName ->
                    if (
                        getSharedPreferences("auron:app-data", MODE_PRIVATE)
                            .getBoolean("initial-permission-grant-$permissionName", true)
                    ) {
                        getSharedPreferences("auron:app-data", MODE_PRIVATE).edit().putBoolean(
                            "initial-permission-grant-$permissionName", false
                        ).apply()
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

        AuronRuntimeAppManager.initSetContentLambda = { content ->
            setContent(
                content = content
            )
        }

        AuronRuntimeManager.quitApp = {
            this.finish()
            exitProcess(0)
        }

        fieldValue()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }
}
