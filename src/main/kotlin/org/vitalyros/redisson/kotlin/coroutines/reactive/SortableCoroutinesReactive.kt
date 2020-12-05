package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RSortableReactive
import org.redisson.api.SortOrder
import org.vitalyros.redisson.kotlin.coroutines.RSortableCoroutines

class SortableCoroutinesReactive<V>(val wrapped: RSortableReactive<V>): RSortableCoroutines<V> {
    override suspend fun readSorted(order: SortOrder): V = wrapped.readSorted(order).awaitSingle()

    override suspend fun readSorted(order: SortOrder, offset: Int, count: Int): V = wrapped.readSorted(order, offset, count).awaitSingle()

    override suspend fun readSorted(byPattern: String, order: SortOrder): V = wrapped.readSorted(byPattern, order).awaitSingle()

    override suspend fun readSorted(byPattern: String, order: SortOrder, offset: Int, count: Int): V = wrapped.readSorted(byPattern, order, offset, count).awaitSingle()

    override suspend fun <T> readSorted(byPattern: String, getPatterns: List<String>, order: SortOrder): Collection<T> = wrapped.readSorted<T>(byPattern, getPatterns, order).awaitSingle()

    override suspend fun <T> readSorted(byPattern: String, getPatterns: List<String>, order: SortOrder, offset: Int, count: Int): Collection<T> = wrapped.readSorted<T>(byPattern, getPatterns, order, offset, count).awaitSingle()

    override suspend fun sortTo(destName: String, order: SortOrder): Int = wrapped.sortTo(destName, order).awaitSingle()

    override suspend fun sortTo(destName: String, order: SortOrder, offset: Int, count: Int): Int = wrapped.sortTo(destName, order, offset, count).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, order: SortOrder): Int = wrapped.sortTo(destName, byPattern, order).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, order: SortOrder, offset: Int, count: Int): Int = wrapped.sortTo(destName, byPattern, order, offset, count).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, getPatterns: List<String>, order: SortOrder): Int = wrapped.sortTo(destName, byPattern, getPatterns, order).awaitSingle()

    override suspend fun sortTo(destName: String, byPattern: String, getPatterns: List<String>, order: SortOrder, offset: Int, count: Int): Int = wrapped.sortTo(destName, byPattern, getPatterns, order, offset, count).awaitSingle()
}