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

import java.util.concurrent.TimeUnit

/**
 * Reactive interface of Redis based [java.util.concurrent.Semaphore].
 *
 *
 * Works in non-fair mode. Therefore order of acquiring is unpredictable.
 *
 * Based on the original org.redisson.api.RSemaphoreReactive by Nikita Koksharov
 * @see org.redisson.api.RSemaphoreReactive
 * Modified to use within kotlin coroutines
 */
interface RSemaphoreCoroutines : RExpirableCoroutines {
    /**
     * Acquires a permit.
     * Waits if necessary until a permit became available.
     *
     * @return `true` if a permit was acquired and `false`
     * otherwise
     */
    suspend fun tryAcquire(): Boolean

    /**
     * Tries to acquire defined amount of currently available `permits`.
     *
     * @param permits the number of permits to acquire
     * @return `true` if permits were acquired and `false`
     * otherwise
     */
    suspend fun tryAcquire(permits: Int): Boolean

    /**
     * Acquires a permit.
     * Waits if necessary until a permit became available.
     */
    suspend fun acquire()

    /**
     * Acquires defined amount of `permits`.
     * Waits if necessary until all permits became available.
     *
     * @param permits the number of permits to acquire
     * @throws IllegalArgumentException if `permits` is negative
     */
    suspend fun acquire(permits: Int)

    /**
     * Releases a permit.
     */
    suspend fun release()

    /**
     * Releases defined amount of `permits`.
     *
     * @param permits amount
     */
    suspend fun release(permits: Int)

    /**
     * Tries to set number of permits.
     *
     * @param permits - number of permits
     * @return `true` if permits has been set successfully, otherwise `false`.
     */
    suspend fun trySetPermits(permits: Int): Boolean

    /**
     * Tries to acquire currently available permit.
     * Waits up to defined `waitTime` if necessary until a permit became available.
     *
     * @param waitTime the maximum time to wait
     * @param unit the time unit
     * @return `true` if a permit was acquired and `false`
     * otherwise
     */
    suspend fun tryAcquire(waitTime: Long, unit: TimeUnit): Boolean

    /**
     * Tries to acquire defined amount of currently available `permits`.
     * Waits up to defined `waitTime` if necessary until all permits became available.
     *
     * @param permits amount of permits
     * @param waitTime the maximum time to wait
     * @param unit the time unit
     * @return `true` if permits were acquired and `false`
     * otherwise
     */
    suspend fun tryAcquire(permits: Int, waitTime: Long, unit: TimeUnit): Boolean

    /**
     * Increases or decreases the number of available permits by defined value.
     *
     * @param permits amount of permits to add/remove
     */
    suspend fun addPermits(permits: Int)

    /**
     * Returns amount of available permits.
     *
     * @return number of permits
     */
    suspend fun availablePermits(): Int

    /**
     * Acquires and returns all permits that are immediately available.
     *
     * @return number of permits
     */
    suspend fun drainPermits(): Int
}