import pl.instah.auron.Plugin.BuildConfig

class Auron(
    version: String
) {
    val sdk = "pl.instah.auron:sdk:$version"
    val appSdk = "pl.instah.auron:app-sdk:$version"
    val permissions = "pl.instah.auron:permissions:$version"
}

val auron = Auron(BuildConfig.version)