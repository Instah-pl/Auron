package pl.instah.auron

sealed class ConfiguredPermission {
    data object CAMERA : ConfiguredPermission()
    data class LOCATION(val precise: Boolean) : ConfiguredPermission()

    fun permission() = when (this) {
        is CAMERA -> Permission.CAMERA
        is LOCATION -> Permission.LOCATION
    }
}