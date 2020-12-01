package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RExpirableReactive
import org.vitalyros.redisson.kotlin.coroutines.RExpirableCoroutines
import java.util.*
import java.util.concurrent.TimeUnit

open class ExpireableCoroutinesReactive(private val wrapped: RExpirableReactive): ObjectCoroutinesReactive(wrapped), RExpirableCoroutines {
    override suspend fun expire(timeToLive: Long, timeUnit: TimeUnit): Boolean = wrapped.expire(timeToLive, timeUnit).awaitSingle()

    override suspend fun expireAt(timestamp: Date): Boolean = wrapped.expireAt(timestamp).awaitSingle()

    override suspend fun expireAt(timestamp: Long): Boolean = wrapped.expireAt(timestamp).awaitSingle()

    override suspend fun clearExpire(): Boolean = wrapped.clearExpire().awaitSingle()

    override suspend fun remainTimeToLive(): Long = wrapped.remainTimeToLive().awaitSingle()
}