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
 * Reactive interface for AtomicDouble object
 *
 * Based on the original org.redisson.api.RAtomicDoubleReactive by Nikita Koksharov
 * @see org.redisson.api.RAtomicDoubleReactive
 * Modified to use within kotlin coroutines
 */
interface RAtomicDoubleCoroutines : RExpirableCoroutines {
    /**
     * Atomically sets the value to the given updated value
     * only if the current value `==` the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return true if successful; or false if the actual value
     * was not equal to the expected value.
     */
    suspend fun compareAndSet(expect: Double, update: Double): Boolean

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the updated value
     */
    suspend fun addAndGet(delta: Double): Double

    /**
     * Atomically decrements the current value by one.
     *
     * @return the updated value
     */
    suspend fun decrementAndGet(): Double

    /**
     * Returns current value.
     *
     * @return current value
     */
    suspend fun get(): Double

    /**
     * Returns and deletes object
     *
     * @return the current value
     */
    suspend fun getAndDelete(): Double

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the updated value
     */
    suspend fun getAndAdd(delta: Double): Double

    /**
     * Atomically sets the given value and returns the old value.
     *
     * @param newValue the new value
     * @return the old value
     */
    suspend fun getAndSet(newValue: Double): Double

    /**
     * Atomically increments the current value by one.
     *
     * @return the updated value
     */
    suspend fun incrementAndGet(): Double

    /**
     * Atomically increments the current value by one.
     *
     * @return the old value
     */
    suspend fun getAndIncrement(): Double

    /**
     * Atomically decrements by one the current value.
     *
     * @return the previous value
     */
    suspend fun getAndDecrement(): Double

    /**
     * Atomically sets the given value.
     *
     * @param newValue the new value
     */
    suspend fun set(newValue: Double)
}