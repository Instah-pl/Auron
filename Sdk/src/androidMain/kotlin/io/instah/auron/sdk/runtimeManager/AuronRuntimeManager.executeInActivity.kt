package io.instah.auron.sdk.runtimeManager

import androidx.activity.ComponentActivity
import io.instah.auron.sdk.AuronRuntimeManager

private var executeInActivityValue: ((ComponentActivity.() -> Any?) -> Any?)? = null
var AuronRuntimeManager.executeInActivity: ((ComponentActivity.() -> Any?) -> Any?)?
    get() = executeInActivityValue
    set(value) { executeInActivityValue = value }
