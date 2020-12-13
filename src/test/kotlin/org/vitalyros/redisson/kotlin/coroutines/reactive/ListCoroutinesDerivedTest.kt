package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions.assertThat

import org.junit.Assert
import org.junit.Test
import org.redisson.TestObject
import org.redisson.client.RedisException
import org.vitalyros.redisson.kotlin.coroutines.RListCoroutines

class ListCoroutinesDerivedTest : CoroutinesTest() {
    @Test
    fun testAddByIndex() = runTest {
        val test2 = redisson.getList<String>("test2")
        test2.add("foo")
        test2.add(0, "bar")
        assertThat(test2.readAll()).containsExactly("bar", "foo")
    }

    @Test
    fun testAddAllReactive() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        val list2 = redisson.getList<Int>("list2")
        Assert.assertEquals(true, list2.addAll(list.readAll()))
        Assert.assertEquals(5, list2.size())
    }

    @Test
    fun testAddAllWithIndex() = runTest {
        val list = redisson.getList<Long>("list")
        list.addAll(listOf(1L, 2L, 3L))
        list.addAll(listOf(1L, 24L, 3L))
        assertThat(list.readAll()).containsExactly(1L, 2L, 3L, 1L, 24L, 3L)
    }

    @Test
    fun testAdd() = runTest {
        val list = redisson.getList<Long>("list")
        list.add(1L)
        list.add(2L)
        assertThat(list.readAll()).containsExactly(1L, 2L)
    }

    @Test
    fun testListIteratorIndex() = runTest {
        val list = redisson.getList<Int>("list2")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(0)
        list.add(7)
        list.add(8)
        list.add(0)
        list.add(10)
        val iterator: Iterator<Int> = list.iterator()
        Assert.assertTrue(1 == iterator.next())
        Assert.assertTrue(2 == iterator.next())
        Assert.assertTrue(3 == iterator.next())
        Assert.assertTrue(4 == iterator.next())
        Assert.assertTrue(5 == iterator.next())
        Assert.assertTrue(0 == iterator.next())
        Assert.assertTrue(7 == iterator.next())
        Assert.assertTrue(8 == iterator.next())
        Assert.assertTrue(0 == iterator.next())
        Assert.assertTrue(10 == iterator.next())
        Assert.assertFalse(iterator.hasNext())
    }

    @Test
    fun testListIteratorPrevious() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(0)
        list.add(7)
        list.add(8)
        list.add(0)
        list.add(10)
        val iterator: Iterator<Int> = list.descendingIterator()
        Assert.assertTrue(10 == iterator.next())
        Assert.assertTrue(0 == iterator.next())
        Assert.assertTrue(8 == iterator.next())
        Assert.assertTrue(7 == iterator.next())
        Assert.assertTrue(0 == iterator.next())
        Assert.assertTrue(5 == iterator.next())
        Assert.assertTrue(4 == iterator.next())
        Assert.assertTrue(3 == iterator.next())
        Assert.assertTrue(2 == iterator.next())
        Assert.assertTrue(1 == iterator.next())
        Assert.assertFalse(iterator.hasNext())
    }

    @Test
    fun testLastIndexOfNone() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertEquals(-1, list.lastIndexOf(10))
    }

    @Test
    fun testLastIndexOf2() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(0)
        list.add(7)
        list.add(8)
        list.add(0)
        list.add(10)
        val index = list.lastIndexOf(3)
        Assert.assertEquals(2, index)
    }

    @Test
    fun testLastIndexOf1() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(3)
        list.add(7)
        list.add(8)
        list.add(0)
        list.add(10)
        val index = list.lastIndexOf(3)
        Assert.assertEquals(5, index)
    }

    @Test
    fun testLastIndexOf() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(3)
        list.add(7)
        list.add(8)
        list.add(3)
        list.add(10)
        val index = list.lastIndexOf(3)
        Assert.assertEquals(8, index.toLong())
    }

    @Test
    fun testIndexOf() = runTest {
        val list = redisson.getList<Int>("list")
        for (i in 1..199) {
            list.add(i)
        }
        Assert.assertTrue(55 == list.indexOf(56))
        Assert.assertTrue(99 == list.indexOf(100))
        Assert.assertTrue(-1 == list.indexOf(200))
        Assert.assertTrue(-1 == list.indexOf(0))
    }

    @Test
    fun testRemoveAt() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        val val1: Int = list.removeAt(0)
        Assert.assertTrue(1 == val1)
        assertThat(list.readAll()).containsExactly(2, 3, 4, 5)
    }

    @Test
    fun testSet() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.set(4, 6)
        assertThat(list.readAll()).containsExactly(1, 2, 3, 4, 6)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testSetFail() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.set(5, 6)
    }

    @Test
    fun testRemoveAllEmpty() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertFalse(list.removeAll(emptyList<Any>()))
    }

    @Test
    fun testRemoveAll() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertFalse(list.removeAll(emptyList<Any>()))
        Assert.assertTrue(list.removeAll(listOf(3, 2, 10, 6)))
        assertThat(list.readAll()).containsExactly(1, 4, 5)
        Assert.assertTrue(list.removeAll(listOf(4)))
        assertThat(list.readAll()).containsExactly(1, 5)
        Assert.assertTrue(list.removeAll(listOf(1, 5, 1, 5)))
        Assert.assertEquals(0, list.size())
    }

    @Test
    fun testRetainAll() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertTrue(list.retainAll(listOf(3, 2, 10, 6)))
        assertThat(list.readAll()).containsExactly(2, 3)
        Assert.assertEquals(2, list.size())
    }

    @Test
    fun testFastSet() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.fastSet(0, 3)
        Assert.assertEquals(3, list.get(0))
    }

    @Test
    fun testRetainAllEmpty() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertTrue(list.retainAll(emptyList<Int>()))
        Assert.assertEquals(0, list.size())
    }

    @Test
    fun testRetainAllNoModify() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        Assert.assertFalse(list.retainAll(listOf(1, 2)))
        assertThat(list.readAll()).containsExactly(1, 2)
    }

    @Test(expected = RedisException::class)
    fun testAddAllIndexError() = runTest {
        val list = redisson.getList<Int>("list")
        list.addAll(2, listOf(7, 8, 9))
    }

    @Test
    fun testAddAllIndex() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertEquals(true, list.addAll(2, listOf(7, 8, 9)))
        assertThat(list.readAll()).containsExactly(1, 2, 7, 8, 9, 3, 4, 5)
        list.addAll(list.size() - 1, listOf(9, 1, 9))
        assertThat(list.readAll()).containsExactly(1, 2, 7, 8, 9, 3, 4, 9, 1, 9, 5)
        list.addAll(list.size(), listOf(0, 5))
        assertThat(list.readAll()).containsExactly(1, 2, 7, 8, 9, 3, 4, 9, 1, 9, 5, 0, 5)
        Assert.assertEquals(true, list.addAll(0, listOf(6, 7)))
        assertThat(list.readAll()).containsExactly(6, 7, 1, 2, 7, 8, 9, 3, 4, 9, 1, 9, 5, 0, 5)
    }

    @Test
    fun testAddAll() = runTest {
        val list = redisson.getList<Int>("list")
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        Assert.assertEquals(true, list.addAll(listOf(7, 8, 9)))
        Assert.assertEquals(true, list.addAll(listOf(9, 1, 9)))
        assertThat(list.readAll()).containsExactly(1, 2, 3, 4, 5, 7, 8, 9, 9, 1, 9)
    }

    @Test
    fun testAddAllEmpty() = runTest {
        val list = redisson.getList<Int>("list")
        Assert.assertEquals(false, list.addAll(emptyList()))
        Assert.assertEquals(0, list.size())
    }

    @Test
    fun testContainsAll() = runTest {
        val list = redisson.getList<Int>("list")
        for (i in 0..199) {
            list.add(i)
        }
        Assert.assertTrue(list.containsAll(listOf(30, 11)))
        Assert.assertFalse(list.containsAll(listOf(30, 711, 11)))
        Assert.assertTrue(list.containsAll(listOf(30)))
    }

    @Test
    fun testContainsAllEmpty() = runTest {
        val list = redisson.getList<Int>("list")
        for (i in 0..199) {
            list.add(i)
        }
        Assert.assertTrue(list.containsAll(emptyList<Any>()))
        Assert.assertTrue(listOf(1).containsAll(emptyList<Any>()))
    }

    @Test
    fun testIteratorSequence() = runTest {
        val list = redisson.getList<String>("list2")
        list.add("1")
        list.add("4")
        list.add("2")
        list.add("5")
        list.add("3")
        checkIterator(list)
        // to test "memory effect" absence
        checkIterator(list)
    }

    private suspend fun checkIterator(list: RListCoroutines<String>) {
        var iteration = 0
        val iterator: Iterator<String> = list.iterator()
        while (iterator.hasNext()) {
            val value = iterator.next()
            val val1 = list.get(iteration)
            Assert.assertEquals(val1, value)
            iteration++
        }
        Assert.assertEquals(list.size(), iteration)
    }

    @Test
    fun testContains() = runTest {
        val list = redisson.getList<String>("list")
        list.add("1")
        list.add("4")
        list.add("2")
        list.add("5")
        list.add("3")
        Assert.assertTrue(list.contains("3"))
        Assert.assertFalse(list.contains("31"))
        Assert.assertTrue(list.contains("1"))
    }

    @Test
    fun testAddGet() = runTest {
        val list = redisson.getList<String>("list")
        list.add("1")
        list.add("4")
        list.add("2")
        list.add("5")
        list.add("3")
        val val1 = list.get(0)
        Assert.assertEquals("1", val1)
        val val2 = list.get(3)
        Assert.assertEquals("5", val2)
    }

    @Test
    fun testDuplicates() = runTest {
        val list = redisson.getList<TestObject>("list")
        list.add(TestObject("1", "2"))
        list.add(TestObject("1", "2"))
        list.add(TestObject("2", "3"))
        list.add(TestObject("3", "4"))
        list.add(TestObject("5", "6"))
        Assert.assertEquals(5, list.size())
    }

    @Test
    fun testSize() = runTest {
        val list = redisson.getList<String>("list")
        list.add("1")
        list.add("2")
        list.add("3")
        list.add("4")
        list.add("5")
        list.add("6")
        assertThat(list.readAll()).containsExactly("1", "2", "3", "4", "5", "6")
        list.remove("2")
        assertThat(list.readAll()).containsExactly("1", "3", "4", "5", "6")
        list.remove("4")
        assertThat(list.readAll()).containsExactly("1", "3", "5", "6")
    }

    @Test
    fun testCodec() = runTest {
        val list = redisson.getList<Any>("list")
        list.add(1)
        list.add(2L)
        list.add("3")
        list.add("e")
        assertThat(list.readAll()).containsExactly(1, 2L, "3", "e")
    }
}