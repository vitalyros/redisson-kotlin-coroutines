package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.redisson.TestObject
import org.vitalyros.redisson.kotlin.coroutines.RSetCoroutines
import java.io.Serializable
import java.util.*

class SetDerivedTest : CoroutinesTest() {
    class SimpleBean : Serializable {
        var lng: Long? = null

    }

    @Test
    fun testAddAllReactive() = runTest {
        val list = redisson.getSet<Int>("set")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        val list2 = redisson.getSet<Int>("set2")
        Assert.assertEquals(true, list2.addAll(list.readAll()))
        Assert.assertEquals(5, list2.size())
    }

    @Test
    fun testRemoveRandom() = runTest {
        val set = redisson.getSet<Int>("simple")
        set.add(1)
        set.add(2)
        set.add(3)
        assertThat(set.removeRandom()).isIn(1, 2, 3)
        assertThat(set.removeRandom()).isIn(1, 2, 3)
        assertThat(set.removeRandom()).isIn(1, 2, 3)
        Assert.assertNull(set.removeRandom())
    }

    @Test
    fun testRandom() = runTest {
        val set = redisson.getSet<Int>("simple")
        set.add(1)
        set.add(2)
        set.add(3)
        assertThat(set.random()).isIn(1, 2, 3)
        assertThat(set.random()).isIn(1, 2, 3)
        assertThat(set.random()).isIn(1, 2, 3)
        assertThat(set.readAll()).containsOnly(1, 2, 3)
    }

    @Test
    fun testAddBean() = runTest {
        val sb = SimpleBean()
        sb.lng = 1L
        val set = redisson.getSet<SimpleBean>("simple")
        set.add(sb)
        Assert.assertEquals(sb.lng, set.iterator().next().lng)
    }

    @Test
    fun testAddLong() = runTest {
        val sb = 1L
        val set = redisson.getSet<Long>("simple_longs")
        set.add(sb)
        for (l in set) {
            Assert.assertEquals(sb.javaClass, l.javaClass)
        }
    }

    @Test
    fun testRemove() = runTest {
        val set = redisson.getSet<Int>("simple")
        set.add(1)
        set.add(3)
        set.add(7)
        Assert.assertTrue(set.remove(1))
        Assert.assertFalse(set.contains(1))
        assertThat(set.readAll()).containsExactlyInAnyOrder(3, 7)
        Assert.assertFalse(set.remove(1))
        assertThat(set.readAll()).containsExactlyInAnyOrder(3, 7)
        set.remove(3)
        Assert.assertFalse(set.contains(3))
        assertThat(set.readAll()).containsExactlyInAnyOrder(7)
    }

    @Test
    fun testIteratorSequence() = runTest {
        val set = redisson.getSet<Long>("set")
        for (i in 0..999) {
            set.add(i.toLong())
        }
        val setCopy: MutableSet<Long> = HashSet()
        for (i in 0..999) {
            setCopy.add(i.toLong())
        }
        checkIterator(set, setCopy)
    }

    private suspend fun checkIterator(set: RSetCoroutines<Long>, setCopy: MutableSet<Long>) {
        val iterator: Iterator<Long> = set.iterator()
        while (iterator.hasNext()) {
            val value = iterator.next()
            if (!setCopy.remove(value)) {
                Assert.fail()
            }
        }
        Assert.assertEquals(0, setCopy.size.toLong())
    }

    @Test
    fun testLong() = runTest {
        val set = redisson.getSet<Long>("set")
        set.add(1L)
        set.add(2L)
        assertThat(set.readAll()).containsOnly(1L, 2L)
    }

    @Test
    fun testRetainAll() = runTest {
        val set = redisson.getSet<Int>("set")
        for (i in 0..19999) {
            set.add(i)
        }
        Assert.assertTrue(set.retainAll(listOf(1, 2)))
        assertThat(set.readAll()).containsExactlyInAnyOrder(1, 2)
        Assert.assertEquals(2, set.size())
    }

