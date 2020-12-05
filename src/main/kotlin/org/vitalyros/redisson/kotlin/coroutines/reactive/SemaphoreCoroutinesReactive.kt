package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RSemaphoreReactive
import org.vitalyros.redisson.kotlin.coroutines.RBucketCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RSemaphoreCoroutines
import java.util.concurrent.TimeUnit

class SemaphoreCoroutinesReactive(val wrapped: RSemaphoreReactive) : ExpireableCoroutinesReactive(wrapped), RSemaphoreCoroutines {
    override suspend fun tryAcquire(): Boolean = wrapped.tryAcquire().awaitSingle()

    override suspend fun tryAcquire(permits: Int): Boolean = wrapped.tryAcquire(permits).awaitSingle()

    override suspend fun acquire() {
        wrapped.acquire().awaitSingleOrNull()
    }

    override suspend fun acquire(permits: Int) {
        wrapped.acquire(permits).awaitSingleOrNull()
    }

    override suspend fun release() {
        wrapped.release().awaitSingleOrNull()
    }

    override suspend fun release(permits: Int) {
        wrapped.release(permits).awaitSingleOrNull()
    }

    override suspend fun trySetPermits(permits: Int): Boolean = wrapped.trySetPermits(permits).awaitSingle()

    override suspend fun tryAcquire(waitTime: Long, unit: TimeUnit): Boolean = wrapped.tryAcquire(waitTime, unit).awaitSingle()

    override suspend fun tryAcquire(permits: Int, waitTime: Long, unit: TimeUnit): Boolean = wrapped.tryAcquire(permits, waitTime, unit).awaitSingle()

    override suspend fun addPermits(permits: Int) {
        wrapped.addPermits(permits).awaitSingleOrNull()
    }

    override suspend fun availablePermits(): Int = wrapped.availablePermits().awaitSingle()

    override suspend fun drainPermits(): Int = wrapped.drainPermits().awaitSingle()
}