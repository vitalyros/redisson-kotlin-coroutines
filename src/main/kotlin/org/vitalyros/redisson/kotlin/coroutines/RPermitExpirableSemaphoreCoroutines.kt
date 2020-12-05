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
 * Reactive interface for Semaphore object with lease time parameter support for each acquired permit.
 *
 *
 * Each permit identified by own id and could be released only using its id.
 * Permit id is a 128-bits unique random identifier generated each time during acquiring.
 *
 *
 * Works in non-fair mode. Therefore order of acquiring is unpredictable.
 *
 * Based on the original org.redisson.api.RPermitExpirableSemaphoreReactive by Nikita Koksharov
 * @see org.redisson.api.RPermitExpirableSemaphoreReactive
 * Modified to use within kotlin coroutines
 */
interface RPermitExpirableSemaphoreCoroutines : RExpirableCoroutines {
    /**
     * Acquires a permit and returns its id.
     * Waits if necessary until a permit became available.
     *
     * @return permit id
     */
    suspend fun acquire(): String

    /**
     * Acquires a permit with defined `leaseTime` and return its id.
     * Waits if necessary until a permit became available.
     *
     * @param leaseTime permit lease time
     * @param unit time unit
     * @return permit id
     */
    suspend fun acquire(leaseTime: Long, unit: TimeUnit): String

    /**
     * Tries to acquire currently available permit and return its id.
     *
     * @return permit id if a permit was acquired and `null`
     * otherwise
     */
    suspend fun tryAcquire(): String?

    /**
     * Tries to acquire currently available permit and return its id.
     * Waits up to defined `waitTime` if necessary until a permit became available.
     *
     * @param waitTime the maximum time to wait
     * @param unit the time unit
     * @return permit id if a permit was acquired and `null`
     * if the waiting time elapsed before a permit was acquired
     */
    suspend fun tryAcquire(waitTime: Long, unit: TimeUnit): String?

    /**
     * Tries to acquire currently available permit
     * with defined `leaseTime` and return its id.
     * Waits up to defined `waitTime` if necessary until a permit became available.
     *
     * @param waitTime the maximum time to wait
     * @param leaseTime permit lease time, use -1 to make it permanent
     * @param unit the time unit
     * @return permit id if a permit was acquired and `null`
     * if the waiting time elapsed before a permit was acquired
     */
    suspend fun tryAcquire(waitTime: Long, leaseTime: Long, unit: TimeUnit): String?

    /**
     * Tries to release permit by its id.
     *
     * @param permitId permit id
     * @return `true` if a permit has been released and `false`
     * otherwise
     */
    suspend fun tryRelease(permitId: String): Boolean

    /**
     * Releases a permit by its id. Increases the number of available permits.
     * Throws an exception if permit id doesn't exist or has already been released.
     *
     * @param permitId - permit id
     */
    suspend fun release(permitId: String)

    /**
     * Returns amount of available permits.
     *
     * @return number of permits
     */
    suspend fun availablePermits(): Int

    /**
     * Tries to set number of permits.
     *
     * @param permits - number of permits
     * @return `true` if permits has been set successfully, otherwise `false`.
     */
    suspend fun trySetPermits(permits: Int): Boolean

    /**
     * Increases or decreases the number of available permits by defined value.
     *
     * @param permits amount of permits to add/remove
     */
    suspend fun addPermits(permits: Int)

    /**
     * Overrides and updates lease time for defined permit id.
     *
     * @param permitId permit id
     * @param leaseTime permit lease time, use -1 to make it permanent
     * @param unit the time unit
     * @return `true` if permits has been updated successfully, otherwise `false`.
     */
    suspend fun updateLeaseTime(permitId: String, leaseTime: Long, unit: TimeUnit): Boolean
}