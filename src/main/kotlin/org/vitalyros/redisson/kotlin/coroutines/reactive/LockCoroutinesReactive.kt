package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RLockReactive
import org.vitalyros.redisson.kotlin.coroutines.RLockCoroutines
import java.util.concurrent.TimeUnit

class LockCoroutinesReactive(val wrapped: RLockReactive): RLockCoroutines {
    override val name: String
        get() = wrapped.name

    override suspend fun forceUnlock(): Boolean = wrapped.forceUnlock().awaitSingle()

    override suspend fun unlock() {
        wrapped.unlock().awaitSingleOrNull()
    }

    override suspend fun tryLock(): Boolean = wrapped.tryLock().awaitSingle()

    override suspend fun lock() {
        wrapped.lock().awaitSingleOrNull()
    }

    override suspend fun lock(leaseTime: Long, unit: TimeUnit) {
        wrapped.lock(leaseTime, unit).awaitSingleOrNull()
    }

    override suspend fun tryLock(waitTime: Long, unit: TimeUnit): Boolean = wrapped.tryLock(waitTime, unit).awaitSingle()

    override suspend fun tryLock(waitTime: Long, leaseTime: Long, unit: TimeUnit): Boolean = wrapped.tryLock(waitTime, leaseTime, unit).awaitSingle()

    override suspend fun isLocked(): Boolean = wrapped.isLocked.awaitSingle()

    override suspend fun remainTimeToLive(): Long = wrapped.remainTimeToLive().awaitSingle()
}