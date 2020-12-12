/**
 * Copyright (c) 2013-2020 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vitalyros.redisson.kotlin.coroutines

import java.util.function.BiFunction
import java.util.function.Function

/**
 * This map uses serialized state of key instead of hashCode or equals methods.
 * This map doesn't allow to store `null` as key or value.
 *
 * Based on the original org.redisson.api.RMapReactive by Nikita Koksharov
 * @see org.redisson.api.RLockReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <K> key
 * @param <V> value
</V></K> */
interface RMapCoroutines<K, V> : RExpirableCoroutines {
    /**
     * Associates specified key with the given value if key isn't already associated with a value.
     * Otherwise, replaces the associated value with the results of the given
     * remapping function, or removes if the result is `null`.
     *
     * @param key - map key
     * @param value - value to be merged with the existing value
     * associated with the key or to be associated with the key,
     * if no existing value
     * @param remappingFunction - the function is invoked with the existing value to compute new value
     * @return new value associated with the specified key or
     * `null` if no value associated with the key
     */
    suspend fun merge(key: K, value: V, remappingFunction: BiFunction<in V, in V, out V?>): V?

    /**
     * Computes a new mapping for the specified key and its current mapped value.
     *
     * @param key - map key
     * @param remappingFunction - function to compute a value
     * @return the new value associated with the specified key, or `null` if none
     */
    suspend fun compute(key: K, remappingFunction: BiFunction<in K, in V?, out V?>): V?

    /**
     * Computes a mapping for the specified key if it's not mapped before.
     *
     * @param key - map key
     * @param mappingFunction - function to compute a value
     * @return current or new computed value associated with
     * the specified key, or `null` if the computed value is null
     */
    suspend fun computeIfAbsent(key: K, mappingFunction: Function<in K, out V?>): V?

    /**
     * Computes a mapping for the specified key only if it's already mapped.
     *
     * @param key - map key
     * @param remappingFunction - function to compute a value
     * @return the new value associated with the specified key, or null if none
     */
    suspend fun computeIfPresent(key: K, remappingFunction: BiFunction<in K, in V, out V?>): V?

    /**
     * Loads all map entries to this Redis map using [org.redisson.api.map.MapLoader].
     *
     * @param replaceExistingValues - `true` if existed values should be replaced, `false` otherwise.
     * @param parallelism - parallelism level, used to increase speed of process execution
     */
    suspend fun loadAll(replaceExistingValues: Boolean, parallelism: Int)

    /**
     * Loads map entries using [org.redisson.api.map.MapLoader] whose keys are listed in defined `keys` parameter.
     *
     * @param keys - map keys
     * @param replaceExistingValues - `true` if existed values should be replaced, `false` otherwise.
     * @param parallelism - parallelism level, used to increase speed of process execution
     */
    suspend fun loadAll(keys: Set<K>, replaceExistingValues: Boolean, parallelism: Int)

    /**
     * Returns size of value mapped by key in bytes
     *
     * @param key - map key
     * @return size of value
     */
    suspend fun valueSize(key: K): Int

    /**
     * Returns map slice contained the mappings with defined `keys`.
     *
     *
     * If map doesn't contain value/values for specified key/keys and [MapLoader] is defined
     * then value/values will be loaded in read-through mode.
     *
     *
     * The returned map is **NOT** backed by the original map.
     *
     * @param keys - map keys
     * @return Map slice
     */
    suspend fun getAll(keys: Set<K>): Map<K, V>

    /**
     * Stores map entries specified in `map` object in batch mode.
     *
     *
     * If [MapWriter] is defined then map entries will be stored in write-through mode.
     *
     * @param map mappings to be stored in this map
     */
    suspend fun putAll(map: Map<K, V>)

    /**
     * Adds the given `delta` to the current value
     * by mapped `key`.
     *
     * Works only for **numeric** values!
     *
     * @param key - map key
     * @param delta the value to add
     * @return the updated value
     */
    suspend fun addAndGet(key: K, delta: Number): V?

    /**
     * Returns `true` if this map contains any map entry
     * with specified `value`, otherwise `false`
     *
     * @param value - map value
     * @return `true` if this map contains any map entry
     * with specified `value`, otherwise `false`
     */
    suspend fun containsValue(value: Any): Boolean

    /**
     * Returns `true` if this map contains map entry
     * mapped by specified `key`, otherwise `false`
     *
     * @param key - map key
     * @return `true` if this map contains map entry
     * mapped by specified `key`, otherwise `false`
     */
    suspend fun containsKey(key: Any): Boolean

    /**
     * Returns size of this map
     *
     * @return size
     */
    suspend fun size(): Int

    /**
     * Removes map entries mapped by specified `keys`.
     *
     *
     * Works faster than `[.remove]` but not returning
     * the value.
     *
     *
     * If [MapWriter] is defined then `keys`are deleted in write-through mode.
     *
     * @param keys - map keys
     * @return the number of keys that were removed from the hash, not including specified but non existing keys
     */
    suspend fun fastRemove(vararg keys: K): Long

