package org.vitalyros.redisson.kotlin

import org.redisson.api.RedissonReactiveClient
import org.vitalyros.redisson.kotlin.coroutines.reactive.RedissonCoroutinesReactive
import org.vitalyros.redisson.kotlin.coroutines.RedissonCoroutinesClient

fun RedissonReactiveClient.coroutines(): RedissonCoroutinesClient = RedissonCoroutinesReactive(this)
