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

import reactor.core.publisher.Mono

/**
 * Base Reactive interface for Multimap object
 *
 * Based on the original org.redisson.api.RMultimapReactive by Nikita Koksharov
 * @see org.redisson.api.RMultimapReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <K> key type
 * @param <V> value type
 */
interface RMultimapCoroutines<K, V> : RExpirableCoroutines {
    /**
     * Returns the number of key-value pairs in this multimap.
     *
     * @return size of multimap
     */
    suspend fun size(): Int

    /**
     * Returns `true` if this multimap contains at least one key-value pair
     * with the key `key`.
     *
     * @param key - map key
     * @return `true` if contains a key
     */
    suspend fun containsKey(key: Any): Boolean

    /**
     * Returns `true` if this multimap contains at least one key-value pair
     * with the value `value`.
     *
     * @param value - map value
     * @return `true` if contains a value
     */
    suspend fun containsValue(value: Any): Boolean

    /**
     * Returns `true` if this multimap contains at least one key-value pair
     * with the key `key` and the value `value`.
     *
     * @param key - map key
     * @param value - map value
     * @return `true` if contains an entry
     */
    suspend fun containsEntry(key: Any, value: Any): Boolean

    /**
     * Stores a key-value pair in this multimap.
     *
     *
     * Some multimap implementations allow duplicate key-value pairs, in which
     * case `put` always adds a new key-value pair and increases the
     * multimap size by 1. Other implementations prohibit duplicates, and storing
     * a key-value pair that's already in the multimap has no effect.
     *
     * @param key - map key
     * @param value - map value
     * @return `true` if the method increased the size of the multimap, or
     * `false` if the multimap already contained the key-value pair and
     * doesn't allow duplicates
     */
    suspend fun put(key: K, value: V): Boolean

    /**
     * Removes a single key-value pair with the key `key` and the value
     * `value` from this multimap, if such exists. If multiple key-value
     * pairs in the multimap fit this description, which one is removed is
     * unspecified.
     *
     * @param key - map key
     * @param value - map value
     * @return `true` if the multimap changed
     */
    suspend fun remove(key: Any, value: Any): Boolean
    // Bulk Operations
    /**
     * Stores a key-value pair in this multimap for each of `values`, all
     * using the same key, `key`. Equivalent to (but expected to be more
     * efficient than): <pre>   `for (V value : values) {
     * put(key, value);
     * }`</pre>
     *
     *
     * In particular, this is a no-op if `values` is empty.
     *
     * @param key - map key
     * @param values - map values
     * @return `true` if the multimap changed
     */
    suspend fun putAll(key: K, values: Iterable<V>): Boolean

    /**
     * Returns the number of key-value pairs in this multimap.
     *
     * @return keys amount
     */
    suspend fun keySize(): Int

    /**
     * Removes `keys` from map by one operation
     *
     * Works faster than `RMultimap.remove` but not returning
     * the value associated with `key`
     *
     * @param keys - map keys
     * @return the number of keys that were removed from the hash, not including specified but non existing keys
     */
    suspend fun fastRemove(vararg keys: K): Long

    /**
     * Read all keys at once
     *
     * @return keys
     */
    suspend fun readAllKeySet(): Set<K>
}