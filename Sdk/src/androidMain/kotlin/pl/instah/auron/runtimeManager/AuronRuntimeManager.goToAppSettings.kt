package pl.instah.auron.runtimeManager

import pl.instah.auron.AuronRuntimeManager

private var goToAppSettingsValue: (() -> Unit)? = null
var AuronRuntimeManager.goToAppSettings: (() -> Unit)?
    get() = goToAppSettingsValue
    set(value) { goToAppSettingsValue = value }