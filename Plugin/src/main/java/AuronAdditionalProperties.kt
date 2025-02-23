import kotlinx.serialization.Serializable

@Serializable
data class AuronAdditionalProperties(
    val useCompose: Boolean,
    val isALibrary: Boolean
)