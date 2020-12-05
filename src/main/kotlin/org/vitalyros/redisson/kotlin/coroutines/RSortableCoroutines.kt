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

import org.redisson.api.SortOrder
import reactor.core.publisher.Mono

/**
 *
 * Based on the original org.redisson.api.RSortableReactive by Nikita Koksharov
 * @see org.redisson.api.RSortableReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <V> object type
</V> */
interface RSortableCoroutines<V> {
    /**
     * Read data in sorted view
     *
     * @param order for sorted data
     * @return sorted collection
     */
    suspend fun readSorted(order: SortOrder): V

    /**
     * Read data in sorted view
     *
     * @param order for sorted data
     * @param offset of sorted data
     * @param count of sorted data
     * @return sorted collection
     */
    suspend fun readSorted(order: SortOrder, offset: Int, count: Int): V

    /**
     * Read data in sorted view
     *
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param order for sorted data
     * @return sorted collection
     */
    suspend fun readSorted(byPattern: String, order: SortOrder): V

    /**
     * Read data in sorted view
     *
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param order for sorted data
     * @param offset of sorted data
     * @param count of sorted data
     * @return sorted collection
     */
    suspend fun readSorted(byPattern: String, order: SortOrder, offset: Int, count: Int): V

    /**
     * Read data in sorted view
     *
     * @param <T> object type
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param getPatterns that is used to load values by keys in sorted view
     * @param order for sorted data
     * @return sorted collection
    </T> */
    suspend fun <T> readSorted(byPattern: String, getPatterns: List<String>, order: SortOrder): Collection<T>

    /**
     * Read data in sorted view
     *
     * @param <T> object type
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param getPatterns that is used to load values by keys in sorted view
     * @param order for sorted data
     * @param offset of sorted data
     * @param count of sorted data
     * @return sorted collection
    </T> */
    suspend fun <T> readSorted(byPattern: String, getPatterns: List<String>, order: SortOrder, offset: Int, count: Int): Collection<T>

    /**
     * Sort data and store to `destName` list
     *
     * @param destName list object destination
     * @param order for sorted data
     * @return length of sorted data
     */
    suspend fun sortTo(destName: String, order: SortOrder): Int

    /**
     * Sort data and store to `destName` list
     *
     * @param destName list object destination
     * @param order for sorted data
     * @param offset of sorted data
     * @param count of sorted data
     * @return length of sorted data
     */
    suspend fun sortTo(destName: String, order: SortOrder, offset: Int, count: Int): Int

    /**
     * Sort data and store to `destName` list
     *
     * @param destName list object destination
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param order for sorted data
     * @return length of sorted data
     */
    suspend fun sortTo(destName: String, byPattern: String, order: SortOrder): Int

    /**
     * Sort data and store to `destName` list
     *
     * @param destName list object destination
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param order for sorted data
     * @param offset of sorted data
     * @param count of sorted data
     * @return length of sorted data
     */
    suspend fun sortTo(destName: String, byPattern: String, order: SortOrder, offset: Int, count: Int): Int

    /**
     * Sort data and store to `destName` list
     *
     * @param destName list object destination
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param getPatterns that is used to load values by keys in sorted view
     * @param order for sorted data
     * @return length of sorted data
     */
    suspend fun sortTo(destName: String, byPattern: String, getPatterns: List<String>, order: SortOrder): Int

    /**
     * Sort data and store to `destName` list
     *
     * @param destName list object destination
     * @param byPattern that is used to generate the keys that are used for sorting
     * @param getPatterns that is used to load values by keys in sorted view
     * @param order for sorted data
     * @param offset of sorted data
     * @param count of sorted data
     * @return length of sorted data
     */
    suspend fun sortTo(destName: String, byPattern: String, getPatterns: List<String>, order: SortOrder, offset: Int, count: Int): Int
}