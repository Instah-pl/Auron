package io.instah.auron.sdk

object App {
    val initialIntention by AuronRuntimeManager::initialIntention

    fun quit() {
        AuronRuntimeManager.quitApp?.invoke()
    }

    object Callbacks {
        val resume = Callback<() -> Unit>()
        val newIntention = Callback<(Map<String, String>) -> Unit>()
    }
}