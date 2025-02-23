package io.instah.auron.sdk.runtimeManager

import io.instah.auron.sdk.AuronRuntimeManager

private var goToAppSettingsValue: (() -> Unit)? = null
var AuronRuntimeManager.goToAppSettings: (() -> Unit)?
    get() = goToAppSettingsValue
    set(value) { goToAppSettingsValue = value }