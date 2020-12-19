package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.*
import org.junit.Test
import org.redisson.client.codec.DoubleCodec
import org.redisson.client.codec.IntegerCodec
import org.redisson.codec.CompositeCodec
import org.vitalyros.redisson.kotlin.coroutines.RMapCoroutines
import java.io.Serializable
import java.util.*

class MapDerivedTest : CoroutinesTest() {
    class SimpleKey : Serializable {
        var key: String? = null

        constructor() {}
        constructor(field: String?) {
            key = field
        }

        override fun toString(): String {
            return "key: $key"
        }

        override fun hashCode(): Int {
            val prime = 31
            var result = 1
            result = prime * result + if (key == null) 0 else key.hashCode()
            return result
        }

        override fun equals(obj: Any?): Boolean {
            if (this === obj) return true
            if (obj == null) return false
            if (javaClass != obj.javaClass) return false
            val other = obj as SimpleKey
            if (key == null) {
                if (other.key != null) return false
            } else if (key != other.key) return false
            return true
        }
    }

    class SimpleValue : Serializable {
        var value: String? = null

        constructor() {}
        constructor(field: String?) {
            value = field
        }

        override fun toString(): String {
            return "value: $value"
        }

        override fun hashCode(): Int {
            val prime = 31
            var result = 1
            result = prime * result + if (value == null) 0 else value.hashCode()
            return result
        }

        override fun equals(obj: Any?): Boolean {
            if (this === obj) return true
            if (obj == null) return false
            if (javaClass != obj.javaClass) return false
            val other = obj as SimpleValue
            if (value == null) {
                if (other.value != null) return false
            } else if (value != other.value) return false
            return true
        }
    }

    @Test
    fun testIteratorSequence() = runTest {
        val map = redisson.getMap<Long, Long>("map")
        for (i in 0..999) {
            map.put(i.toLong(), i.toLong())
        }
        val setCopy: MutableMap<Long, Long> = HashMap()
        for (i in 0..999) {
            setCopy[i.toLong()] = i.toLong()
        }
        checkIterator(map, setCopy)
    }

