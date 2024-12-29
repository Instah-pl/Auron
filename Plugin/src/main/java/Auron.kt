class Auron(
    version: String
) {
    val sdk = "pl.instah.auron:Sdk:$version"
    val appSdk = "pl.instah.auron:Application-Sdk:$version"
}

val auron = Auron("1.0.1A")