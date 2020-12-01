package org.vitaliros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.redisson.RedisRunner
import org.redisson.Redisson
import org.redisson.config.Config
import org.vitalyros.redisson.kotlin.coroutines.RedissonCoroutinesClient
import org.vitalyros.redisson.kotlin.coroutines

open class CoroutinesTest {
    companion object {
        var testCoroutineContext = GlobalScope.coroutineContext
        lateinit var redisson: RedissonCoroutinesClient

        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            RedisRunner.startDefaultRedisServerInstance()
            redisson = createInstance()
        }

        @JvmStatic
        @AfterClass
        fun afterClass() {
            redisson.shutdown()
            RedisRunner.shutDownDefaultRedisServerInstance()
        }

        fun createInstance(): RedissonCoroutinesClient {
            val config: Config = createConfig()
            return Redisson.createReactive(config).coroutines()
        }

        fun createConfig(): Config {
            val config = Config()
            config.useSingleServer().address = RedisRunner.getDefaultRedisServerBindAddressAndPort()
            return config
        }
    }

    @Before
    fun before() = runTest {
        redisson.getKeys().flushall()
    }

    fun runTest(block: suspend (CoroutineScope) -> Unit) {
        runBlocking(testCoroutineContext, block)
    }
}