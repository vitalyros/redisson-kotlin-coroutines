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
 * Reactive interface for Lock object
 *
 * Based on the original org.redisson.api.RLockReactive by Nikita Koksharov
 * @see org.redisson.api.RLockReactive
 * Modified to use within kotlin coroutines
 *
 * locking and unlocking with threadId, holdCount not implemented
 */
interface RLockCoroutines {
    /**
     * Returns name of object
     *
     * @return name - name of object
     */
    val name: String

    /**
     * Unlocks the lock independently of its state
     *
     * @return `true` if lock existed and now unlocked
     * otherwise `false`
     */
    suspend fun forceUnlock(): Boolean

    /**
     * Unlocks the lock
     */
    suspend fun unlock()

    /**
     * Tries to acquire the lock.
     *
     * @return `true` if lock acquired otherwise `false`
     */
    suspend fun tryLock(): Boolean

    /**
     * Acquires the lock.
     * Waits if necessary until lock became available.
     */
    suspend fun lock()

    /**
     * Acquires the lock with defined `leaseTime`.
     * Waits if necessary until lock became available.
     *
     * Lock will be released automatically after defined `leaseTime` interval.
     *
     * @param leaseTime the maximum time to hold the lock after it's acquisition,
     * if it hasn't already been released by invoking `unlock`.
     * If leaseTime is -1, hold the lock until explicitly unlocked.
     * @param unit the time unit
     */
    suspend fun lock(leaseTime: Long, unit: TimeUnit)

    /**
     * Tries to acquire the lock.
     * Waits up to defined `waitTime` if necessary until the lock became available.
     *
     * @param waitTime the maximum time to acquire the lock
     * @param unit time unit
     * @return `true` if lock is successfully acquired,
     * otherwise `false` if lock is already set.
     */
    suspend fun tryLock(waitTime: Long, unit: TimeUnit): Boolean

    /**
     * Tries to acquire the lock with defined `leaseTime`.
     * Waits up to defined `waitTime` if necessary until the lock became available.
     *
     * Lock will be released automatically after defined `leaseTime` interval.
     *
     * @param waitTime the maximum time to acquire the lock
     * @param leaseTime lease time
     * @param unit time unit
     * @return `true` if lock is successfully acquired,
     * otherwise `false` if lock is already set.
     */
    suspend fun tryLock(waitTime: Long, leaseTime: Long, unit: TimeUnit): Boolean

    /**
     * Checks if the lock locked by any thread
     *
     * @return `true` if locked otherwise `false`
     */
    suspend fun isLocked(): Boolean

    /**
     * Remaining time to live of the lock
     *
     * @return time in milliseconds
     * -2 if the lock does not exist.
     * -1 if the lock exists but has no associated expire.
     */
    suspend fun remainTimeToLive(): Long
}