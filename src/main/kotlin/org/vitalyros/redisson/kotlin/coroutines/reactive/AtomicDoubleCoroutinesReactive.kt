package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RAtomicDoubleReactive
import org.vitalyros.redisson.kotlin.coroutines.RAtomicDoubleCoroutines

class AtomicDoubleCoroutinesReactive(val wrapped: RAtomicDoubleReactive): RAtomicDoubleCoroutines, ExpireableCoroutinesReactive(wrapped) {
    override suspend fun compareAndSet(expect: Double, update: Double): Boolean = wrapped.compareAndSet(expect, update).awaitSingle()

    override suspend fun addAndGet(delta: Double): Double = wrapped.addAndGet(delta).awaitSingle()

    override suspend fun decrementAndGet(): Double = wrapped.decrementAndGet().awaitSingle()

    override suspend fun get(): Double = wrapped.get().awaitSingle()

    override suspend fun getAndDelete(): Double = wrapped.getAndDelete().awaitSingle()

    override suspend fun getAndAdd(delta: Double): Double = wrapped.getAndAdd(delta).awaitSingle()

    override suspend fun getAndSet(newValue: Double): Double = wrapped.getAndSet(newValue).awaitSingle()

    override suspend fun incrementAndGet(): Double = wrapped.incrementAndGet().awaitSingle()

    override suspend fun getAndIncrement(): Double = wrapped.getAndIncrement().awaitSingle()

    override suspend fun getAndDecrement(): Double = wrapped.getAndDecrement().awaitSingle()

    override suspend fun set(newValue: Double) {
        wrapped.set(newValue).awaitSingleOrNull()
    }
}