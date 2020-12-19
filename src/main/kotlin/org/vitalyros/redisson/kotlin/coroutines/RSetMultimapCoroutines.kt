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
 * Reactive interface for Set based Multimap
 *
 * Based on the original org.redisson.api.RSetMultimapReactive by Nikita Koksharov
 * @see org.redisson.api.RSetMultimapReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <K> key type
 * @param <V> value type
</V></K> */
interface RSetMultimapCoroutines<K, V> : RMultimapCoroutines<K, V> {
    /**
     * Returns a view Set of the values associated with `key` in this
     * multimap, if any. Note that when `containsKey(key)` is false, this
     * returns an empty collection, not `null`.
     *
     *
     * Changes to the returned collection will update the underlying multimap,
     * and vice versa.
     *
     * @param key - map key
     * @return set of values
     */
    operator fun get(key: K): RSetCoroutines<V>

    /**
     * Returns all elements at once. Result Set is **NOT** backed by map,
     * so changes are not reflected in map.
     *
     * @param key - map key
     * @return set of values
     */
    suspend fun getAll(key: K): Set<V>

    /**
     * Removes all values associated with the key `key`.
     *
     *
     * Once this method returns, `key` will not be mapped to any values
     *
     * Use [RMultimapCoroutines.fastRemove] if values are not needed.
     *
     * @param key - map key
     * @return the values that were removed (possibly empty). The returned
     * set *may* be modifiable, but updating it will have no
     * effect on the multimap.
     */
    suspend fun removeAll(key: Any): Set<V>

    /**
     * Stores a collection of values with the same key, replacing any existing
     * values for that key.
     *
     *
     * If `values` is empty, this is equivalent to
     * [.removeAll].
     *
     * @param key - map key
     * @param values - map values
     * @return set of replaced values, or an empty collection if no
     * values were previously associated with the key. Set
     * *may* be modifiable, but updating it will have no effect on the
     * multimap.
     */
    suspend fun replaceValues(key: K, values: Iterable<V>): Set<V>
}