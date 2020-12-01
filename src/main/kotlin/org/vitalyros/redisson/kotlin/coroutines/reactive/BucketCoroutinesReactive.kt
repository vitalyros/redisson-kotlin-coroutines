package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.*
import org.redisson.api.RBucketReactive
import org.vitalyros.redisson.kotlin.coroutines.RBucketCoroutines
import java.util.concurrent.TimeUnit

class BucketCoroutinesReactive<V>(val wrapped: RBucketReactive<V>): RBucketCoroutines<V>, ExpireableCoroutinesReactive(wrapped) {
    override suspend fun size(): Long = wrapped.size().awaitSingle()

    override suspend fun trySet(value: V): Boolean = wrapped.trySet(value).awaitSingle()

    override suspend fun trySet(value: V, timeToLive: Long, timeUnit: TimeUnit): Boolean = wrapped.trySet(value, timeToLive, timeUnit).awaitSingle()

    override suspend fun setIfExists(value: V): Boolean = wrapped.setIfExists(value).awaitSingle()

    override suspend fun setIfExists(value: V, timeToLive: Long, timeUnit: TimeUnit): Boolean = wrapped.setIfExists(value, timeToLive, timeUnit).awaitSingle()

    override suspend fun compareAndSet(expect: V, update: V): Boolean = wrapped.compareAndSet(expect, update).awaitSingle()

    override suspend fun getAndSet(newValue: V): V? = wrapped.getAndSet(newValue).awaitSingleOrNull()

    override suspend fun getAndSet(value: V, timeToLive: Long, timeUnit: TimeUnit): V? = wrapped.getAndSet(value, timeToLive, timeUnit).awaitSingleOrNull()

    override suspend fun get(): V? = wrapped.get().awaitSingleOrNull()

    override suspend fun getAndDelete(): V? = wrapped.get().awaitSingleOrNull()

    override suspend fun set(value: V) {
        wrapped.set(value).awaitSingleOrNull()
    }

    override suspend fun set(value: V, timeToLive: Long, timeUnit: TimeUnit)  {
        wrapped.set(value, timeToLive, timeUnit).awaitSingleOrNull()
    }
}