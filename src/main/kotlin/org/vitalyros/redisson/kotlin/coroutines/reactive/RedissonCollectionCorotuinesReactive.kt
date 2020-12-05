package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RCollectionReactive
import org.vitalyros.redisson.kotlin.coroutines.RCollectionCoroutines

class RedissonCollectionCorotuinesReactive<V>(val wrapped: RCollectionReactive<V>): ExpireableCoroutinesReactive(wrapped), RCollectionCoroutines<V> {
    override suspend fun iterator(): Iterator<V> = wrapped.iterator().collectList().awaitSingle().iterator()

    override suspend fun retainAll(c: Collection<*>): Boolean = wrapped.retainAll(c).awaitSingle()

    override suspend fun removeAll(c: Collection<*>): Boolean = wrapped.removeAll(c).awaitSingle()

    override suspend fun contains(o: V): Boolean = wrapped.contains(o).awaitSingle()

    override suspend fun containsAll(c: Collection<*>): Boolean = wrapped.containsAll(c).awaitSingle()

    override suspend fun remove(o: V): Boolean = wrapped.remove(o).awaitSingle()

    override suspend fun size(): Int = wrapped.size().awaitSingle()

    override suspend fun add(e: V): Boolean = wrapped.add(e).awaitSingle()

    override suspend fun addAll(c: Collection<V>): Boolean = wrapped.addAll(c).awaitSingle()
}