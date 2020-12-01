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
package org.vitalyros.redisson.kotlin.coroutines;

import org.redisson.api.ObjectListener
import org.redisson.client.codec.Codec
import java.util.concurrent.TimeUnit

/**
 * Base Coroutines interface for all Redisson objects
 *
 * Based on original org.redisson.api.RObject
 * @see org.redisson.api.RObject
 * Modified to use within kotlin coroutines
 */
interface RObjectCoroutines {
    /**
     * Returns number of seconds spent since last write or read operation over this object.
     *
     * @return number of seconds
     */
    suspend fun getIdleTime(): Long

    val name: String

    val codec: Codec

    /**
     * Returns bytes amount used by object in Redis memory. 
     * 
     * @return size in bytes
     */
    suspend fun sizeInMemory(): Long
    
    /**
     * Restores object using its state returned by {@link #dump()} method.
     * 
     * @param state - state of object
     */
    suspend fun restore(state: ByteArray)
    
    /**
     * Restores object using its state returned by {@link #dump()} method and set time to live for it.
     * 
     * @param state - state of object
     * @param timeToLive - time to live of the object
     * @param timeUnit - time unit
     */
    suspend fun restore(state: ByteArray, timeToLive: Long, timeUnit: TimeUnit)
    
    /**
     * Restores and replaces object if it already exists.
     * 
     * @param state - state of the object
     */
    suspend fun restoreAndReplace(state: ByteArray)
    
    /**
     * Restores and replaces object if it already exists and set time to live for it.
     * 
     * @param state - state of the object
     * @param timeToLive - time to live of the object
     * @param timeUnit - time unit
     */
    suspend fun restoreAndReplace(state: ByteArray, timeToLive: Long, timeUnit: TimeUnit)

    /**
     * Returns dump of object
     * 
     * @return dump
     */
    suspend fun dump(): ByteArray
    
    /**
     * Update the last access time of an object. 
     * 
     * @return <code>true</code> if object was touched else <code>false</code>
     */
    suspend fun touch(): Boolean
    
    /**
     * Delete the objects.
     * Actual removal will happen later asynchronously.
     * <p>
     * Requires Redis 4.0+
     * 
     * @return <code>true</code> if it was exist and deleted else <code>false</code>
     */
    suspend fun unlink(): Boolean
    
    /**
     * Copy object from source Redis instance to destination Redis instance
     *
     * @param host - destination host
     * @param port - destination port
     * @param database - destination database
     * @param timeout - maximum idle time in any moment of the communication with the destination instance in milliseconds
     */
    suspend fun copy(host: String, port: Int, database: Int, timeout: Long)
    
    /**
     * Transfer a object from a source Redis instance to a destination Redis instance
     * in  mode
     *
     * @param host - destination host
     * @param port - destination port
     * @param database - destination database
     * @param timeout - maximum idle time in any moment of the communication with the destination instance in milliseconds
     */
    suspend fun migrate(host: String, port: Int, database: Int, timeout: Long)

    /**
     * Move object to another database in mode
     *
     * @param database - number of Redis database
     * @return <code>true</code> if key was moved <code>false</code> if not
     */
    suspend fun move(database: Int): Boolean

    /**
     * Delete object in mode
     *
     * @return <code>true</code> if object was deleted <code>false</code> if not
     */
    suspend fun delete(): Boolean

    /**
     * Rename current object key to <code>newName</code>
     * in  mode
     *
     * @param newName - new name of object
     * @return void
     */
    suspend fun rename(newName: String)

    /**
     * Rename current object key to <code>newName</code>
     * in mode only if new key is not exists
     *
     * @param newName - new name of object
     * @return <code>true</code> if object has been renamed successfully and <code>false</code> otherwise
     */
    suspend fun renamenx(newName: String): Boolean

    /**
     * Check object existence
     *
     * @return <code>true</code> if object exists and <code>false</code> otherwise
     */
    suspend fun isExists(): Boolean

    /**
     * Adds object event listener
     * 
     * @see org.redisson.api.ExpiredObjectListener
     * @see org.redisson.api.DeletedObjectListener
     * 
     * @param listener - object event listener
     * @return listener id
     */
    suspend fun addListener(listener: ObjectListener): Int

    /**
     * Removes object event listener
     * 
     * @param listenerId - listener id
     * @return void
     */
    suspend fun removeListener(listenerId: Int)
    
}
