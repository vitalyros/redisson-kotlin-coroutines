package org.vitalyros.redisson.kotlin.coroutines

import java.util.concurrent.TimeUnit

/**
 * Coroutines implementation of object holder. Max size of object is 512MB
 *
 *
 * Based on the original org.redisson.api.RBucket by Nikita Koksharov
 * @see org.redisson.api.RBucket
 * Modified to use within kotlin coroutines

 * @param <V> - the type of object
 */
interface RBucketCoroutines<V>: RExpirableCoroutines {
    /**
     * Returns size of object in bytes
     *
     * @return object size
     */
    suspend fun size(): Long

    /**
     * Tries to set element atomically into empty holder.
     *
     * @param value - value to set
     * @return `true` if successful, or `false` if
     * element was already set
     */
    suspend fun trySet(value: V): Boolean

    /**
     * Tries to set element atomically into empty holder with defined `timeToLive` interval.
     *
     * @param value - value to set
     * @param timeToLive - time to live interval
     * @param timeUnit - unit of time to live interval
     * @return `true` if successful, or `false` if
     * element was already set
     */
    suspend fun trySet(value: V, timeToLive: Long, timeUnit: TimeUnit): Boolean

    /**
     * Sets value only if it's already exists.
     *
     * @param value - value to set
     * @return `true` if successful, or `false` if
     * element wasn't set
     */
    suspend fun setIfExists(value: V): Boolean

    /**
     * Sets value only if it's already exists.
     *
     * @param value - value to set
     * @param timeToLive - time to live interval
     * @param timeUnit - unit of time to live interval
     * @return `true` if successful, or `false` if
     * element wasn't set
     */
    suspend fun setIfExists(value: V, timeToLive: Long, timeUnit: TimeUnit): Boolean

    /**
     * Atomically sets the value to the given updated value
     * only if serialized state of the current value equals
     * to serialized state of the expected value.
     *
     * @param expect the expected value
     * @param update the new value
     * @return `true` if successful; or `false` if the actual value
     * was not equal to the expected value.
     */
    suspend fun compareAndSet(expect: V, update: V): Boolean

    /**
     * Retrieves current element in the holder and replaces it with `newValue`.
     *
     * @param newValue - value to set
     * @return previous value
     */
    suspend fun getAndSet(newValue: V): V?

    /**
     * Retrieves current element in the holder and replaces it with `newValue` with defined `timeToLive` interval.
     *
     * @param value - value to set
     * @param timeToLive - time to live interval
     * @param timeUnit - unit of time to live interval
     * @return previous value
     */
    suspend fun getAndSet(value: V, timeToLive: Long, timeUnit: TimeUnit): V?

    /**
     * Retrieves element stored in the holder.
     *
     * @return element
     */
    suspend fun get(): V?

    /**
     * Retrieves element in the holder and removes it.
     *
     * @return element
     */
    suspend fun getAndDelete(): V?

    /**
     * Stores element into the holder.
     *
     * @param value - value to set
     * @return void
     */
    suspend fun set(value: V)

    /**
     * Stores element into the holder with defined `timeToLive` interval.
     *
     * @param value - value to set
     * @param timeToLive - time to live interval
     * @param timeUnit - unit of time to live interval
     * @return void
     */
     suspend fun set(value: V, timeToLive: Long, timeUnit: TimeUnit)
}