    /**
     * Stores the specified `value` mapped by specified `key`.
     *
     *
     * Works faster than `[.put]` but not returning
     * previous value.
     *
     *
     * Returns `true` if key is a new key in the hash and value was set or
     * `false` if key already exists in the hash and the value was updated.
     *
     *
     * If [MapWriter] is defined then map entry is stored in write-through mode.
     *
     * @param key - map key
     * @param value - map value
     * @return `true` if key is a new key in the hash and value was set.
     * `false` if key already exists in the hash and the value was updated.
     */
    suspend fun fastPut(key: K, value: V): Boolean

    /**
     * Stores the specified `value` mapped by specified `key`
     * only if there is no value with specified`key` stored before.
     *
     *
     * Returns `true` if key is a new one in the hash and value was set or
     * `false` if key already exists in the hash and change hasn't been made.
     *
     *
     * Works faster than `[.putIfAbsent]` but not returning
     * the previous value associated with `key`
     *
     *
     * If [MapWriter] is defined then new map entry is stored in write-through mode.
     *
     * @param key - map key
     * @param value - map value
     * @return `true` if key is a new one in the hash and value was set.
     * `false` if key already exists in the hash and change hasn't been made.
     */
    suspend fun fastPutIfAbsent(key: K, value: V): Boolean

    /**
     * Read all keys at once
     *
     * @return keys
     */
    suspend fun readAllKeySet(): Set<K>

    /**
     * Read all values at once
     *
     * @return values
     */
    suspend fun readAllValues(): Collection<V>

    /**
     * Read all map entries at once
     *
     * @return entries
     */
    suspend fun readAllEntrySet(): Set<MutableMap.MutableEntry<K, V>>

    /**
     * Read all map as local instance at once
     *
     * @return map
     */
    suspend fun readAllMap(): Map<K, V>

    /**
     * Returns the value mapped by defined `key` or `null` if value is absent.
     *
     *
     * If map doesn't contain value for specified key and [MapLoader] is defined
     * then value will be loaded in read-through mode.
     *
     * @param key the key
     * @return the value mapped by defined `key` or `null` if value is absent
     */
    suspend fun get(key: K): V?

    /**
     * Stores the specified `value` mapped by specified `key`.
     * Returns previous value if map entry with specified `key` already existed.
     *
     *
     * If [MapWriter] is defined then map entry is stored in write-through mode.
     *
     * @param key - map key
     * @param value - map value
     * @return previous associated value
     */
    suspend fun put(key: K, value: V): V?

    /**
     * Removes map entry by specified `key` and returns value.
     *
     *
     * If [MapWriter] is defined then `key`is deleted in write-through mode.
     *
     * @param key - map key
     * @return deleted value, `null` if map entry doesn't exist
     */
    suspend fun remove(key: K): V?

    /**
     * Replaces previous value with a new `value` mapped by specified `key`.
     * Returns `null` if there is no map entry stored before and doesn't store new map entry.
     *
     *
     * If [MapWriter] is defined then new `value`is written in write-through mode.
     *
     * @param key - map key
     * @param value - map value
     * @return previous associated value
     * or `null` if there is no map entry stored before and doesn't store new map entry
     */
    suspend fun replace(key: K, value: V): V?

    /**
     * Replaces previous `oldValue` with a `newValue` mapped by specified `key`.
     * Returns `false` if previous value doesn't exist or equal to `oldValue`.
     *
     *
     * If [MapWriter] is defined then `newValue`is written in write-through mode.
     *
     * @param key - map key
     * @param oldValue - map old value
     * @param newValue - map new value
     * @return `true` if value has been replaced otherwise `false`.
     */
    suspend fun replace(key: K, oldValue: V, newValue: V): Boolean

    /**
     * Removes map entry only if it exists with specified `key` and `value`.
     *
     *
     * If [MapWriter] is defined then `key`is deleted in write-through mode.
     *
     * @param key - map key
     * @param value - map value
     * @return `true` if map entry has been removed otherwise `false`.
     */
    suspend fun remove(key: Any, value: Any): Boolean

    /**
     * Stores the specified `value` mapped by specified `key`
     * only if there is no value with specified`key` stored before.
     *
     *
     * If [MapWriter] is defined then new map entry is stored in write-through mode.
     *
     * @param key - map key
     * @param value - map value
     * @return `null` if key is a new one in the hash and value was set.
     * Previous value if key already exists in the hash and change hasn't been made.
     */
    suspend fun putIfAbsent(key: K, value: V): V?

    /**
     * Returns iterator over map entries collection.
     * Map entries are loaded in batch. Batch size is `10`.
     *
     * @see .readAllEntrySet
     * @return iterator
     */
    suspend fun entryIterator(): Iterator<MutableMap.MutableEntry<K, V>>

