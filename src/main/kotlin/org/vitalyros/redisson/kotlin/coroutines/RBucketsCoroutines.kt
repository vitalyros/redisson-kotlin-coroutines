/**
 * Copyright (c) 2013-2020 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vitalyros.redisson.kotlin.coroutines

/**
 * Operations over multiple Bucket objects.
 *
 * Based on original org.redisson.api.RBuckets
 * @see org.redisson.api.RBuckets
 * Modified to use within kotlin coroutines
 *
 * Identical to Redisson's RBuckets interface but with suspend modifier applied to all functions that require suspension and does not extend RBucketsAsync
 */
interface RBucketsCoroutines {
    /**
     * Returns Redis object mapped by key. Result Map is not contains
     * key-value entry for null values.
     *
     * @param <V> type of value
     * @param keys - keys
     * @return Map with name of bucket as key and bucket as value
    </V> */
    suspend fun <V> get(vararg keys: String): Map<String, V>

    /**
     * Try to save objects mapped by Redis key.
     * If at least one of them is already exist then
     * don't set none of them.
     *
     * @param buckets - map of buckets
     * @return `true` if object has been set overwise `false`
     */
    suspend fun trySet(buckets: Map<String, *>): Boolean

    /**
     * Saves objects mapped by Redis key.
     *
     * @param buckets - map of buckets
     */
    suspend fun set(buckets: Map<String, *>)
}