    @Test
    fun testContainsAll() = runTest {
        val set = redisson.getSet<Any>("set")
        for (i in 0..199) {
            set.add(i)
        }
        Assert.assertTrue(set.containsAll(emptyList<Any>()))
        Assert.assertTrue(set.containsAll(listOf(30, 11)))
        Assert.assertFalse(set.containsAll(listOf(30, 711, 11)))
    }

    @Test
    fun testContains() = runTest {
        val set = redisson.getSet<TestObject>("set")
        set.add(TestObject("1", "2"))
        set.add(TestObject("1", "2"))
        set.add(TestObject("2", "3"))
        set.add(TestObject("3", "4"))
        set.add(TestObject("5", "6"))
        Assert.assertTrue(set.contains(TestObject("2", "3")))
        Assert.assertTrue(set.contains(TestObject("1", "2")))
        Assert.assertFalse(set.contains(TestObject("1", "9")))
    }

    @Test
    fun testDuplicates() = runTest {
        val set = redisson.getSet<TestObject>("set")
        set.add(TestObject("1", "2"))
        set.add(TestObject("1", "2"))
        set.add(TestObject("2", "3"))
        set.add(TestObject("3", "4"))
        set.add(TestObject("5", "6"))
        Assert.assertEquals(4, set.size())
    }

    @Test
    fun testSize() = runTest {
        val set = redisson.getSet<Int>("set")
        set.add(1)
        set.add(2)
        set.add(3)
        set.add(3)
        set.add(4)
        set.add(5)
        set.add(5)
        Assert.assertEquals(5, set.size())
    }

    @Test
    fun testRetainAllEmpty() = runTest {
        val set = redisson.getSet<Int>("set")
        set.add(1)
        set.add(2)
        set.add(3)
        set.add(4)
        set.add(5)
        Assert.assertTrue(set.retainAll(emptyList<Int>()))
        Assert.assertEquals(0, set.size())
    }

    @Test
    fun testRetainAllNoModify() = runTest {
        val set = redisson.getSet<Int>("set")
        set.add(1)
        set.add(2)
        Assert.assertFalse(set.retainAll(listOf(1, 2)))
        assertThat(set.readAll()).containsExactlyInAnyOrder(1, 2)
    }

    @Test
    fun testMove() = runTest {
        val set = redisson.getSet<Int>("set")
        val otherSet = redisson.getSet<Int>("otherSet")
        set.add(1)
        set.add(2)
        Assert.assertTrue(set.move("otherSet", 1))
        Assert.assertEquals(1, set.size())
        assertThat(set.readAll()).containsExactly(2)
        Assert.assertEquals(1, otherSet.size())
        assertThat(otherSet.readAll()).containsExactly(1)
    }

    @Test
    fun testMoveNoMember() = runTest {
        val set = redisson.getSet<Int>("set")
        val otherSet = redisson.getSet<Int>("otherSet")
        set.add(1)
        Assert.assertFalse(set.move("otherSet", 2))
        Assert.assertEquals(1, set.size())
        Assert.assertEquals(0, otherSet.size())
    }

    @Test
    fun testIntersection() = runTest {
        val firstSetName = "firstSet"
        val firstSet = redisson.getSet<Int>(firstSetName)
        firstSet.add(1)
        firstSet.add(2)
        firstSet.add(3)
        val secondSetName = "secondSet"
        val secondSet = redisson.getSet<Int>(secondSetName)
        secondSet.add(3)
        secondSet.add(4)
        secondSet.add(1)
        val tmp = redisson.getSet<Any>("tmp")
        val count: Int = tmp.intersection(firstSetName, secondSetName)
        Assert.assertEquals(2, count.toLong())
        Assert.assertTrue(tmp.contains(1))
        Assert.assertTrue(tmp.contains(3))
    }
}