package pl.instah.auron.runtimeManager

import pl.instah.auron.AuronRuntimeManager

private var checkIsPermissionGrantedValue: ((String) -> Boolean)? = null
var AuronRuntimeManager.checkIsPermissionGranted: ((String) -> Boolean)?
    get() = checkIsPermissionGrantedValue
    set(value) { checkIsPermissionGrantedValue = value }