    /**
     * Returns iterator over map entries collection.
     * Map entries are loaded in batch. Batch size is defined by `count` param.
     *
     * @see .readAllEntrySet
     * @param count - size of entries batch
     * @return iterator
     */
    suspend fun entryIterator(count: Int): Iterator<MutableMap.MutableEntry<K, V>>

    /**
     * Returns iterator over map entries collection.
     * Map entries are loaded in batch. Batch size is `10`.
     * If `keyPattern` is not null then only entries mapped by matched keys of this pattern are loaded.
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @see .readAllEntrySet
     * @param pattern - key pattern
     * @return iterator
     */
    suspend fun entryIterator(pattern: String?): Iterator<MutableMap.MutableEntry<K, V>>

    /**
     * Returns iterator over map entries collection.
     * Map entries are loaded in batch. Batch size is defined by `count` param.
     * If `keyPattern` is not null then only entries mapped by matched keys of this pattern are loaded.
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @see .readAllEntrySet
     * @param pattern - key pattern
     * @param count - size of entries batch
     * @return iterator
     */
    suspend fun entryIterator(pattern: String?, count: Int): Iterator<MutableMap.MutableEntry<K, V>>

    /**
     * Returns iterator over values collection of this map.
     * Values are loaded in batch. Batch size is `10`.
     *
     * @see .readAllValues
     * @return iterator
     */
    suspend fun valueIterator(): Iterator<V>

    /**
     * Returns iterator over values collection of this map.
     * Values are loaded in batch. Batch size is defined by `count` param.
     *
     * @see .readAllValues
     * @param count - size of values batch
     * @return iterator
     */
    suspend fun valueIterator(count: Int): Iterator<V>

    /**
     * Returns iterator over values collection of this map.
     * Values are loaded in batch. Batch size is `10`.
     * If `keyPattern` is not null then only values mapped by matched keys of this pattern are loaded.
     *
     *
     * Use `org.redisson.client.codec.StringCodec` for Map keys.
     *
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @see .readAllValues
     * @param pattern - key pattern
     * @return iterator
     */
    suspend fun valueIterator(pattern: String?): Iterator<V>

    /**
     * Returns iterator over values collection of this map.
     * Values are loaded in batch. Batch size is defined by `count` param.
     * If `keyPattern` is not null then only values mapped by matched keys of this pattern are loaded.
     *
     *
     * Use `org.redisson.client.codec.StringCodec` for Map keys.
     *
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @see .readAllValues
     * @param pattern - key pattern
     * @param count - size of values batch
     * @return iterator
     */
    suspend fun valueIterator(pattern: String?, count: Int): Iterator<V>

    /**
     * Returns iterator over key set of this map.
     * Keys are loaded in batch. Batch size is `10`.
     *
     * @see .readAllKeySet
     * @return iterator
     */
    suspend fun keyIterator(): Iterator<K>

    /**
     * Returns iterator over key set of this map.
     * Keys are loaded in batch. Batch size is defined by `count` param.
     *
     * @see .readAllKeySet
     * @param count - size of keys batch
     * @return iterator
     */
    suspend fun keyIterator(count: Int): Iterator<K>

    /**
     * Returns iterator over key set of this map.
     * If `pattern` is not null then only keys match this pattern are loaded.
     *
     *
     * Use `org.redisson.client.codec.StringCodec` for Map keys.
     *
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @see .readAllKeySet
     * @param pattern - key pattern
     * @return iterator
     */
    suspend fun keyIterator(pattern: String?): Iterator<K>

    /**
     * Returns iterator over key set of this map.
     * If `pattern` is not null then only keys match this pattern are loaded.
     * Keys are loaded in batch. Batch size is defined by `count` param.
     *
     *
     * Use `org.redisson.client.codec.StringCodec` for Map keys.
     *
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @see .readAllKeySet
     * @param pattern - key pattern
     * @param count - size of keys batch
     * @return iterator
     */
    suspend fun keyIterator(pattern: String?, count: Int): Iterator<K>

    /**
     * Returns `RPermitExpirableSemaphore` instance associated with key
     *
     * @param key - map key
     * @return permitExpirableSemaphore
     */
    fun getPermitExpirableSemaphore(key: K): RPermitExpirableSemaphoreCoroutines

    /**
     * Returns `RSemaphore` instance associated with key
     *
     * @param key - map key
     * @return semaphore
     */
    fun getSemaphore(key: K): RSemaphoreCoroutines

    /**
     * Returns `RLock` instance associated with key
     *
     * @param key - map key
     * @return fairLock
     */
    fun getFairLock(key: K): RLockCoroutines

    /**
     * Returns `RReadWriteLock` instance associated with key
     *
     * @param key - map key
     * @return readWriteLock
     */
    fun getReadWriteLock(key: K): RReadWriteLockCoroutines

    /**
     * Returns `RLock` instance associated with key
     *
     * @param key - map key
     * @return lock
     */
    fun getLock(key: K): RLockCoroutines
}