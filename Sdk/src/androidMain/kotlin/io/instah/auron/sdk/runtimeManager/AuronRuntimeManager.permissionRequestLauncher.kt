package io.instah.auron.sdk.runtimeManager

import androidx.activity.result.ActivityResultLauncher
import io.instah.auron.sdk.AuronRuntimeManager

private var permissionRequestLauncherValue: ActivityResultLauncher<Array<String>>? = null
var AuronRuntimeManager.permissionRequestLauncher: ActivityResultLauncher<Array<String>>?
    get() = permissionRequestLauncherValue
    set(value) { permissionRequestLauncherValue = value }