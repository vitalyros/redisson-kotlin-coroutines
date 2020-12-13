package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RListReactive
import org.redisson.api.RSetReactive
import org.vitalyros.redisson.kotlin.coroutines.*

class SetCoroutinesReactive<V> (val wrapped: RSetReactive<V>): RSetCoroutines<V>,  SortableCollectionCoroutinesReactive<Set<V>, V>(wrapped, wrapped) {
    override fun getPermitExpirableSemaphore(value: V): RPermitExpirableSemaphoreCoroutines = PermitExpirableSemaphoreCoroutinesReactive(wrapped.getPermitExpirableSemaphore(value))

    override fun getSemaphore(value: V): RSemaphoreCoroutines = SemaphoreCoroutinesReactive(wrapped.getSemaphore((value)))

    override fun getFairLock(value: V): RLockCoroutines = LockCoroutinesReactive(wrapped.getFairLock(value))

    override fun getReadWriteLock(value: V): RReadWriteLockCoroutines = ReadWriteLockCoroutinesReactive(wrapped.getReadWriteLock(value))

    override fun getLock(value: V): RLockCoroutines = LockCoroutinesReactive(wrapped.getLock(value))

    override suspend fun iterator(count: Int): Iterator<V> = wrapped.iterator(count).collectList().awaitSingle().iterator()

    override suspend fun iterator(pattern: String?, count: Int): Iterator<V> = wrapped.iterator(pattern, count).collectList().awaitSingle().iterator()

    override suspend fun iterator(pattern: String?): Iterator<V> = wrapped.iterator(pattern).collectList().awaitSingle().iterator()

    override suspend fun removeRandom(amount: Int): Set<V> = wrapped.removeRandom(amount).awaitSingle()

    override suspend fun removeRandom(): V = wrapped.removeRandom().awaitSingleOrNull()

    override suspend fun random(): V = wrapped.random().awaitSingleOrNull()

    override suspend fun random(count: Int): Set<V> = wrapped.random(count).awaitSingle()

    override suspend fun move(destination: String, member: V): Boolean = wrapped.move(destination, member).awaitSingle()

    override suspend fun readAll(): Set<V> = wrapped.readAll().awaitSingle()

    override suspend fun union(vararg names: String): Int = wrapped.union(* names).awaitSingle()

    override suspend fun readUnion(vararg names: String): Set<V> = wrapped.readUnion(*names).awaitSingle()

    override suspend fun diff(vararg names: String): Int = wrapped.diff(*names).awaitSingle()

    override suspend fun readDiff(vararg names: String): Set<V> = wrapped.readDiff(*names).awaitSingle()

    override suspend fun intersection(vararg names: String): Int = wrapped.intersection(*names).awaitSingle()

    override suspend fun readIntersection(vararg names: String): Set<V> = wrapped.readIntersection(*names).awaitSingle()
}