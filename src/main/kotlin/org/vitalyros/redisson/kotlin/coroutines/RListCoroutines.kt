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

/**
 *
 * Based on the original org.redisson.api.RListReactive by Nikita Koksharov
 * @see org.redisson.api.RListReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <V> the type of elements held in this collection
 */
interface RListCoroutines<V> : RCollectionCoroutines<V>, RSortableCoroutines<List<V>> {
    /**
     * Loads elements by specified `indexes`
     *
     * @param indexes of elements
     * @return elements
     */
    suspend fun get(vararg indexes: Int): List<V>

    /**
     * Add `element` after `elementToFind`
     *
     * @param elementToFind - object to find
     * @param element - object to add
     * @return new list size
     */
    suspend fun addAfter(elementToFind: V, element: V): Int

    /**
     * Add `element` before `elementToFind`
     *
     * @param elementToFind - object to find
     * @param element - object to add
     * @return new list size
     */
    suspend fun addBefore(elementToFind: V, element: V): Int

    suspend fun descendingIterator(): Iterator<V>
    suspend fun descendingIterator(startIndex: Int): Iterator<V>
    suspend fun iterator(startIndex: Int): Iterator<V>
    /**
     * Returns last index of `element` or
     * -1 if element isn't found
     *
     * @param element to find
     * @return index of -1 if element isn't found
     */
    suspend fun lastIndexOf(element: Any): Int

    /**
     * Returns last index of `element` or
     * -1 if element isn't found
     *
     * @param element to find
     * @return index of -1 if element isn't found
     */
    suspend fun indexOf(element: Any): Int

    /**
     * Inserts `element` at `index`.
     * Subsequent elements are shifted.
     *
     * @param index - index number
     * @param element - element to insert
     * todo original RListReactive documentation on this method said it returns `true` if list was changed, but the result type was Mono<Void>, this might be fixed in future
     */
    suspend fun add(index: Int, element: V)

    /**
     * Inserts `elements` at `index`.
     * Subsequent elements are shifted.
     *
     * @param index - index number
     * @param elements - elements to insert
     * @return `true` if list changed
     * or `false` if element isn't found
     */
    suspend fun addAll(index: Int, elements: Collection<V>): Boolean

    /**
     * Set `element` at `index`.
     * Works faster than [.set] but
     * doesn't return previous element.
     *
     * @param index - index of object
     * @param element - object
     */
    suspend fun fastSet(index: Int, element: V)

    /**
     * Set `element` at `index` and returns previous element.
     *
     * @param index - index of object
     * @param element - object
     * @return previous element or `null` if element wasn't set.
     */
    suspend fun set(index: Int, element: V): V?

    /**
     * Get element at `index`
     *
     * @param index - index of object
     * @return element
     * todo check if the returned element can be null if the index is out of the List's bounds
     */
    suspend fun get(index: Int): V?

    /**
     * Removes element at `index`.
     *
     * @param index - index of object
     * @return element or `null` if element wasn't set.
     */
    suspend fun removeAt(index: Int): V

    /**
     * Read all elements at once
     *
     * @return list of values
     */
    suspend fun readAll(): List<V>

    /**
     * Trim list and remains elements only in specified range
     * `fromIndex`, inclusive, and `toIndex`, inclusive.
     *
     * @param fromIndex - from index
     * @param toIndex - to index
     */
    suspend fun trim(fromIndex: Int, toIndex: Int)

    /**
     * Remove object by specified index
     *
     * @param index - index of object
     */
    suspend fun fastRemove(index: Int)

    /**
     * Returns range of values from 0 index to `toIndex`. Indexes are zero based.
     * `-1` means the last element, `-2` means penultimate and so on.
     *
     * @param toIndex - end index
     * @return elements
     */
    suspend fun range(toIndex: Int): List<V>

    /**
     * Returns range of values from `fromIndex` to `toIndex` index including.
     * Indexes are zero based. `-1` means the last element, `-2` means penultimate and so on.
     *
     * @param fromIndex - start index
     * @param toIndex - end index
     * @return elements
     */
    suspend fun range(fromIndex: Int, toIndex: Int): List<V>
}