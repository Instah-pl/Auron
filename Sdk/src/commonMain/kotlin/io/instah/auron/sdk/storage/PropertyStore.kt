package io.instah.auron.sdk.storage

import kotlinx.coroutines.sync.Mutex

//TODO: Add a property store version system
abstract class PropertyStore(
    val name: String,
) {
    val mutex = Mutex()

    inner class field<T>(
        val default: T
    ) {
        var parent = this@PropertyStore
    }
}