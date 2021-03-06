package data.repository

import data.remote.ElasticsearchRemoteDataSource
import domain.datarepository.ElasticsearchDataRepository
import domain.model.ElasticsearchConfiguration
import domain.model.TimeInterval
import domain.utility.toDateFormat
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ElasticsearchRepository @Inject constructor(
    private val elasticsearchConfiguration: ElasticsearchConfiguration,
    private val elasticsearchRemoteDataSource: ElasticsearchRemoteDataSource
) : ElasticsearchDataRepository {

    override fun getElasticsearchData(timeInterval: TimeInterval): Single<ByteArray> =
        elasticsearchRemoteDataSource.getLogs(
            elasticsearchConfiguration.indexPattern,
            timeInterval.toElasticsearchQuery()
        ).map { handleResultData(it) }

    private fun handleResultData(data: String): ByteArray {
        val initLogs = "\"hits\":["
        val result = data.substring((data.indexOf(initLogs) + initLogs.length - 1), data.lastIndexOf("]") + 1)
        return if (result.isNotEmpty() && result != "[]")
            result.toByteArray()
        else
            byteArrayOf()
    }

    private fun TimeInterval.toElasticsearchQuery() =
        "@timestamp:[${startAt.toDateFormat()} TO ${finishIn.toDateFormat()}]"
}