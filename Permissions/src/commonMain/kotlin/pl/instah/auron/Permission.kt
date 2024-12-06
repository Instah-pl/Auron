package pl.instah.auron

enum class Permission(
    val underlyingPermissionNames: List<String>,
    val underlyingUsedFeatureNames: List<String> = listOf()
) {
    LOCATION(listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION")),
    CAMERA(listOf("android.permission.CAMERA"), listOf("android.hardware.camera"))
}