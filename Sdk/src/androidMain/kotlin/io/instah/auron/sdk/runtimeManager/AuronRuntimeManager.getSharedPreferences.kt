package io.instah.auron.sdk.runtimeManager

import android.content.SharedPreferences
import io.instah.auron.sdk.AuronRuntimeManager

private var getSharedPreferencesValue: ((String) -> SharedPreferences)? = null
var AuronRuntimeManager.getSharedPreferences: ((String) -> SharedPreferences)?
    get() = getSharedPreferencesValue
    set(value) { getSharedPreferencesValue = value }