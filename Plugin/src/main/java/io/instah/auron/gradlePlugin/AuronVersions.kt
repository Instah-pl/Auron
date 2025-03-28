package io.instah.auron.gradlePlugin

class AuronVersions(
    val auronVersion: String
) {
    val sdk = "io.instah.auron:sdk:$auronVersion"
    val appSdk = "io.instah.auron:app-sdk:$auronVersion"
    val permissions = "io.instah.auron:permissions:$auronVersion"
    val voyager = Voyager

    object Voyager {
        val navigator = "cafe.adriel.voyager:voyager-navigator:latest.release"
        val screenModel = "cafe.adriel.voyager:voyager-screenmodel:latest.release"
        val bottomSheetNavigator = "cafe.adriel.voyager:voyager-bottom-sheet-navigator:latest.release"
        val tabNavigator = "cafe.adriel.voyager:voyager-tab-navigator:latest.release"
        val transitions = "cafe.adriel.voyager:voyager-transitions:latest.release"
        val koin = "cafe.adriel.voyager:voyager-koin:latest.release"
    }
}