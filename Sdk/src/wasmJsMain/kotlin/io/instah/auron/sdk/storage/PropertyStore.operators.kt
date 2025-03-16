package io.instah.auron.sdk.storage

import kotlin.reflect.KProperty

actual inline operator fun <reified T> PropertyStore.field<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
    TODO()
}

actual inline operator fun <reified T> PropertyStore.field<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    TODO()
}