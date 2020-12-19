package org.vitalyros.redisson.kotlin.coroutines.reactive

import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.vitalyros.redisson.kotlin.coroutines.RPermitExpirableSemaphoreCoroutines
import java.util.concurrent.TimeUnit

open class PermitExpirableSemaphoreTest : CoroutinesTest() {
    companion object {
        val TOTAL_PERMITS = 3
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
    fun testAquireRelease() = runTest {
        val semaphore = getTestSemaphore()
        val id1 = semaphore.acquire()
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        val id2 = semaphore.acquire()
        assertEquals(TOTAL_PERMITS - 2, semaphore.availablePermits())
        val id3 = semaphore.acquire(10000, TimeUnit.MILLISECONDS)
        assertEquals(0, semaphore.availablePermits())
        semaphore.release(id1)
        assertEquals(TOTAL_PERMITS - 2, semaphore.availablePermits())
        semaphore.release(id2)
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        semaphore.release(id3)
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    @Test
    fun testTryAquireRelease() = runTest {
        val semaphore = getTestSemaphore()
        val id1 = semaphore.tryAcquire()
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        val id2 = semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)
        assertEquals(TOTAL_PERMITS - 2, semaphore.availablePermits())
        val id3 = semaphore.tryAcquire(1000, 10000, TimeUnit.MILLISECONDS)
        assertEquals(0, semaphore.availablePermits())
        assertNull(semaphore.tryAcquire())
        assertTrue(semaphore.tryRelease(id1!!))
        assertEquals(TOTAL_PERMITS - 2, semaphore.availablePermits())
        assertTrue(semaphore.tryRelease(id2!!))
        assertEquals(TOTAL_PERMITS - 1, semaphore.availablePermits())
        assertTrue(semaphore.tryRelease(id3!!))
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
        assertFalse(semaphore.tryRelease(id3))
        assertEquals(TOTAL_PERMITS, semaphore.availablePermits())
    }

    fun getTestSemaphore(): RPermitExpirableSemaphoreCoroutines = redisson.getPermitExpirableSemaphore("test_Semaphore")
}