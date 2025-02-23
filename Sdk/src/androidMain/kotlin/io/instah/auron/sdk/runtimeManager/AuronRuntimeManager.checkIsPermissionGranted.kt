package io.instah.auron.sdk.runtimeManager

import io.instah.auron.sdk.AuronRuntimeManager

private var checkIsPermissionGrantedValue: ((String) -> Boolean)? = null
var AuronRuntimeManager.checkIsPermissionGranted: ((String) -> Boolean)?
    get() = checkIsPermissionGrantedValue
    set(value) { checkIsPermissionGrantedValue = value }