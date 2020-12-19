package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RListMultimapReactive
import org.vitalyros.redisson.kotlin.coroutines.RListCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RListMultimapCoroutines

class ListMultimapCoroutinesReactive<K, V>(private val wrapped: RListMultimapReactive<K, V>) : RListMultimapCoroutines<K, V>, MultimapCoroutinesReactive<K, V>(wrapped) {
    override fun get(key: K): RListCoroutines<V> = ListCoroutinesReactive(wrapped.get(key))

    override suspend fun getAll(key: K): List<V> = wrapped.getAll(key).awaitSingle()

    override suspend fun removeAll(key: Any): List<V> = wrapped.removeAll(key).awaitSingle()

    override suspend fun replaceValues(key: K, values: Iterable<V>): List<V> = wrapped.replaceValues(key, values).awaitSingle()
}