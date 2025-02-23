package io.instah.auron.sdk

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first

class Signal<T>() {
    private val sharedFlow = MutableSharedFlow<T>()

    suspend fun await(): T {
        return sharedFlow.first()
    }

    suspend fun awaitConditional(
        condition: (T) -> Boolean,
    ): T {
        while (true) {
            val value = sharedFlow.first()
            if (condition(value)) {
                return value
            }
        }
    }

    suspend fun emit(value: T) = sharedFlow.emit(value)
}