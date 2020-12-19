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
 * Reactive interface for AtomicLong object
 *
 * Based on the original org.redisson.api.RAtomicLongReactive by Nikita Koksharov
 * @see org.redisson.api.RAtomicLongReactive
 * Modified to use within kotlin coroutines
 */
interface RAtomicLongCoroutines : RExpirableCoroutines {
    /**
     * Atomically sets the value to the given updated value
     * only if the current value `==` the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful; or false if the actual value
     * was not equal to the expected value.
     */
    suspend fun compareAndSet(expect: Long, update: Long): Boolean

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the updated value
     */
    suspend fun addAndGet(delta: Long): Long

    /**
     * Atomically decrements the current value by one.
     *
     * @return the updated value
     */
    suspend fun decrementAndGet(): Long

    /**
     * Returns current value.
     *
     * @return the current value
     */
    suspend fun get(): Long

    /**
     * Returns and deletes object
     *
     * @return the current value
     */
    suspend fun getAndDelete(): Long

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the old value before the add
     */
    suspend fun getAndAdd(delta: Long): Long

    /**
     * Atomically sets the given value and returns the old value.
     *
     * @param newValue the new value
     * @return the old value
     */
    suspend fun getAndSet(newValue: Long): Long

    /**
     * Atomically increments the current value by one.
     *
     * @return the updated value
     */
    suspend fun incrementAndGet(): Long

    /**
     * Atomically increments the current value by one.
     *
     * @return the old value
     */
    suspend fun getAndIncrement(): Long

    /**
     * Atomically decrements by one the current value.
     *
     * @return the previous value
     */
    suspend fun getAndDecrement(): Long

    /**
     * Atomically sets the given value.
     *
     * @param newValue the new value
     */
    suspend fun set(newValue: Long)
}