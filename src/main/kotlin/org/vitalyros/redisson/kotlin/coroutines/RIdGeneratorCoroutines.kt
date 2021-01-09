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
 * Id generator of <code>Long</code> type numbers.
 * Returns unique numbers but not monotonically increased.
 *
 * Based on the original org.redisson.api.RIdGenerator by Nikita Koksharov
 * @see org.redisson.api.RIdGenerator
 * Modified to use within kotlin coroutines
 */
interface RIdGeneratorCoroutines {

    /**
     * Initializes Id generator params.
     *
     * @param value - initial value
     * @param allocationSize - values range allocation size
     * @return `true` if Id generator initialized
     * `false` if Id generator already initialized
     */
    suspend fun tryInit(value: Long, allocationSize: Long): Boolean

    /**
     * Returns next unique number but not monotonically increased
     *
     * @return number
     */
    suspend fun nextId(): Long
}