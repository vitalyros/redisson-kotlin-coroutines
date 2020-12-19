package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.junit.Assert.*
import org.junit.Test

class AtomicLongDerivedTest : CoroutinesTest() {
    @Test
    fun testCompareAndSet() = runTest {
        val al = redisson.getAtomicLong("test")
        assertFalse(al.compareAndSet(-1, 2))
        assertEquals(0, al.get())
        assertTrue(al.compareAndSet(0, 2))
        assertEquals(2, al.get())
    }

    @Test
    fun testSetThenIncrement() = runTest {
        val al = redisson.getAtomicLong("test")
        al.set(2)
        assertEquals(2, al.getAndIncrement())
        assertEquals(3, al.get())
    }

    @Test
    fun testIncrementAndGet() = runTest {
        val al = redisson.getAtomicLong("test")
        assertEquals(1, al.incrementAndGet())
        assertEquals(1, al.get())
    }

    @Test
    fun testGetAndIncrement() = runTest {
        val al = redisson.getAtomicLong("test")
        assertEquals(0, al.getAndIncrement())
        assertEquals(1, al.get())
    }

    @Test
    fun test() = runTest {
        val al = redisson.getAtomicLong("test")
        assertEquals(0, al.get())
        assertEquals(0, al.getAndIncrement())
        assertEquals(1, al.get())
        assertEquals(1, al.getAndDecrement())
        assertEquals(0, al.get())
        assertEquals(0, al.getAndIncrement())
        assertEquals(1, al.getAndSet(12))
        assertEquals(12, al.get())
        al.set(1)
        val state = redisson.getAtomicLong("test").get()
        assertEquals(1, state)
        al.set(Long.MAX_VALUE - 1000)
        val newState = redisson.getAtomicLong("test").get()
        assertEquals(Long.MAX_VALUE - 1000, newState)
    }
}