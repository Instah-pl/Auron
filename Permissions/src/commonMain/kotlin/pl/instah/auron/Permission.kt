package pl.instah.auron

enum class Permission(
    val underlyingPermissionNames: Set<String>,
    val underlyingUsedFeatureNames: Set<String> = setOf()
) {
    LOCATION(setOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION")),
    CAMERA(setOf("android.permission.CAMERA"), setOf("android.hardware.camera"))
}