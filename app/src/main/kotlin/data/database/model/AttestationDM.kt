package data.database.model

data class AttestationDM(
    val dateStart: Long,
    val dateEnd: Long,
    val source: SourceDM,
    val dateTimestamp: Long,
    val otsData: ByteArray,
    val isOtsComplete: Boolean = false,
    val previousAttestationSignature: ByteArray? = null,
    val id: Long? = null
)