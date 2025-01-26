package pl.instah.auron.runtimeManager

import pl.instah.auron.AuronRuntimeManager

private var checkIsManualPermissionGrantRequiredValue: ((String) -> Boolean)? = null
var AuronRuntimeManager.checkIsManualPermissionGrantRequired: ((String) -> Boolean)?
    get() = checkIsManualPermissionGrantRequiredValue
    set(value) { checkIsManualPermissionGrantRequiredValue = value }