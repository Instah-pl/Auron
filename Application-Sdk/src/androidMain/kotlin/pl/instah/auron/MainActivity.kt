package pl.instah.auron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.instah.auron.runtimeManager.permissionRequestLauncher
import pl.instah.auron.utils.permissionNamesToConfiguredPermissions
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

        AuronRuntimeManager.permissionRequestLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, Boolean> ->
            val permissionsDenied = permissions.filter { !it.value }.map { it.key }

            CoroutineScope(Dispatchers.Default).launch {
                val permissionsGranted = permissionNamesToConfiguredPermissions(
                    permissionNames = permissions.filter { it.value }.map { it.key }.toSet()
                ).toSet()

                val permissionsGrantedCommon = permissionsGranted.map { it.permission() }

                PermissionManager.onPermissionDecision.emit(
                    PermissionManager.PermissionDecisionResult(
                        permissionsGranted = permissionsGranted, permissionsDenied = Permission.entries
                            .filter { permission ->
                                permission.underlyingPermissionNames.any { permissionsDenied.contains(it) }
                            }.filter { !permissionsGrantedCommon.contains(it) }.toSet()
                    )
                )
            }
        }

        AuronRuntimeAppManager.initSetContentLambda = {
            setContent(
                content = it
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
        throw Exception(permissions.toString())
    }
}
