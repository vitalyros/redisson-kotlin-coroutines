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

import org.redisson.api.RType
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

/**
 * Based on original org.redisson.api.RKeys
 * @see org.redisson.api.RKeys
 * Modified to use within kotlin coroutines
 */
interface RKeysCoroutines {
    /**
     * Get keys using iterator with defined `limit`.
     * Keys are traversed with SCAN operation.
     *
     * @param limit - limit of keys amount
     * @return Iterable object
     */
    suspend fun getKeysWithLimit(limit: Int): Iterable<String>

    /**
     * Get keys using iterator with defined `limit`.
     * Keys are traversed with SCAN operation.
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param limit - limit of keys amount
     * @param pattern - match pattern
     * @return Iterable object
     */
    suspend fun getKeysWithLimit(pattern: String?, limit: Int): Iterable<String?>

    /**
     * Move object to another database
     *
     * @param name of object
     * @param database - Redis database number
     * @return `true` if key was moved else `false`
     */
    suspend fun move(name: String, database: Int): Boolean

    /**
     * Transfer object from source Redis instance to destination Redis instance
     *
     * @param name of object
     * @param host - destination host
     * @param port - destination port
     * @param database - destination database
     * @param timeout - maximum idle time in any moment of the communication with the destination instance in milliseconds
     */
    suspend fun migrate(name: String, host: String, port: Int, database: Int, timeout: Long)

    /**
     * Copy object from source Redis instance to destination Redis instance
     *
     * @param name of object
     * @param host - destination host
     * @param port - destination port
     * @param database - destination database
     * @param timeout - maximum idle time in any moment of the communication with the destination instance in milliseconds
     */
    suspend fun copy(name: String, host: String, port: Int, database: Int, timeout: Long)

    /**
     * Set a timeout for object. After the timeout has expired,
     * the key will automatically be deleted.
     *
     * @param name of object
     * @param timeToLive - timeout before object will be deleted
     * @param timeUnit - timeout time unit
     * @return `true` if the timeout was set and `false` if not
     */
    suspend fun expire(name: String, timeToLive: Long, timeUnit: TimeUnit): Boolean

    /**
     * Set an expire date for object. When expire date comes
     * the key will automatically be deleted.
     *
     * @param name of object
     * @param timestamp - expire date in milliseconds (Unix timestamp)
     * @return `true` if the timeout was set and `false` if not
     */
    suspend fun expireAt(name: String, timestamp: Long): Boolean

    /**
     * Clear an expire timeout or expire date for object.
     *
     * @param name of object
     * @return `true` if timeout was removed
     * `false` if object does not exist or does not have an associated timeout
     */
    suspend fun clearExpire(name: String): Boolean

    /**
     * Rename object with `oldName` to `newName`
     * only if new key is not exists
     *
     * @param oldName - old name of object
     * @param newName - new name of object
     * @return `true` if object has been renamed successfully and `false` otherwise
     */
    suspend fun renamenx(oldName: String, newName: String): Boolean

    /**
     * Rename current object key to `newName`
     *
     * @param currentName - current name of object
     * @param newName - new name of object
     */
    suspend fun rename(currentName: String, newName: String)

    /**
     * Remaining time to live of Redisson object that has a timeout
     *
     * @param name of key
     * @return time in milliseconds
     * -2 if the key does not exist.
     * -1 if the key exists but has no associated expire.
     */
    suspend fun remainTimeToLive(name: String): Long

    /**
     * Update the last access time of an object.
     *
     * @param names of keys
     * @return count of objects were touched
     */
    suspend fun touch(vararg names: String): Long

    /**
     * Checks if provided keys exist
     *
     * @param names of keys
     * @return amount of existing keys
     */
    suspend fun countExists(vararg names: String): Long

    /**
     * Get Redis object type by key
     *
     * @param key - name of key
     * @return type of key
     */
    suspend fun getType(key: String): RType

    /**
     * Get hash slot identifier for key.
     * Available for cluster nodes only
     *
     * @param key - name of key
     * @return slot number
     */
    suspend fun getSlot(key: String): Int

    /**
     * Get all keys by pattern using iterator.
     * Keys traversed with SCAN operation. Each SCAN operation loads
     * up to **10** keys per request.
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern - match pattern
     * @return Iterable object
     */
    suspend fun getKeysByPattern(pattern: String?): Iterable<String>

