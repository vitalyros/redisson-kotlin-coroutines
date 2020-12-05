package org.vitalyros.redisson.kotlin.coroutines.reactive

import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.vitalyros.redisson.kotlin.coroutines.RSemaphoreCoroutines
import java.util.concurrent.TimeUnit

open class SemaphoreReactiveTest : CoroutinesTest() {
    companion object {
        val TOTAL_PERMITS = 10
    }

    @Before
    fun beforeSemaphoreTest() = runTest {
        getTestSemaphore().unlink()
        getTestSemaphore().trySetPermits(TOTAL_PERMITS)
    }

    @After
    fun afterSemaphoreTest() = runTest {
        getTestSemaphore().unlink()
    }

    @Test
    fun testAcqireRelease_single() = runTest {
        val semaphore = getTestSemaphore()
        semaphore.acquire()
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        semaphore.release()
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testAcqireRelease_multiple() = runTest {
        val semaphore = getTestSemaphore()
        semaphore.acquire(5)
        assertEquals(TOTAL_PERMITS - 5, semaphore.availablePermits())
        semaphore.release(5)
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testAcqireRelease_all() = runTest {
        val semaphore = getTestSemaphore()
        semaphore.acquire(TOTAL_PERMITS)
        assertEquals(0, semaphore.availablePermits())
        semaphore.release(TOTAL_PERMITS)
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testDrainPermits() = runTest {
        val semaphore = getTestSemaphore()
        semaphore.drainPermits()
        assertEquals(0, semaphore.availablePermits())
        semaphore.release(TOTAL_PERMITS)
    }

    @Test
    fun testTryAcquire_single() = runTest {
        val semaphore = getTestSemaphore()
        assertTrue(semaphore.tryAcquire())
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        semaphore.release()
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }


    @Test
    fun testTryAcquire_multiple() = runTest {
        val semaphore = getTestSemaphore()
        assertTrue(semaphore.tryAcquire(5))
        assertEquals(TOTAL_PERMITS - 5, semaphore.availablePermits())
        semaphore.release(5)
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testTryAcquireWaitTime_single() = runTest {
        val semaphore = getTestSemaphore()
        assertTrue(semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS))
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        semaphore.release()
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testTryAcquireWaitTime_multiple() = runTest {
        val semaphore = getTestSemaphore()
        assertTrue(semaphore.tryAcquire(5, 1000, TimeUnit.MILLISECONDS))
        assertEquals(TOTAL_PERMITS - 5, semaphore.availablePermits())
        semaphore.release(5)
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testTryAcquire_all() = runTest {
        val semaphore = getTestSemaphore()
        assertTrue(semaphore.tryAcquire(TOTAL_PERMITS))
        assertEquals(0, semaphore.availablePermits())
        assertFalse(semaphore.tryAcquire())
        semaphore.release(TOTAL_PERMITS)
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    fun getTestSemaphore(): RSemaphoreCoroutines = redisson.getSemaphore("test_Semaphore")
}