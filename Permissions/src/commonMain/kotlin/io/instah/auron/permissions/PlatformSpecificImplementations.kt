package io.instah.auron.permissions

expect object PlatformSpecificImplementations {
    fun minSdk(sdkVersion: Int): Boolean
}