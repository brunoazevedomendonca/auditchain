package data.database.infrastructure

import data.database.model.SourceDM
import org.jetbrains.exposed.dao.id.LongIdTable

const val DATABASE_URL = "jdbc:sqlite://C:/ots/debug/data.db" //debug
//const val DATABASE_URL = "jdbc:sqlite:./data.db" //prod
const val DATABASE_DRIVER = "org.sqlite.JDBC"

object TableAttestation : LongIdTable() {
    val dateStart = long("date_start")
    val dateEnd = long("date_end")
    val dataSource = enumerationByName("data_source", 30, SourceDM::class)
    val sourceParams = varchar("source_params", 255).nullable()
    val dateTimestamp = long("date_timestamp")
    val dataSignature = blob("data_signature")
    val otsData = blob("ots_data")
    val dateOtsComplete = long("date_ots_complete").nullable()

    init {
        index(true, dateStart, dateEnd, dataSource, sourceParams)
    }
}

object TableStampException : LongIdTable() {
    val dateStart = long("date_start")
    val dateEnd = long("date_end")
    val dataSource = enumerationByName("data_source", 30, SourceDM::class)
    val sourceParams = varchar("source_params", 255).nullable()
    val exception = varchar("exception", 150)
    val dateException = long("date_exception")
    val processed = bool("processed")
}