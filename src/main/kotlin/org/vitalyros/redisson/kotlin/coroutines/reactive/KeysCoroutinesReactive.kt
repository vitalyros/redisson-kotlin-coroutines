package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RKeysReactive
import org.redisson.api.RType
import org.vitalyros.redisson.kotlin.coroutines.RKeysCoroutines
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

class KeysCoroutinesReactive(val wrapped: RKeysReactive) : RKeysCoroutines {
    override suspend fun getKeysWithLimit(limit: Int): Iterable<String> = getKeysByPattern(null, limit)

    override suspend fun getKeysWithLimit(pattern: String?, limit: Int): Iterable<String> = wrapped.getKeysByPattern(pattern, limit).collectList().awaitSingle()

    override suspend fun move(name: String, database: Int): Boolean = wrapped.move(name, database).awaitSingle()

    override suspend fun migrate(name: String, host: String, port: Int, database: Int, timeout: Long) {
        wrapped.migrate(name, host, port, database, timeout).awaitSingleOrNull()
    }

    override suspend fun copy(name: String, host: String, port: Int, database: Int, timeout: Long) {
        wrapped.copy(name, host, port, database, timeout).awaitSingleOrNull()
    }

    override suspend fun expire(name: String, timeToLive: Long, timeUnit: TimeUnit): Boolean = wrapped.expire(name, timeToLive, timeUnit).awaitSingle()

    override suspend fun expireAt(name: String, timestamp: Long): Boolean = wrapped.expireAt(name, timestamp).awaitSingle()

    override suspend fun clearExpire(name: String): Boolean = wrapped.clearExpire(name).awaitSingle()

    override suspend fun renamenx(oldName: String, newName: String): Boolean = wrapped.renamenx(oldName, newName).awaitSingle()

    override suspend fun rename(currentName: String, newName: String) {
        wrapped.rename(currentName, newName).awaitSingle()
    }

    override suspend fun remainTimeToLive(name: String): Long = wrapped.remainTimeToLive(name).awaitSingle()

    override suspend fun touch(vararg names: String): Long = wrapped.touch(*names).awaitSingle()

    override suspend fun countExists(vararg names: String): Long = wrapped.countExists(*names).awaitSingle()

    override suspend fun getType(key: String): RType = wrapped.getType(key).awaitSingle()

    override suspend fun getSlot(key: String): Int = wrapped.getSlot(key).awaitSingle()

    override suspend fun getKeysByPattern(pattern: String?): Iterable<String> = wrapped.getKeysByPattern(pattern).collectList().awaitSingle()

    override suspend fun getKeysByPattern(pattern: String?, count: Int): Iterable<String> = wrapped.getKeysByPattern(pattern, count).collectList().awaitSingle()

    override suspend fun getKeys(): Iterable<String> = wrapped.keys.collectList().awaitSingle()

    override suspend fun getKeys(count: Int): Iterable<String> = wrapped.getKeys(count).collectList().awaitSingle()

    override fun getKeysStreamByPattern(pattern: String?): Stream<String> = wrapped.getKeysByPattern(pattern).toStream()

    override fun getKeysStreamByPattern(pattern: String, count: Int): Stream<String> = wrapped.getKeysByPattern(pattern, count).toStream()

    override val keysStream: Stream<String> = wrapped.keys.toStream()

    override fun getKeysStream(count: Int): Stream<String> = wrapped.getKeys(count).toStream()

    override suspend fun randomKey(): String? = wrapped.randomKey().awaitSingleOrNull()

    override suspend fun deleteByPattern(pattern: String?): Long = wrapped.deleteByPattern(pattern).awaitSingle()

    override suspend fun delete(vararg keys: String): Long = wrapped.delete(*keys).awaitSingle()

    override suspend fun unlink(vararg keys: String): Long = wrapped.delete(*keys).awaitSingle()

    override suspend fun count(): Long = wrapped.count().awaitSingle()

    override suspend fun swapdb(db1: Int, db2: Int) {
        wrapped.swapdb(db1, db2).awaitSingleOrNull()
    }

    override suspend fun flushdb() {
        wrapped.flushdb().awaitSingleOrNull()
    }

    override suspend fun flushdbParallel() {
        wrapped.flushdbParallel().awaitSingleOrNull()
    }

    override suspend fun flushall() {
        wrapped.flushall().awaitSingleOrNull()
    }

    override suspend fun flushallParallel() {
        wrapped.flushallParallel().awaitSingleOrNull()
    }
}