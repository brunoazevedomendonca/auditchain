package domain.usecase

import domain.di.ComputationScheduler
import domain.exception.MaxTimeIntervalExceededException
import domain.model.AttestationConfiguration
import domain.model.TimeInterval
import domain.utility.Logger
import domain.utility.getNextTimeInterval
import domain.utility.getPreviousTimeInterval
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetTimeIntervals @Inject constructor(
    private val attestationConfiguration: AttestationConfiguration,
    @ComputationScheduler private val executorScheduler: Scheduler,
    private val logger: Logger
) {
    fun getSingle(startAt: Long, finishIn: Long): Single<List<TimeInterval>> =
        Single.fromCallable<List<TimeInterval>> {
            val firstMomentInterval: Long =
                getPreviousTimeInterval(startAt, attestationConfiguration.frequencyMillis, false)
            val finishMomentInterval: Long =
                getNextTimeInterval(finishIn, attestationConfiguration.frequencyMillis, false)

            if(finishMomentInterval - firstMomentInterval > attestationConfiguration.maxTimeIntervalMillis)
                throw MaxTimeIntervalExceededException(TimeInterval(firstMomentInterval, finishMomentInterval))

            val intervalList = mutableListOf<TimeInterval>()
            var interval: Long = firstMomentInterval
            while (interval < finishMomentInterval) {
                val nextInterval = getNextTimeInterval(interval, attestationConfiguration.frequencyMillis)
                intervalList.add(TimeInterval(interval, nextInterval))
                interval = nextInterval
            }

            intervalList
        }.doOnError { logger.log("Error on ${this::class.qualifiedName}: $it") }
            .subscribeOn(executorScheduler)
}