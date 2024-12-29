package pl.instah.auron.appMethods

import pl.instah.auron.AuronRuntimeManager

internal actual fun quitApp() {
    AuronRuntimeManager.quitApp?.invoke()
}