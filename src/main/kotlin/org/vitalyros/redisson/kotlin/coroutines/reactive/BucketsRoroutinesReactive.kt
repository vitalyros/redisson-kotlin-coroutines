package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RBucketsReactive
import org.vitalyros.redisson.kotlin.coroutines.RBucketsCoroutines

class BucketsRoroutinesReactive(private val wrapped: RBucketsReactive): RBucketsCoroutines {
    override suspend fun <V> get(vararg keys: String): Map<String, V> = wrapped.get<V>(*keys).awaitSingle()

    override suspend fun trySet(buckets: Map<String, *>): Boolean = wrapped.trySet(buckets).awaitSingle()

    override suspend fun set(buckets: Map<String, *>) {
        wrapped.set(buckets).awaitSingleOrNull()
    }
}