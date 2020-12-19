package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RMultimapReactive
import org.vitalyros.redisson.kotlin.coroutines.RMultimapCoroutines

open class MultimapCoroutinesReactive<K, V>(private val wrapped: RMultimapReactive<K, V>) : RMultimapCoroutines<K, V>, ExpireableCoroutinesReactive(wrapped) {
    override suspend fun size(): Int = wrapped.size().awaitSingle()
    override suspend fun containsKey(key: Any): Boolean = wrapped.containsKey(key).awaitSingle()

    override suspend fun containsValue(value: Any): Boolean = wrapped.containsValue(value).awaitSingle()

    override suspend fun containsEntry(key: Any, value: Any): Boolean = wrapped.containsEntry(key, value).awaitSingle()
    override suspend fun put(key: K, value: V): Boolean = wrapped.put(key, value).awaitSingle()

    override suspend fun remove(key: Any, value: Any): Boolean = wrapped.remove(key, value).awaitSingle()

    override suspend fun putAll(key: K, values: Iterable<V>): Boolean = wrapped.putAll(key, values).awaitSingle()

    override suspend fun keySize(): Int = wrapped.keySize().awaitSingle()

    override suspend fun fastRemove(vararg keys: K): Long = wrapped.fastRemove(*keys).awaitSingle()

    override suspend fun readAllKeySet(): Set<K> = wrapped.readAllKeySet().awaitSingle()
}