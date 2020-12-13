package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RCollectionReactive
import org.redisson.api.RSortableReactive
import org.redisson.api.SortOrder
import org.vitalyros.redisson.kotlin.coroutines.RSortableCoroutines

abstract class SortableCollectionCoroutinesReactive<V, T>(private val wrappedSortable: RSortableReactive<V>, wrappedCollection: RCollectionReactive<T>): RSortableCoroutines<V>, CollectionCoroutinesReactive<T>(wrappedCollection) {
    override suspend fun readSorted(order: SortOrder): V = wrappedSortable.readSorted(order).awaitSingle()

    override suspend fun readSorted(order: SortOrder, offset: Int, count: Int): V = wrappedSortable.readSorted(order, offset, count).awaitSingle()

    override suspend fun readSorted(byPattern: String, order: SortOrder): V = wrappedSortable.readSorted(byPattern, order).awaitSingle()

    override suspend fun readSorted(byPattern: String, order: SortOrder, offset: Int, count: Int): V = wrappedSortable.readSorted(byPattern, order, offset, count).awaitSingle()

    override suspend fun <T> readSorted(byPattern: String, getPatterns: List<String>, order: SortOrder): Collection<T> = wrappedSortable.readSorted<T>(byPattern, getPatterns, order).awaitSingle()

    override suspend fun <T> readSorted(byPattern: String, getPatterns: List<String>, order: SortOrder, offset: Int, count: Int): Collection<T> = wrappedSortable.readSorted<T>(byPattern, getPatterns, order, offset, count).awaitSingle()

    override suspend fun sortTo(destName: String, order: SortOrder): Int = wrappedSortable.sortTo(destName, order).awaitSingle()

    override suspend fun sortTo(destName: String, order: SortOrder, offset: Int, count: Int): Int = wrappedSortable.sortTo(destName, order, offset, count).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, order: SortOrder): Int = wrappedSortable.sortTo(destName, byPattern, order).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, order: SortOrder, offset: Int, count: Int): Int = wrappedSortable.sortTo(destName, byPattern, order, offset, count).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, getPatterns: List<String>, order: SortOrder): Int = wrappedSortable.sortTo(destName, byPattern, getPatterns, order).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, getPatterns: List<String>, order: SortOrder, offset: Int, count: Int): Int = wrappedSortable.sortTo(destName, byPattern, getPatterns, order, offset, count).awaitSingle()
}