package org.vitalyros.redisson.kotlin.coroutines.reactive;

import org.redisson.api.MapOptions
import org.redisson.api.RLock
import org.redisson.api.RedissonReactiveClient
import org.redisson.client.codec.Codec
import org.redisson.config.Config
import org.vitalyros.redisson.kotlin.coroutines.*
import java.util.concurrent.TimeUnit

class RedissonCoroutinesReactive(val wrapped: RedissonReactiveClient) : RedissonCoroutinesClient {
    override fun <V> getBucket(name: String): RBucketCoroutines<V> = BucketCoroutinesReactive(wrapped.getBucket<V>(name))

    override fun <V> getBucket(name: String, codec: Codec): RBucketCoroutines<V> = BucketCoroutinesReactive(wrapped.getBucket<V>(name, codec))

    override fun getBuckets(): RBucketsCoroutines = BucketsRoroutinesReactive(wrapped.buckets)

    override fun getBuckets(codec: Codec): RBucketsCoroutines = BucketsRoroutinesReactive(wrapped.getBuckets(codec))

    override fun <V> findBuckets(pattern: String?): List<RBucketCoroutines<V>> = wrapped.findBuckets<V>(pattern).map { BucketCoroutinesReactive(it) }

    override fun <K, V> getMap(name: String): RMapCoroutines<K, V> = MapCoroutinesReactive(wrapped.getMap(name))

    override fun <K, V> getMap(name: String, codec: Codec): RMapCoroutines<K, V> = MapCoroutinesReactive(wrapped.getMap(name, codec))

    override fun <K, V> getMap(name: String, codec: Codec, options: MapOptions<K, V>): RMapCoroutines<K, V> = MapCoroutinesReactive(wrapped.getMap(name, codec, options))

    override fun <V> getList(name: String): RListCoroutines<V> = ListCoroutinesReactive(wrapped.getList(name))

    override fun <V> getList(name: String, codec: Codec): RListCoroutines<V> = ListCoroutinesReactive(wrapped.getList(name, codec))

    override fun <V> getSet(name: String): RSetCoroutines<V> = SetCoroutinesReactive(wrapped.getSet(name))

    override fun <V> getSet(name: String, codec: Codec): RSetCoroutines<V> = SetCoroutinesReactive(wrapped.getSet(name, codec))

    override fun getKeys(): RKeysCoroutines = KeysCoroutinesReactive(wrapped.keys)

    override fun getFairLock(name: String): RLockCoroutines = LockCoroutinesReactive(wrapped.getFairLock(name))

    override fun getLock(name: String): RLockCoroutines = LockCoroutinesReactive(wrapped.getLock(name))

    override fun getMultiLock(vararg locks: RLock): RLockCoroutines = LockCoroutinesReactive(wrapped.getMultiLock(*locks))

    override fun getSemaphore(name: String): RSemaphoreCoroutines = SemaphoreCoroutinesReactive(wrapped.getSemaphore(name))

    override fun getPermitExpirableSemaphore(name: String): RPermitExpirableSemaphoreCoroutines = PermitExpirableSemaphoreCoroutinesReactive(wrapped.getPermitExpirableSemaphore(name))

    override fun getReadWriteLock(name: String): RReadWriteLockCoroutines = ReadWriteLockCoroutinesReactive(wrapped.getReadWriteLock(name))

    override fun shutdown() = wrapped.shutdown()

    override fun shutdown(quietPeriod: Long, timeout: Long, unit: TimeUnit) = wrapped.shutdown()

    override val config: Config
        get() = wrapped.config


    override fun isShutdown(): Boolean = wrapped.isShutdown

    override fun isShuttingDown(): Boolean = wrapped.isShuttingDown

    override val id: String
        get() = wrapped.id

}