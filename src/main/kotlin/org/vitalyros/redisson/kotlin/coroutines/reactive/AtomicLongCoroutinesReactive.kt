package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RAtomicLongReactive
import org.vitalyros.redisson.kotlin.coroutines.RAtomicLongCoroutines

class AtomicLongCoroutinesReactive(val wrapped: RAtomicLongReactive): RAtomicLongCoroutines, ExpireableCoroutinesReactive(wrapped) {
    override suspend fun compareAndSet(expect: Long, update: Long): Boolean = wrapped.compareAndSet(expect, update).awaitSingle()

    override suspend fun addAndGet(delta: Long): Long = wrapped.addAndGet(delta).awaitSingle()

    override suspend fun decrementAndGet(): Long = wrapped.decrementAndGet().awaitSingle()

    override suspend fun get(): Long = wrapped.get().awaitSingle()

    override suspend fun getAndDelete(): Long = wrapped.getAndDelete().awaitSingle()

    override suspend fun getAndAdd(delta: Long): Long = wrapped.getAndAdd(delta).awaitSingle()

    override suspend fun getAndSet(newValue: Long): Long = wrapped.getAndSet(newValue).awaitSingle()

    override suspend fun incrementAndGet(): Long = wrapped.incrementAndGet().awaitSingle()

    override suspend fun getAndIncrement(): Long = wrapped.getAndIncrement().awaitSingle()

    override suspend fun getAndDecrement(): Long = wrapped.getAndDecrement().awaitSingle()

    override suspend fun set(newValue: Long) {
        wrapped.set(newValue).awaitSingleOrNull()
    }
}