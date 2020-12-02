package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.vitalyros.redisson.kotlin.coroutines.RLockCoroutines
import java.util.concurrent.TimeUnit

/**
 * Lock has been thoroughly tested in Redisson
 */
class LockCoroutinesReactiveTest : CoroutinesTest() {
    @After
    fun afterLockTests() = runTest {
        getTestLock().forceUnlock()
    }

    @Before
    fun beforeLockTests() = runTest {
        getTestLock().forceUnlock()
    }

    @Test
    fun testLockUnlock() = runTest {
        val lock = getTestLock()
        lock.lock()
        assertTrue(lock.isLocked())
        lock.unlock()
        assertFalse(lock.isLocked())
    }

    @Test
    fun testForceUnlock() = runTest {
        val lock = getTestLock()
        lock.lock()
        assertTrue(lock.isLocked())
        assert(lock.forceUnlock())
        assertFalse(lock.isLocked())
    }

    @Test
    fun testLease() = runTest {
        val lock = getTestLock()
        lock.lock(10000, TimeUnit.MILLISECONDS)
        assertTrue(lock.isLocked())
    }

    @Test
    fun testTryLockWithWaiting() = runTest {
        val lock = getTestLock()
        assert(lock.tryLock(10000, TimeUnit.MILLISECONDS))
        assertTrue(lock.isLocked())
    }

    @Test
    fun testTryLease() = runTest {
        val lock = getTestLock()
        assert(lock.tryLock(10000, 10000, TimeUnit.MILLISECONDS))
        assertTrue(lock.isLocked())
    }


    @Test
    fun test() = runTest {
        val lock = getTestLock()
        lock.forceUnlock()
        assertEquals(-2, lock.remainTimeToLive())
        val lockTime = 10000L
        lock.lock(lockTime, TimeUnit.MILLISECONDS)
        val ttl = lock.remainTimeToLive()
        assert(ttl > 0)
        assert(ttl <= lockTime)
        lock.unlock()
    }
    
    fun getTestLock(): RLockCoroutines = redisson.getLock("test")
}