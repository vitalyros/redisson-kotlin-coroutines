package org.vitalyros.redisson.kotlin.coroutines

/**
 * Common reactive interface for collection object
 *
 * Based on the original org.redisson.api.RCollectionReactive by Nikita Koksharov
 * @see org.redisson.api.RCollectionReactive
 * Modified to use within kotlin coroutines
 *
 *
 * @param <V> value
</V> */
interface RCollectionCoroutines<V> : RExpirableCoroutines {
    /**
     * Returns iterator over collection elements
     *
     * @return iterator
     */
    suspend operator fun iterator(): Iterator<V>

    /**
     * Retains only the elements in this collection that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing elements to be retained in this collection
     * @return `true` if this collection changed as a result of the call
     */
    suspend fun retainAll(c: Collection<*>): Boolean

    /**
     * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing elements to be removed from this collection
     * @return `true` if this collection changed as a result of the
     * call
     */
    suspend fun removeAll(c: Collection<*>): Boolean

    /**
     * Returns `true` if this collection contains encoded state of the specified element.
     *
     * @param o element whose presence in this collection is to be tested
     * @return `true` if this collection contains the specified
     * element and `false` otherwise
     */
    suspend fun contains(o: V): Boolean

    /**
     * Returns `true` if this collection contains all of the elements
     * in the specified collection.
     *
     * @param  c collection to be checked for containment in this collection
     * @return `true` if this collection contains all of the elements
     * in the specified collection
     */
    suspend fun containsAll(c: Collection<*>): Boolean

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).
     *
     * @param o element to be removed from this collection, if present
     * @return `true` if an element was removed as a result of this call
     */
    suspend fun remove(o: V): Boolean

    /**
     * Returns number of elements in this collection.
     *
     * @return size of collection
     */
    suspend fun size(): Int

    /**
     * Adds element into this collection.
     *
     * @param e - element to add
     * @return `true` if an element was added
     * and `false` if it is already present
     */
    suspend fun add(e: V): Boolean

    /**
     * Adds all elements contained in the specified collection
     *
     * @param c - collection of elements to add
     * @return `true` if at least one element was added
     * and `false` if all elements are already present
     */
    suspend fun addAll(c: Collection<V>): Boolean
}