    /**
     * Get all keys by pattern using iterator.
     * Keys traversed with SCAN operation. Each SCAN operation loads
     * up to `count` keys per request.
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern - match pattern
     * @param count - keys loaded per request to Redis
     * @return Iterable object
     */
    suspend fun getKeysByPattern(pattern: String?, count: Int): Iterable<String>

    /**
     * Get all keys using iterator. Keys traversing with SCAN operation.
     * Each SCAN operation loads up to `10` keys per request.
     *
     * @return Iterable object
     */
    suspend fun getKeys(): Iterable<String>

    /**
     * Get all keys using iterator. Keys traversing with SCAN operation.
     * Each SCAN operation loads up to `count` keys per request.
     *
     * @param count - keys loaded per request to Redis
     * @return Iterable object
     */
    suspend fun getKeys(count: Int): Iterable<String>

    /**
     * Get all keys by pattern using Stream.
     * Keys traversed with SCAN operation. Each SCAN operation loads
     * up to **10** keys per request.
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern - match pattern
     * @return Iterable object
     */
    fun getKeysStreamByPattern(pattern: String?): Stream<String>

    /**
     * Get all keys by pattern using Stream.
     * Keys traversed with SCAN operation. Each SCAN operation loads
     * up to `count` keys per request.
     *
     *
     * Supported glob-style patterns:
     *
     *
     * h?llo subscribes to hello, hallo and hxllo
     *
     *
     * h*llo subscribes to hllo and heeeello
     *
     *
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern - match pattern
     * @param count - keys loaded per request to Redis
     * @return Iterable object
     */
    fun getKeysStreamByPattern(pattern: String, count: Int): Stream<String>

    /**
     * Get all keys using Stream. Keys traversing with SCAN operation.
     * Each SCAN operation loads up to `10` keys per request.
     *
     * @return Iterable object
     */
    val keysStream: Stream<String>

    /**
     * Get all keys using Stream. Keys traversing with SCAN operation.
     * Each SCAN operation loads up to `count` keys per request.
     *
     * @param count - keys loaded per request to Redis
     * @return Iterable object
     */
    fun getKeysStream(count: Int): Stream<String>

    /**
     * Get random key
     *
     * @return random key
     */
    suspend fun randomKey(): String?

    /**
     * Delete multiple objects by a key pattern.
     *
     *
     * Method executes in **NON atomic way** in cluster mode due to lua script limitations.
     *
     *
     * Supported glob-style patterns:
     * h?llo subscribes to hello, hallo and hxllo
     * h*llo subscribes to hllo and heeeello
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern - match pattern
     * @return number of removed keys
     */
    suspend fun deleteByPattern(pattern: String?): Long

    /**
     * Delete multiple objects
     *
     * @param objects of Redisson
     * @return number of removed keys
     * todo: unimplemented in React api
     */
    //suspend fun delete(vararg objects: RObject): Long

    /**
     * Delete multiple objects by name
     *
     * @param keys - object names
     * @return number of removed keys
     */
    suspend fun delete(vararg keys: String): Long

    /**
     * Delete multiple objects by name.
     * Actual removal will happen later asynchronously.
     *
     *
     * Requires Redis 4.0+
     *
     * @param keys of objects
     * @return number of removed keys
     */
    suspend fun unlink(vararg keys: String): Long

    /**
     * Returns the number of keys in the currently-selected database
     *
     * @return count of keys
     */
    suspend fun count(): Long

    /**
     * Swap two databases.
     */
    suspend fun swapdb(db1: Int, db2: Int)

    /**
     * Delete all keys of currently selected database
     */
    suspend fun flushdb()

    /**
     * Delete all keys of currently selected database
     * in background without blocking server.
     *
     *
     * Requires Redis 4.0+
     *
     */
    suspend fun flushdbParallel()

    /**
     * Delete all keys of all existing databases
     */
    suspend fun flushall()

    /**
     * Delete all keys of all existing databases
     * in background without blocking server.
     *
     *
     * Requires Redis 4.0+
     *
     */
    suspend fun flushallParallel()
}
