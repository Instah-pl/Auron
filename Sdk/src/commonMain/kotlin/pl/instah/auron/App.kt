package pl.instah.auron

import pl.instah.auron.appMethods.quitApp

object App {
    val quit = ::quitApp

    object Callbacks {
        val resume = Callback<() -> Unit>()
    }
}