import pl.instah.auron.Plugin.BuildConfig

class Auron(
    val auronVersion: String
) {
    val sdk = "pl.instah.auron:sdk:$auronVersion"
    val appSdk = "pl.instah.auron:app-sdk:$auronVersion"
    val permissions = "pl.instah.auron:permissions:$auronVersion"

    object Voyager {
        val navigator = "cafe.adriel.voyager:voyager-navigator:latest.release"
        val screenModel = "cafe.adriel.voyager:voyager-screenmodel:latest.release"
        val bottomSheetNavigator = "cafe.adriel.voyager:voyager-bottom-sheet-navigator:latest.release"
        val tabNavigator = "cafe.adriel.voyager:voyager-tab-navigator:latest.release"
        val transitions = "cafe.adriel.voyager:voyager-transitions:latest.release"
        val koin = "cafe.adriel.voyager:voyager-koin:latest.release"
    }
}

val auron = Auron(
    auronVersion = BuildConfig.version
)