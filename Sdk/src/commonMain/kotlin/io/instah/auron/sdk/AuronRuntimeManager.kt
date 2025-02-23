package io.instah.auron.sdk

object AuronRuntimeManager {
    var quitApp: (() -> Unit)? = null
    var initialIntention: Map<String, String> = mapOf()
}