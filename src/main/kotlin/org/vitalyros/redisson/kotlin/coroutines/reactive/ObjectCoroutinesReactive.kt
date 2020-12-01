package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.ObjectListener
import org.redisson.api.RObjectReactive
import org.redisson.client.codec.Codec
import org.vitalyros.redisson.kotlin.coroutines.RObjectCoroutines
import java.util.concurrent.TimeUnit

open class ObjectCoroutinesReactive(private val wrapped: RObjectReactive): RObjectCoroutines {
    override suspend fun getIdleTime(): Long = wrapped.idleTime.awaitSingle()

    override val name: String
        get() = wrapped.name

    override val codec: Codec
        get() = wrapped.codec

    override suspend fun sizeInMemory(): Long = wrapped.sizeInMemory().awaitSingle()

    override suspend fun restore(state: ByteArray) {
        wrapped.restore(state).awaitSingleOrNull()
    }

    override suspend fun restore(state: ByteArray, timeToLive: Long, timeUnit: TimeUnit) {
        wrapped.restore(state, timeToLive, timeUnit).awaitSingleOrNull()
    }

    override suspend fun restoreAndReplace(state: ByteArray) {
        wrapped.restoreAndReplace(state).awaitSingleOrNull()
    }

    override suspend fun restoreAndReplace(state: ByteArray, timeToLive: Long, timeUnit: TimeUnit) {
        wrapped.restoreAndReplace(state, timeToLive, timeUnit).awaitSingleOrNull()
    }

    override suspend fun dump(): ByteArray = wrapped.dump().awaitSingle()

    override suspend fun touch(): Boolean = wrapped.touch().awaitSingle()

    override suspend fun unlink(): Boolean = wrapped.unlink().awaitSingle()

    override suspend fun copy(host: String, port: Int, database: Int, timeout: Long) {
        wrapped.copy(host, port, database, timeout).awaitSingleOrNull()
    }

    override suspend fun migrate(host: String, port: Int, database: Int, timeout: Long) {
        wrapped.migrate(host, port, database, timeout).awaitSingleOrNull()
    }

    override suspend fun move(database: Int): Boolean = wrapped.move(database).awaitSingle()

    override suspend fun delete(): Boolean = wrapped.delete().awaitSingle()

    override suspend fun rename(newName: String) {
        wrapped.rename(newName).awaitSingleOrNull()
    }

    override suspend fun renamenx(newName: String): Boolean = wrapped.renamenx(newName).awaitSingle()

    override suspend fun isExists(): Boolean = wrapped.isExists.awaitSingle()

    override suspend fun addListener(listener: ObjectListener): Int = wrapped.addListener(listener).awaitSingle()

    override suspend fun removeListener(listenerId: Int)  {
        wrapped.removeListener(listenerId).awaitSingleOrNull()
    }
}