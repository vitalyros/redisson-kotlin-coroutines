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
 * Reactive interface for Redis based implementation of [java.util.Set]
 *
 * Based on the original org.redisson.api.RSetReactive by Nikita Koksharov
 * @see org.redisson.api.RSetReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <V> type of value
</V> */
interface RSetCoroutines<V> : RCollectionCoroutines<V>, RSortableCoroutines<Set<V>> {
    /**
     * Returns `RPermitExpirableSemaphore` instance associated with `value`
     *
     * @param value - set value
     * @return RPermitExpirableSemaphore object
     */
    fun getPermitExpirableSemaphore(value: V): RPermitExpirableSemaphoreCoroutines

    /**
     * Returns `RSemaphore` instance associated with `value`
     *
     * @param value - set value
     * @return RSemaphore object
     */
    fun getSemaphore(value: V): RSemaphoreCoroutines

    /**
     * Returns `RLock` instance associated with `value`
     *
     * @param value - set value
     * @return RLock object
     */
    fun getFairLock(value: V): RLockCoroutines

    /**
     * Returns `RReadWriteLock` instance associated with `value`
     *
     * @param value - set value
     * @return RReadWriteLock object
     */
    fun getReadWriteLock(value: V): RReadWriteLockCoroutines

    /**
     * Returns lock instance associated with `value`
     *
     * @param value - set value
     * @return RLock object
     */
    fun getLock(value: V): RLockCoroutines

    /**
     * Returns elements iterator fetches elements in a batch.
     * Batch size is defined by `count` param.
     *
     * @param count - size of elements batch
     * @return iterator
     */
    suspend fun iterator(count: Int): Iterator<V>

    /**
     * Returns elements iterator fetches elements in a batch.
     * Batch size is defined by `count` param.
     * If pattern is not null then only elements match this pattern are loaded.
     *
     * @param pattern - search pattern
     * @param count - size of elements batch
     * @return iterator
     */
    suspend fun iterator(pattern: String?, count: Int): Iterator<V>

    /**
     * Returns elements iterator.
     * If `pattern` is not null then only elements match this pattern are loaded.
     *
     * @param pattern - search pattern
     * @return iterator
     */
    suspend fun iterator(pattern: String?): Iterator<V>

    /**
     * Removes and returns random elements limited by `amount`
     *
     * @param amount of random elements
     * @return random elements
     */
    suspend fun removeRandom(amount: Int): Set<V>

    /**
     * Removes and returns random element
     *
     * @return random element
     */
    suspend fun removeRandom(): V?

    /**
     * Returns random element
     *
     * @return random element
     */
    suspend fun random(): V?

    /**
     * Returns random elements from set limited by `count`
     *
     * @param count - values amount to return
     * @return random elements
     */
    suspend fun random(count: Int): Set<V>

    /**
     * Move a member from this set to the given destination set in async mode.
     *
     * @param destination the destination set
     * @param member the member to move
     * @return true if the element is moved, false if the element is not a
     * member of this set or no operation was performed
     */
    suspend fun move(destination: String, member: V): Boolean

    /**
     * Read all elements at once
     *
     * @return values
     */
    suspend fun readAll(): Set<V>

    /**
     * Union sets specified by name and write to current set.
     * If current set already exists, it is overwritten.
     *
     * @param names - name of sets
     * @return size of union
     */
    suspend fun union(vararg names: String): Int

    /**
     * Union sets specified by name with current set.
     * Without current set state change.
     *
     * @param names - name of sets
     * @return size of union
     */
    suspend fun readUnion(vararg names: String): Set<V>

    /**
     * Diff sets specified by name and write to current set.
     * If current set already exists, it is overwritten.
     *
     * @param names - name of sets
     * @return size of diff
     */
    suspend fun diff(vararg names: String): Int

    /**
     * Diff sets specified by name with current set.
     * Without current set state change.
     *
     * @param names - name of sets
     * @return values
     */
    suspend fun readDiff(vararg names: String): Set<V>

    /**
     * Intersection sets specified by name and write to current set.
     * If current set already exists, it is overwritten.
     *
     * @param names - name of sets
     * @return size of intersection
     */
    suspend fun intersection(vararg names: String): Int

    /**
     * Intersection sets specified by name with current set.
     * Without current set state change.
     *
     * @param names - name of sets
     * @return values
     */
    suspend fun readIntersection(vararg names: String): Set<V>
}