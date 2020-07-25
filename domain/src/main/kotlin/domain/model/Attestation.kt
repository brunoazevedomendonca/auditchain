package domain.model

data class Attestation(
    val timeInterval: TimeInterval,
    val source: Source,
    val dateTimestamp: Long,
    var otsData: ByteArray,
    var isOtsComplete: Boolean = false,
    val id: Long? = null
)