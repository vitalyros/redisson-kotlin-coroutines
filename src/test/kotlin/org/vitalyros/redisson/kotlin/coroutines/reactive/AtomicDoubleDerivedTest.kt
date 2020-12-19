package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.junit.Assert.*
import org.junit.Test

class AtomicDoubleDerivedTest : CoroutinesTest() {
    @Test
    fun testCompareAndSet() = runTest {
        val al = redisson.getAtomicDouble("test")
        assertFalse(al.compareAndSet(-1.0, 2.0))
        assertEquals(0.0, al.get(), 0.0)
        assertTrue(al.compareAndSet(0.0, 2.0))
        assertEquals(2.0, al.get(), 0.0)
    }

    @Test
    fun testSetThenIncrement() = runTest {
        val al = redisson.getAtomicDouble("test")
        al.set(2.0)
        assertEquals(2.0, al.getAndIncrement(), 0.0)
        assertEquals(3.0, al.get(), 0.0)
    }

    @Test
    fun testIncrementAndGet() = runTest {
        val al = redisson.getAtomicDouble("test")
        assertEquals(1.0, al.incrementAndGet(), 0.0)
        assertEquals(1.0, al.get(), 0.0)
    }

    @Test
    fun testGetAndIncrement() = runTest {
        val al = redisson.getAtomicDouble("test")
        assertEquals(0.0, al.getAndIncrement(), 0.0)
        assertEquals(1.0, al.get(), 0.0)
    }

    @Test
    fun test() = runTest {
        val al = redisson.getAtomicDouble("test")
        assertEquals(0.0, al.get(), 0.0)
        assertEquals(0.0, al.getAndIncrement(), 0.0)
        assertEquals(1.0, al.get(), 0.0)
        assertEquals(1.0, al.getAndDecrement(), 0.0)
        assertEquals(0.0, al.get(), 0.0)
        assertEquals(0.0, al.getAndIncrement(), 0.0)
        assertEquals(1.0, al.getAndSet(12.0), 0.0)
        assertEquals(12.0, al.get(), 0.0)
        al.set(1.0)
        val state = redisson.getAtomicDouble("test").get()
        assertEquals(1.0, state, 0.0)
        al.set(Double.MAX_VALUE - 1000)
        val newState = redisson.getAtomicDouble("test").get()
        assertEquals(Double.MAX_VALUE - 1000, newState, .0)
    }
}