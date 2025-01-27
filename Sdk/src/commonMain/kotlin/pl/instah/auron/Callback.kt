package pl.instah.auron

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class Callback<T : Any>() {
    private val modificationMutex = Mutex()
    var registered: MutableMap<String, T> = mutableMapOf()

    @OptIn(ExperimentalUuidApi::class)
    suspend fun register(
        callback: T
    ): String = modificationMutex.withLock {
        val uuid = Uuid.random().toString()
        registered[uuid] = callback
        return@withLock uuid
    }

    suspend fun remove(uuid: String) = modificationMutex.withLock {
        registered.remove(uuid)
    }
}