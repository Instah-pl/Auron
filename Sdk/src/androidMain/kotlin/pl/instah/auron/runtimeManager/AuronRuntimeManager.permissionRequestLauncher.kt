package pl.instah.auron.runtimeManager

import androidx.activity.result.ActivityResultLauncher
import pl.instah.auron.AuronRuntimeManager

private var permissionRequestLauncherValue: ActivityResultLauncher<Array<String>>? = null
var AuronRuntimeManager.permissionRequestLauncher: ActivityResultLauncher<Array<String>>?
    get() = permissionRequestLauncherValue
    set(value) { permissionRequestLauncherValue = value }