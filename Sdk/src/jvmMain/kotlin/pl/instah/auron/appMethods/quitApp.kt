package pl.instah.auron.appMethods

import pl.instah.auron.AuronRuntimeManager
import kotlin.system.exitProcess

internal actual fun quitApp() {
    (AuronRuntimeManager.quitApp?: { exitProcess(0) }).invoke()
}