package pl.instah.auron

import android.annotation.SuppressLint
import androidx.activity.result.ActivityResultLauncher

@SuppressLint("StaticFieldLeak")
object AuronRuntimeManager {
    var permissionRequestLauncher: ActivityResultLauncher<Array<String>>? = null
    var quitApp: () -> Unit = {}
}