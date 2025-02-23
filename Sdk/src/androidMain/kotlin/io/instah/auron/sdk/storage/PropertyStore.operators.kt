//TODO: Add dedicated type serializers
package io.instah.auron.sdk.storage

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import io.instah.auron.sdk.AuronRuntimeManager
import io.instah.auron.sdk.runtimeManager.getSharedPreferences
import kotlin.reflect.KProperty

@OptIn(ExperimentalSerializationApi::class)
actual inline operator fun <reified T> PropertyStore.field<T>.getValue(thisRef: Any?, property: KProperty<*>): T =
    runBlocking {
        this@getValue.parent.mutex.withLock {
            val value = AuronRuntimeManager.getSharedPreferences!!("app:${this@getValue.parent.name}").getString(
                property.name, "0"
            )!!

            if (value == "0") {
                return@runBlocking this@getValue.default
            }

            if (value == "1") {
                return@runBlocking null as T
            }

            //value is 2 then
            return@runBlocking Json.decodeFromString<T>(value.drop(1))
        }
    }

@OptIn(ExperimentalSerializationApi::class)
actual inline operator fun <reified T> PropertyStore.field<T>.setValue(
    thisRef: Any?,
    property: KProperty<*>,
    value: T
) =
    runBlocking {
        this@setValue.parent.mutex.withLock {
            var result = if (value == null) {
                "1"
            } else {
                "2" + Json.encodeToString(value)
            }

            AuronRuntimeManager.getSharedPreferences!!("app:${this@setValue.parent.name}").edit().putString(
                property.name, result
            ).apply()
        }
    }