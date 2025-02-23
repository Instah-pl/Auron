package io.instah.auron.sdk.runtimeManager

import io.instah.auron.sdk.AuronRuntimeManager

private var checkIsManualPermissionGrantRequiredValue: ((String) -> Boolean)? = null
var AuronRuntimeManager.checkIsManualPermissionGrantRequired: ((String) -> Boolean)?
    get() = checkIsManualPermissionGrantRequiredValue
    set(value) { checkIsManualPermissionGrantRequiredValue = value }