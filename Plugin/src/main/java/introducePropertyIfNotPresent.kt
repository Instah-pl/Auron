import korlibs.io.file.VfsFile
import korlibs.io.lang.Properties
import kotlinx.coroutines.runBlocking

fun introducePropertyIfNotPresent(
    file: VfsFile,
    property: String,
    value: String
) {
    if (!runBlocking { file.exists() }) {
        runBlocking {
            file.writeString(
                Properties(mapOf(property to value)).toString()
            )
        }
    } else {
        val content = Properties.parseString(runBlocking { file.readString() })

        if (!content.contains(property)) {
            content[property] = value
            runBlocking { file.writeString(content.toString()) }
        }
    }
}