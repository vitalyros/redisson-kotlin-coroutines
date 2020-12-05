package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.redisson.api.RReadWriteLockReactive
import org.vitalyros.redisson.kotlin.coroutines.RLockCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RReadWriteLockCoroutines

class ReadWriteLockCoroutinesReactive(val wrapped: RReadWriteLockReactive): RReadWriteLockCoroutines {
    override fun readLock(): RLockCoroutines = LockCoroutinesReactive(wrapped.readLock())

    override fun writeLock(): RLockCoroutines = LockCoroutinesReactive(wrapped.writeLock())
}