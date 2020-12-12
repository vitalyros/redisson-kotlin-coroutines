package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RMapReactive
import org.vitalyros.redisson.kotlin.coroutines.*
import java.util.function.BiFunction
import java.util.function.Function

class MapCoroutinesReactive<K, V>(val wrapped: RMapReactive<K, V>) : RMapCoroutines<K, V>, ExpireableCoroutinesReactive(wrapped) {
    override suspend fun merge(key: K, value: V, remappingFunction: BiFunction<in V, in V, out V?>): V? =
            wrapped.merge(key, value, remappingFunction).awaitSingleOrNull()

    override suspend fun compute(key: K, remappingFunction: BiFunction<in K, in V?, out V?>): V? =
            wrapped.compute(key, remappingFunction).awaitSingleOrNull()

    override suspend fun computeIfAbsent(key: K, mappingFunction: Function<in K, out V?>): V? =
            wrapped.computeIfAbsent(key, mappingFunction).awaitSingleOrNull()

    override suspend fun computeIfPresent(key: K, remappingFunction: BiFunction<in K, in V, out V?>): V? =
            wrapped.computeIfPresent(key, remappingFunction).awaitSingleOrNull()

    override suspend fun loadAll(replaceExistingValues: Boolean, parallelism: Int) {
        wrapped.loadAll(replaceExistingValues, parallelism).awaitSingleOrNull()
    }

    override suspend fun loadAll(keys: Set<K>, replaceExistingValues: Boolean, parallelism: Int) {
        wrapped.loadAll(keys, replaceExistingValues, parallelism).awaitSingleOrNull()
    }

    override suspend fun valueSize(key: K): Int = wrapped.valueSize(key).awaitSingle()

    override suspend fun getAll(keys: Set<K>): Map<K, V> = wrapped.getAll(keys).awaitSingle()

    override suspend fun putAll(map: Map<K, V>) {
        wrapped.putAll(map).awaitSingleOrNull()
    }

    override suspend fun addAndGet(key: K, delta: Number): V = wrapped.addAndGet(key, delta).awaitSingleOrNull()

    override suspend fun containsValue(value: Any): Boolean = wrapped.containsValue(value).awaitSingle()

    override suspend fun containsKey(key: Any): Boolean = wrapped.containsKey(key).awaitSingle()

    override suspend fun size(): Int = wrapped.size().awaitSingle()

    override suspend fun fastRemove(vararg keys: K): Long = wrapped.fastRemove(*keys).awaitSingle()

    override suspend fun fastPut(key: K, value: V): Boolean = wrapped.fastPut(key, value).awaitSingle()

    override suspend fun fastPutIfAbsent(key: K, value: V): Boolean = wrapped.fastPutIfAbsent(key, value).awaitSingle()

    override suspend fun readAllKeySet(): Set<K> = wrapped.readAllKeySet().awaitSingle()

    override suspend fun readAllValues(): Collection<V> = wrapped.readAllValues().awaitSingle()

    override suspend fun readAllEntrySet(): Set<MutableMap.MutableEntry<K, V>> = wrapped.readAllEntrySet().awaitSingle()

    override suspend fun readAllMap(): Map<K, V> = wrapped.readAllMap().awaitSingle()

    override suspend fun get(key: K): V? = wrapped.get(key).awaitSingleOrNull()

    override suspend fun put(key: K, value: V): V? = wrapped.put(key, value).awaitSingleOrNull()

    override suspend fun remove(key: K): V? = wrapped.remove(key).awaitSingleOrNull()

    override suspend fun replace(key: K, value: V): V? = wrapped.replace(key, value).awaitSingleOrNull()

    override suspend fun replace(key: K, oldValue: V, newValue: V): Boolean = wrapped.replace(key, oldValue, newValue).awaitSingle()

    override suspend fun remove(key: Any, value: Any): Boolean = wrapped.remove(key, value).awaitSingle()

    override suspend fun putIfAbsent(key: K, value: V): V? = wrapped.putIfAbsent(key, value).awaitSingleOrNull()

    override suspend fun entryIterator(): Iterator<MutableMap.MutableEntry<K, V>> = wrapped.entryIterator().collectList().awaitSingle().iterator()

    override suspend fun entryIterator(count: Int): Iterator<MutableMap.MutableEntry<K, V>> = wrapped.entryIterator(count).collectList().awaitSingle().iterator()

    override suspend fun entryIterator(pattern: String?): Iterator<MutableMap.MutableEntry<K, V>> = wrapped.entryIterator(pattern).collectList().awaitSingle().iterator()

    override suspend fun entryIterator(pattern: String?, count: Int): Iterator<MutableMap.MutableEntry<K, V>> = wrapped.entryIterator(pattern, count).collectList().awaitSingle().iterator()

    override suspend fun valueIterator(): Iterator<V> = wrapped.valueIterator().collectList().awaitSingle().iterator()

    override suspend fun valueIterator(count: Int): Iterator<V> = wrapped.valueIterator(count).collectList().awaitSingle().iterator()

    override suspend fun valueIterator(pattern: String?): Iterator<V> = wrapped.valueIterator(pattern).collectList().awaitSingle().iterator()

    override suspend fun valueIterator(pattern: String?, count: Int): Iterator<V> = wrapped.valueIterator(pattern, count).collectList().awaitSingle().iterator()

    override suspend fun keyIterator(): Iterator<K> = wrapped.keyIterator().collectList().awaitSingle().iterator()

    override suspend fun keyIterator(count: Int): Iterator<K> = wrapped.keyIterator(count).collectList().awaitSingle().iterator()

    override suspend fun keyIterator(pattern: String?): Iterator<K> = wrapped.keyIterator(pattern).collectList().awaitSingle().iterator()

    override suspend fun keyIterator(pattern: String?, count: Int): Iterator<K> = wrapped.keyIterator(pattern, count).collectList().awaitSingle().iterator()

    override fun getPermitExpirableSemaphore(key: K): RPermitExpirableSemaphoreCoroutines = PermitExpirableSemaphoreCoroutinesReactive(wrapped.getPermitExpirableSemaphore(key))

    override fun getSemaphore(key: K): RSemaphoreCoroutines = SemaphoreCoroutinesReactive(wrapped.getSemaphore(key))

    override fun getFairLock(key: K): RLockCoroutines = LockCoroutinesReactive(wrapped.getFairLock((key)))

    override fun getReadWriteLock(key: K): RReadWriteLockCoroutines = ReadWriteLockCoroutinesReactive(wrapped.getReadWriteLock((key)))

    override fun getLock(key: K): RLockCoroutines = LockCoroutinesReactive(wrapped.getLock(key))
}