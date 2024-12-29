import korlibs.io.file.VfsFile
import korlibs.io.file.std.userHomeVfs
import kotlinx.coroutines.runBlocking

fun getSdkDir(): String {
    val os = System.getProperty("os.name")

    fun VfsFile.onlyWhenExists(): VfsFile? = if (runBlocking { exists() }) this else null
    val noSdkError = Exception("We couldn't find your android SDK, please configure it in the local.properties file")

    if (os.contains("Linux", true)) {
        return userHomeVfs["Android/Sdk"].onlyWhenExists()?.absolutePath ?: throw noSdkError
    } else if (os.contains("Mac", true)) {
        return userHomeVfs["Library/Android/sdk"].onlyWhenExists()?.absolutePath ?: throw noSdkError
    } else if (os.contains("Windows", true)) {
        return userHomeVfs["AppData"]["Local"]["Android"]["Sdk"].onlyWhenExists()?.absolutePath ?: throw noSdkError
    }

    throw noSdkError
}