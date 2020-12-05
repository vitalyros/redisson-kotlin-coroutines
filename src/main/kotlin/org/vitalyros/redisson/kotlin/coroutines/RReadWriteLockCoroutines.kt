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
 * A `ReadWriteLock` maintains a pair of associated [ ], one for read-only operations and one for writing.
 * The [read lock][.readLock] may be held simultaneously by
 * multiple reader threads, so long as there are no writers.  The
 * [write lock][.writeLock] is exclusive.
 *
 * Works in non-fair mode. Therefore order of read and write
 * locking is unspecified.
 *
 * Based on the original org.redisson.api.RReadWriteLockReactive by Nikita Koksharov
 * @see org.redisson.api.RReadWriteLockReactive
 * Modified to use within kotlin coroutines
 */
interface RReadWriteLockCoroutines {
    /**
     * Returns the lock used for reading.
     *
     * @return the lock used for reading
     */
    fun readLock(): RLockCoroutines

    /**
     * Returns the lock used for writing.
     *
     * @return the lock used for writing
     */
    fun writeLock(): RLockCoroutines
}