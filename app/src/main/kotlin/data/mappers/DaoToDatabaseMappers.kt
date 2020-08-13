package data.mappers

import data.database.infrastructure.dao.AttestationDao
import data.database.infrastructure.dao.StampExceptionDao
import data.database.model.AttestationDM
import data.database.model.StampExceptionDM

// All methods involving DAOs must be called within a transaction

fun AttestationDao.toDatabaseModel(): AttestationDM =
    AttestationDM(
        dateStart,
        dateEnd,
        dataSource,
        dateTimestamp,
        otsData.bytes,
        isOtsComplete,
        previousAttestationSignature?.bytes,
        id.value
    )

fun StampExceptionDao.toDatabaseModel(): StampExceptionDM =
    StampExceptionDM(
        dateStart,
        dateEnd,
        dataSource,
        exception,
        dateException,
        processed,
        id.value
    )