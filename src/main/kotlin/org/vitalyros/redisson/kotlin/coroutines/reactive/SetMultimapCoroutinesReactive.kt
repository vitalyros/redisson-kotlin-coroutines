package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RSetMultimapReactive
import org.vitalyros.redisson.kotlin.coroutines.RSetCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RSetMultimapCoroutines
import java.lang.Exception
import kotlin.jvm.Throws

class SetMultimapCoroutinesReactive<K, V>(val wrapped: RSetMultimapReactive<K, V>) : RSetMultimapCoroutines<K, V>, MultimapCoroutinesReactive<K, V>(wrapped) {
    @Throws(Exception::class)
    override fun get(key: K): RSetCoroutines<V> = SetCoroutinesReactive(wrapped.get(key))
    override suspend fun getAll(key: K): Set<V> = wrapped.getAll(key).awaitSingle()
    override suspend fun removeAll(key: Any): Set<V> = wrapped.removeAll(key).awaitSingle()
    override suspend fun replaceValues(key: K, values: Iterable<V>): Set<V> = wrapped.replaceValues(key, values).awaitSingle()
}