    private suspend fun <K, V> checkIterator(set: RMapCoroutines<K, V>, setCopy: MutableMap<K, V>) {
        val iterator = set.entryIterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (!setCopy.remove(entry.key, entry.value)) {
                fail()
            }
        }
        assertEquals(0, setCopy.size.toLong())
    }

    @Test
    fun testAddAndGet() = runTest {
        val map = redisson.getMap<Int, Int>("getAll", CompositeCodec(redisson.config.codec, IntegerCodec.INSTANCE))
        map.put(1, 100)
        var res: Int? = map.addAndGet(1, 12)
        assertEquals(112, res)
        res = map.get(1)
        assertEquals(112, res)
        val map2 = redisson.getMap<Int, Double>("getAll2", CompositeCodec(redisson.config.codec, DoubleCodec.INSTANCE))
        map2.put(1, 100.2)
        var res2 = map2.addAndGet(1, 12.1)
        assertTrue(112.3.compareTo(res2!!) == 0)
        res2 = map2.get(1)
        assertTrue(112.3.compareTo(res2!!) == 0)
        val mapStr = redisson.getMap<String, Int>("mapStr", CompositeCodec(redisson.config.codec, IntegerCodec.INSTANCE))
        assertThat(mapStr.put("1", 100)).isNull()
        assertThat(mapStr.addAndGet("1", 12)).isEqualTo(112)
        assertThat(mapStr.get("1")).isEqualTo(112)
    }

    @Test
    fun testGetAll() = runTest {
        val map= redisson.getMap<Int, Int>("getAll")
        map.put(1, 100)
        map.put(2, 200)
        map.put(3, 300)
        map.put(4, 400)
        val filtered = map.getAll(HashSet(Arrays.asList(2, 3, 5)))
        val expectedMap: MutableMap<Int, Int> = HashMap()
        expectedMap[2] = 200
        expectedMap[3] = 300
        assertEquals(expectedMap, filtered)
    }

    @Test
    fun testGetAllWithStringKeys() = runTest {
        val map = redisson.getMap<String, Int>("getAllStrings")
        map.put("A", 100)
        map.put("B", 200)
        map.put("C", 300)
        map.put("D", 400)
        val filtered: Map<String, Int> = map.getAll(HashSet(Arrays.asList("B", "C", "E")))
        val expectedMap: MutableMap<String, Int> = HashMap()
        expectedMap["B"] = 200
        expectedMap["C"] = 300
        assertEquals(expectedMap, filtered)
    }

    @Test
    fun testInteger() = runTest {
        val map = redisson.getMap<Int, Int>("test_int")
        map.put(1, 2)
        map.put(3, 4)
        assertEquals(2, map.size())
        val val1 = map.get(1)
        assertEquals(2, val1)
        val val2 = map.get(3)
        assertEquals(4, val2)
    }

    @Test
    fun testLong() = runTest {
        val map = redisson.getMap<Long, Long>("test_long")
        map.put(1L, 2L)
        map.put(3L, 4L)
        assertEquals(2, map.size())
        val val1 = map.get(1L)
        assertEquals(2L, val1)
        val val2 = map.get(3L)
        assertEquals(4L, val2)
    }

    @Test
    fun testSimpleTypes() = runTest {
        val map = redisson.getMap<Int, String>("simple12")
        map.put(1, "12")
        map.put(2, "33")
        map.put(3, "43")
        val val1 = map.get(2)
        assertEquals("33", val1)
    }

    @Test
    fun testRemove() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("33"), SimpleValue("44"))
        map.put(SimpleKey("5"), SimpleValue("6"))
        map.remove(SimpleKey("33"))
        map.remove(SimpleKey("5"))
        assertEquals(1, map.size())
    }

    @Test
    fun testPutAll() = runTest {
        val map = redisson.getMap<Int, String>("simple")
        map.put(1, "1")
        map.put(2, "2")
        map.put(3, "3")
        val joinMap: MutableMap<Int, String> = HashMap()
        joinMap[4] = "4"
        joinMap[5] = "5"
        joinMap[6] = "6"
        map.putAll(joinMap)
        assertThat(map.keyIterator()).contains(1, 2, 3, 4, 5, 6)
    }

    @Test
    fun testContainsValue() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("33"), SimpleValue("44"))
        map.put(SimpleKey("5"), SimpleValue("6"))
        assertTrue(map.containsValue(SimpleValue("2")))
        assertFalse(map.containsValue(SimpleValue("441")))
        assertFalse(map.containsValue(SimpleKey("5")))
    }

    @Test
    fun testContainsKey() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("33"), SimpleValue("44"))
        map.put(SimpleKey("5"), SimpleValue("6"))
        assertTrue(map.containsKey(SimpleKey("33")))
        assertFalse(map.containsKey(SimpleKey("34")))
    }

    @Test
    fun testRemoveValue() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        val size = map.remove(SimpleKey("1"), SimpleValue("2"))
        assertTrue(size)
        val val1 = map.get(SimpleKey("1"))
        assertNull(val1)
        assertEquals(0, map.size())
    }

    @Test
    fun testRemoveValueFail() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        val removed = map.remove(SimpleKey("2"), SimpleValue("1"))
        assertFalse(removed)
        val size2 = map.remove(SimpleKey("1"), SimpleValue("3"))
        assertFalse(size2)
        val val1 = map.get(SimpleKey("1"))
        assertEquals("2", val1!!.value)
    }

    @Test
    fun testReplaceOldValueFail() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        val res = map.replace(SimpleKey("1"), SimpleValue("43"), SimpleValue("31"))
        assertFalse(res)
        val val1 = map.get(SimpleKey("1"))
        assertEquals("2", val1!!.value)
    }

    @Test
    fun testReplaceOldValueSuccess() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        val res = map.replace(SimpleKey("1"), SimpleValue("2"), SimpleValue("3"))
        assertTrue(res)
        val res1 = map.replace(SimpleKey("1"), SimpleValue("2"), SimpleValue("3"))
        assertFalse(res1)
        val val1 = map.get(SimpleKey("1"))
        assertEquals("3", val1!!.value)
    }

    @Test
    fun testReplaceValue() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        val res = map.replace(SimpleKey("1"), SimpleValue("3"))
        assertEquals("2", res!!.value)
        val val1 = map.get(SimpleKey("1"))
        assertEquals("3", val1!!.value)
    }

    @Test
    fun testReplace() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("33"), SimpleValue("44"))
        map.put(SimpleKey("5"), SimpleValue("6"))
        val val1 = map.get(SimpleKey("33"))
        assertEquals("44", val1!!.value)
        map.put(SimpleKey("33"), SimpleValue("abc"))
        val val2 = map.get(SimpleKey("33"))
        assertEquals("abc", val2!!.value)
    }

    @Test
    fun testPutGet() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("33"), SimpleValue("44"))
        map.put(SimpleKey("5"), SimpleValue("6"))
        val val1 = map.get(SimpleKey("33"))
        assertEquals("44", val1!!.value)
        val val2 = map.get(SimpleKey("5"))
        assertEquals("6", val2!!.value)
    }

    @Test
    fun testPutIfAbsent() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        val key = SimpleKey("1")
        val value = SimpleValue("2")
        map.put(key, value)
        assertEquals(value, map.putIfAbsent(key, SimpleValue("3")))
        assertEquals(value, map.get(key))
        val key1 = SimpleKey("2")
        val value1 = SimpleValue("4")
        assertNull(map.putIfAbsent(key1, value1))
        assertEquals(value1, map.get(key1))
    }

    @Test
    fun testSize() = runTest {
        val map = redisson.getMap<SimpleKey, SimpleValue>("simple")
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("3"), SimpleValue("4"))
        map.put(SimpleKey("5"), SimpleValue("6"))
        assertEquals(3, map.size())
        map.put(SimpleKey("1"), SimpleValue("2"))
        map.put(SimpleKey("3"), SimpleValue("4"))
        assertEquals(3, map.size())
        map.put(SimpleKey("1"), SimpleValue("21"))
        map.put(SimpleKey("3"), SimpleValue("41"))
        assertEquals(3, map.size())
        map.put(SimpleKey("51"), SimpleValue("6"))
        assertEquals(4, map.size())
        map.remove(SimpleKey("3"))
        assertEquals(3, map.size())
    }

    @Test
    fun testEmptyRemove() = runTest {
        val map = redisson.getMap<Int, Int>("simple")
        assertThat(map.remove(1, 3)).isFalse()
        map.put(4, 5)
        assertThat(map.remove(4, 5)).isTrue()
    }

    @Test
    fun testFastRemoveAsync() = runTest {
        val map = redisson.getMap<Int, Int>("simple")
        map.put(1, 3)
        map.put(3, 5)
        map.put(4, 6)
        map.put(7, 8)
        assertEquals(3L, map.fastRemove(1, 3, 7))
        assertEquals(1, map.size())
    }

    @Test
    fun testKeyIterator() = runTest {
        val map = redisson.getMap<Int, Int>("simple")
        map.put(1, 0)
        map.put(3, 5)
        map.put(4, 6)
        map.put(7, 8)
        val keys: MutableList<Int> = ArrayList(Arrays.asList(1, 3, 4, 7))
        val iterator: Iterator<Int> = map.keyIterator()
        while (iterator.hasNext()) {
            val value = iterator.next()
            if (!keys.remove(value)) {
                fail()
            }
        }
        assertEquals(0, keys.size.toLong())
    }

    @Test
    fun testValueIterator() = runTest {
        val map = redisson.getMap<Int, Int>("simple")
        map.put(1, 0)
        map.put(3, 5)
        map.put(4, 6)
        map.put(7, 8)
        val values: MutableList<Int> = ArrayList(Arrays.asList(0, 5, 6, 8))
        val iterator: Iterator<Int> = map.valueIterator()
        while (iterator.hasNext()) {
            val value = iterator.next()
            if (!values.remove(value)) {
                fail()
            }
        }
        assertEquals(0, values.size.toLong())
    }

    @Test
    fun testFastPut() = runTest {
        val map = redisson.getMap<Int, Int>("simple")
        assertTrue(map.fastPut(1, 2))
        assertFalse(map.fastPut(1, 3))
        assertEquals(1, map.size())
    }

    @Test
    fun testFastRemoveEmpty() = runTest {
        val map = redisson.getMap<Int, Int>("simple")
        map.put(1, 3)
        assertEquals(0, map.fastRemove())
        assertEquals(1, map.size())
    }
}