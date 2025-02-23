package io.instah.auron.permissions

import android.os.Build

actual object PlatformSpecificImplementations {
    actual fun minSdk(sdkVersion: Int) = sdkVersion <= Build.VERSION.SDK_INT
}