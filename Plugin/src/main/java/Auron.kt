class Auron(
    version: String
) {
    val sdk = "pl.instah.auron:sdk:$version"
    val appSdk = "pl.instah.auron:app-sdk:$version"
    val permissions = "pl.instah.auron:permissions:$version"
}

val auron = Auron("1.0.3A")