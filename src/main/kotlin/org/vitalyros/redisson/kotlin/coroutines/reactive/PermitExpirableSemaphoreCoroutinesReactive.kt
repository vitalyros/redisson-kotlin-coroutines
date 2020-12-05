package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RPermitExpirableSemaphoreReactive
import org.vitalyros.redisson.kotlin.coroutines.RPermitExpirableSemaphoreCoroutines
import java.util.concurrent.TimeUnit

class PermitExpirableSemaphoreCoroutinesReactive(val wrapped: RPermitExpirableSemaphoreReactive): ExpireableCoroutinesReactive(wrapped), RPermitExpirableSemaphoreCoroutines {
    override suspend fun acquire(): String = wrapped.acquire().awaitSingle()

    override suspend fun acquire(leaseTime: Long, unit: TimeUnit): String = wrapped.acquire().awaitSingle()

    override suspend fun tryAcquire(): String? = wrapped.tryAcquire().awaitSingleOrNull()

    override suspend fun tryAcquire(waitTime: Long, unit: TimeUnit): String? = wrapped.tryAcquire(waitTime, unit).awaitSingleOrNull()

    override suspend fun tryAcquire(waitTime: Long, leaseTime: Long, unit: TimeUnit): String? = wrapped.tryAcquire(waitTime, leaseTime, unit).awaitSingleOrNull()

    override suspend fun tryRelease(permitId: String): Boolean = wrapped.tryRelease(permitId).awaitSingle()

    override suspend fun release(permitId: String) {
        wrapped.release(permitId).awaitSingleOrNull()
    }

    override suspend fun availablePermits(): Int = wrapped.availablePermits().awaitSingle()

    override suspend fun trySetPermits(permits: Int): Boolean = wrapped.trySetPermits(permits).awaitSingle()

    override suspend fun addPermits(permits: Int) {
        wrapped.addPermits(permits).awaitSingleOrNull()
    }

    override suspend fun updateLeaseTime(permitId: String, leaseTime: Long, unit: TimeUnit): Boolean = wrapped.updateLeaseTime(permitId, leaseTime, unit).awaitSingleOrNull()
}