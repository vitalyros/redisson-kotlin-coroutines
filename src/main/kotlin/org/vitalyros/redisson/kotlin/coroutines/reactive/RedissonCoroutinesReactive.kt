package org.vitalyros.redisson.kotlin.coroutines.reactive;

import org.redisson.api.RKeys
import org.redisson.api.RLiveObjectService
import org.redisson.api.RedissonReactiveClient
import org.redisson.api.redisnode.BaseRedisNodes
import org.redisson.api.redisnode.RedisNodes
import org.redisson.client.codec.Codec
import org.redisson.config.Config
import org.vitalyros.redisson.kotlin.coroutines.RBucketCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RBucketsCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RKeysCoroutines
import org.vitalyros.redisson.kotlin.coroutines.RedissonCoroutinesClient
import java.util.concurrent.TimeUnit

class RedissonCoroutinesReactive(val wrapped: RedissonReactiveClient) : RedissonCoroutinesClient {
    override fun <V> getBucket(name: String): RBucketCoroutines<V> = BucketCoroutinesReactive(wrapped.getBucket<V>(name))

    override fun <V> getBucket(name: String, codec: Codec): RBucketCoroutines<V> = BucketCoroutinesReactive(wrapped.getBucket<V>(name, codec))

    override fun getBuckets(): RBucketsCoroutines = BucketsRoroutinesReactive(wrapped.buckets)

    override fun getBuckets(codec: Codec): RBucketsCoroutines = BucketsRoroutinesReactive(wrapped.getBuckets(codec))

    override fun <V> findBuckets(pattern: String?): List<RBucketCoroutines<V>> = wrapped.findBuckets<V>(pattern).map { BucketCoroutinesReactive(it) }

    override fun getKeys(): RKeysCoroutines = KeysCoroutinesReactive(wrapped.keys)

    override fun shutdown() = wrapped.shutdown()

    override fun shutdown(quietPeriod: Long, timeout: Long, unit: TimeUnit) = wrapped.shutdown()

    override val config: Config
        get() = wrapped.config


    override fun isShutdown(): Boolean = wrapped.isShutdown

    override fun isShuttingDown(): Boolean = wrapped.isShuttingDown

    override val id: String
        get() = wrapped.id
}