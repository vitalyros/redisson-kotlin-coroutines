package org.vitalyros.redisson.kotlin.coroutines;

import org.redisson.api.RLock
import org.redisson.client.codec.Codec
import org.redisson.config.Config
import java.util.concurrent.TimeUnit

interface RedissonCoroutinesClient {
    /**
     * Returns object holder instance by name
     *
     * @param <V> type of value
     * @param name - name of object
     * @return Bucket object
     */
    fun <V> getBucket(name: String): RBucketCoroutines<V>

    /**
     * Returns object holder instance by name
     * using provided codec for object.
     *
     * @param <V> type of value
     * @param name - name of object
     * @param codec - codec for value
     * @return Bucket object
     */
    fun <V> getBucket(name: String, codec: Codec): RBucketCoroutines<V>

    /**
     * Returns interface for mass operations with Bucket objects.
     *
     * @return Buckets
     */
    fun getBuckets(): RBucketsCoroutines

    /**
     * Returns interface for mass operations with Bucket objects
     * using provided codec for object.
     *
     * @param codec - codec for bucket objects
     * @return Buckets
     */
    fun getBuckets(codec: Codec): RBucketsCoroutines

    /**
     * Returns a list of object holder instances by a key pattern
     *
     * @param <V> type of value
     * @param pattern - pattern for name of buckets
     * @return list of buckets
    </V> */
    fun <V> findBuckets(pattern: String?): List<RBucketCoroutines<V>>

    /**
     * Returns interface with methods for Redis keys.
     * Each of Redis/Redisson object associated with own key
     *
     * @return Keys object
     */
    fun getKeys(): RKeysCoroutines

    /**
     * Returns Lock instance by name.
     *
     *
     * Implements a **fair** locking so it guarantees an acquire order by threads.
     *
     *
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param name - name of object
     * @return Lock object
     */
    fun getFairLock(name: String): RLockCoroutines

    /**
     * Returns Lock instance by name.
     *
     *
     * Implements a **non-fair** locking so doesn't guarantees an acquire order by threads.
     *
     *
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param name - name of object
     * @return Lock object
     */
    fun getLock(name: String): RLockCoroutines

    /**
     * Returns MultiLock instance associated with specified `locks`
     *
     * @param locks - collection of locks
     * @return MultiLock object
     */
    fun getMultiLock(vararg locks: RLock): RLockCoroutines

    /**
     * Returns semaphore instance by name
     *
     * @param name - name of object
     * @return Semaphore object
     */
    fun getSemaphore(name: String): RSemaphoreCoroutines

    /**
     * Returns semaphore instance by name.
     * Supports lease time parameter for each acquired permit.
     *
     * @param name - name of object
     * @return PermitExpirableSemaphore object
     */
    fun getPermitExpirableSemaphore(name: String): RPermitExpirableSemaphoreCoroutines

    /**
     * Returns ReadWriteLock instance by name.
     *
     *
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param name - name of object
     * @return Lock object
     */
    fun getReadWriteLock(name: String): RReadWriteLockCoroutines

    /**
     * Shutdown Redisson instance but **NOT** Redis server
     *
     * This equates to invoke shutdown(0, 2, TimeUnit.SECONDS);
     */
    fun shutdown()

    /**
     * Shuts down Redisson instance but **NOT** Redis server
     *
     * Shutdown ensures that no tasks are submitted for *'the quiet period'*
     * (usually a couple seconds) before it shuts itself down.  If a task is submitted during the quiet period,
     * it is guaranteed to be accepted and the quiet period will start over.
     *
     * @param quietPeriod the quiet period as described in the documentation
     * @param timeout     the maximum amount of time to wait until the executor is [.shutdown]
     * regardless if a task was submitted during the quiet period
     * @param unit        the unit of `quietPeriod` and `timeout`
     */
    fun shutdown(quietPeriod: Long, timeout: Long, unit: TimeUnit)

    /**
     * Allows to get configuration provided
     * during Redisson instance creation. Further changes on
     * this object not affect Redisson instance.
     *
     * @return Config object
     */
    val config: Config

    /**
     * Returns `true` if this Redisson instance has been shut down.
     *
     * @return `true` if this Redisson instance has been shut down overwise `false`
     */
    fun isShutdown(): Boolean

    /**
     * Returns `true` if this Redisson instance was started to be shutdown
     * or was shutdown [.isShutdown] already.
     *
     * @return `true` if this Redisson instance was started to be shutdown
     * or was shutdown [.isShutdown] already.
     */
    fun isShuttingDown(): Boolean

    /**
     * Returns id of this Redisson instance
     *
     * @return id
     */
    val id: String
}