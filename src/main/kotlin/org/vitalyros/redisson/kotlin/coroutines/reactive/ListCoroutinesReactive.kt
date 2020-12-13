package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.redisson.api.RListReactive
import org.vitalyros.redisson.kotlin.coroutines.RListCoroutines

class ListCoroutinesReactive<V> (val wrapped: RListReactive<V>): RListCoroutines<V>,  SortableCollectionCoroutinesReactive<List<V>, V>(wrapped, wrapped) {
    override suspend fun get(vararg indexes: Int): List<V>  = wrapped.get(*indexes).awaitSingle()

    override suspend fun addAfter(elementToFind: V, element: V): Int = wrapped.addAfter(elementToFind, element).awaitSingle()

    override suspend fun addBefore(elementToFind: V, element: V): Int = wrapped.addBefore(elementToFind, element).awaitSingle()

    override suspend fun descendingIterator(): Iterator<V> = wrapped.descendingIterator().collectList().awaitSingle().iterator()

    override suspend fun descendingIterator(startIndex: Int): Iterator<V> = wrapped.descendingIterator(startIndex).collectList().awaitSingle().iterator()

    override suspend fun iterator(startIndex: Int): Iterator<V> = wrapped.iterator(startIndex).collectList().awaitSingle().iterator()

    override suspend fun lastIndexOf(element: Any): Int = wrapped.lastIndexOf(element).awaitSingle()

    override suspend fun indexOf(element: Any): Int = wrapped.indexOf(element).awaitSingle()

    override suspend fun add(index: Int, element: V) {
        wrapped.add(index, element).awaitSingleOrNull()
    }

    override suspend fun addAll(index: Int, elements: Collection<V>): Boolean = wrapped.addAll(index, elements).awaitSingle()

    override suspend fun fastSet(index: Int, element: V) {
        wrapped.fastSet(index, element).awaitSingleOrNull()
    }

    override suspend fun set(index: Int, element: V): V? = wrapped.set(index, element).awaitSingleOrNull()

    override suspend fun get(index: Int): V? = wrapped.get(index).awaitSingleOrNull()

    override suspend fun removeAt(index: Int): V = wrapped.remove(index).awaitSingle()

    override suspend fun readAll(): List<V> = wrapped.readAll().awaitSingle()

    override suspend fun trim(fromIndex: Int, toIndex: Int) {
        wrapped.trim(fromIndex, toIndex).awaitSingleOrNull()
    }

    override suspend fun fastRemove(index: Int) {
        wrapped.fastRemove(index).awaitSingleOrNull()
    }

    override suspend fun range(toIndex: Int): List<V> = wrapped.range(toIndex).awaitSingle()

    override suspend fun range(fromIndex: Int, toIndex: Int): List<V> = wrapped.range(fromIndex, toIndex).awaitSingle()
}