package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.Serializable
import java.util.*

class ListMultimapDerivedTest : CoroutinesTest() {
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
    fun testDelete() = runTest {
        val testList = redisson.getListMultimap<String, String>("test")
        testList.put("1", "01")
        testList.put("1", "02")
        testList.put("1", "03")
        val list = testList["1"]
        list.delete()
        Assertions.assertThat(testList.size()).isZero()
        Assertions.assertThat(testList["1"].size()).isZero()
    }

    @Test
    fun testReadAllKeySet() = runTest {
        val map = redisson.getListMultimap<String, String>("test1")
        map.put("1", "4")
        map.put("2", "5")
        map.put("3", "6")
        Assertions.assertThat(map.readAllKeySet()).containsExactly("1", "2", "3")
    }

// todo fix java.lang.NoSuchMethodException: org.redisson.RedissonListMultimap.readAll()
//
//    @Test
//    fun testSize() = runTest {
//        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("1"), SimpleValue("4"))
//        Assertions.assertThat(map.size()).isEqualTo(3)
//        Assertions.assertThat(map.fastRemove(SimpleKey("0"))).isEqualTo(1)
//        val s = map.get(SimpleKey("0"))
//        Assertions.assertThat(s.readAll()).isEmpty()
//        Assertions.assertThat(map.size()).isEqualTo(1)
//    }

// todo fix java.lang.NoSuchMethodException: org.redisson.RedissonListMultimap.readAll()
//
//    @Test
//    fun testKeySize() = runTest {
//        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("1"), SimpleValue("4"))
//        Assertions.assertThat(map.keySize()).isEqualTo(2)
//        Assertions.assertThat(map.fastRemove(SimpleKey("0"))).isEqualTo(1)
//        val s = map.get(SimpleKey("0"))
//        Assertions.assertThat(s.readAll()).isEmpty()
//        Assertions.assertThat(map.size()).isEqualTo(1)
//    }

// todo fix java.lang.NoSuchMethodException: org.redisson.RedissonListMultimap.readAll()
//
//    @Test
//    fun testPut() = runTest {
//        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("{multi.map}.some.key")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("0"), SimpleValue("3"))
//        map.put(SimpleKey("0"), SimpleValue("3"))
//        map.put(SimpleKey("3"), SimpleValue("4"))
//        Assertions.assertThat(map.size()).isEqualTo(5)
//        val s1 = map.get(SimpleKey("0"))
//        Assertions.assertThat(s1.readAll()).containsExactly(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"), SimpleValue("3"))
//        val allValues = map.getAll(SimpleKey("0"))
//        Assertions.assertThat(allValues).containsExactly(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"), SimpleValue("3"))
//        val s2 = map.get(SimpleKey("3"))
//        Assertions.assertThat(s2.readAll()).containsExactly(SimpleValue("4"))
//    }

// todo fix java.lang.NoSuchMethodException: org.redisson.RedissonListMultimap.readAll()
//
//    @Test
//    fun testRemoveAllFromCollection() = runTest {
//        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("0"), SimpleValue("3"))
//        val values: Collection<SimpleValue> = Arrays.asList(SimpleValue("1"), SimpleValue("2"))
//        Assertions.assertThat(map.get(SimpleKey("0")).removeAll(values)).isTrue()
//        Assertions.assertThat(map.get(SimpleKey("0")).size()).isEqualTo(1)
//        Assertions.assertThat(map.get(SimpleKey("0")).removeAll(Arrays.asList(SimpleValue("3")))).isTrue()
//        Assertions.assertThat(map.get(SimpleKey("0")).size()).isZero()
//        Assertions.assertThat(map.get(SimpleKey("0")).removeAll(Arrays.asList(SimpleValue("3")))).isFalse()
//    }

    @Test
    fun testRemoveAll() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("0"), SimpleValue("2"))
        map.put(SimpleKey("0"), SimpleValue("3"))
        val values = map.removeAll(SimpleKey("0"))
        Assertions.assertThat(values).containsExactly(SimpleValue("1"), SimpleValue("1"), SimpleValue("2"), SimpleValue("3"))
        Assertions.assertThat(map.size()).isZero()
        val values2 = map.removeAll(SimpleKey("0"))
        Assertions.assertThat(values2).isEmpty()
    }

    @Test
    fun testFastRemove() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("1"))).isTrue()
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("2"))).isTrue()
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("2"))).isTrue()
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("3"))).isTrue()
        val removed = map.fastRemove(SimpleKey("0"), SimpleKey("1"))
        Assertions.assertThat(removed).isEqualTo(1)
        Assertions.assertThat(map.size()).isZero()
    }

    @Test
    fun testContainsKey() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        Assertions.assertThat(map.containsKey(SimpleKey("0"))).isTrue()
        Assertions.assertThat(map.containsKey(SimpleKey("1"))).isFalse()
    }

    @Test
    fun testContainsValue() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("{1}test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        Assertions.assertThat(map.containsValue(SimpleValue("1"))).isTrue()
        Assertions.assertThat(map.containsValue(SimpleValue("0"))).isFalse()
    }

    @Test
    fun testContainsEntry() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        Assertions.assertThat(map.containsEntry(SimpleKey("0"), SimpleValue("1"))).isTrue()
        Assertions.assertThat(map.containsEntry(SimpleKey("0"), SimpleValue("2"))).isFalse()
    }
// todo fix java.lang.NoSuchMethodException: org.redisson.RedissonListMultimap.readAll()
//
//    @Test
//    fun testRange() = runTest {
//        val map = redisson.getListMultimap<Int, Int>("test1")
//        map.put(1, 1)
//        map.put(1, 2)
//        map.put(1, 3)
//        map.put(1, 4)
//        map.put(1, 5)
//        Assertions.assertThat(map[1].range(1)).containsExactly(1, 2)
//        Assertions.assertThat(map[1].range(1, 3)).containsExactly(2, 3, 4)
//    }

    @Test
    fun testRemove() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("0"), SimpleValue("2"))
        map.put(SimpleKey("0"), SimpleValue("3"))
        Assertions.assertThat(map.remove(SimpleKey("0"), SimpleValue("2"))).isTrue()
        Assertions.assertThat(map.remove(SimpleKey("0"), SimpleValue("5"))).isFalse()
        Assertions.assertThat(map.get(SimpleKey("0")).size()).isEqualTo(2)
        Assertions.assertThat(map.getAll(SimpleKey("0")).size).isEqualTo(2)
    }
// todo fix java.lang.NoSuchMethodException: org.redisson.RedissonListMultimap.readAll()
//
//    @Test
//    fun testPutAll() = runTest {
//        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
//        val values = Arrays.asList(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"), SimpleValue("3"))
//        Assertions.assertThat(map.putAll(SimpleKey("0"), values)).isTrue()
//        Assertions.assertThat(map.putAll(SimpleKey("0"), Arrays.asList(SimpleValue("1")))).isTrue()
//        val testValues = Arrays.asList(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"), SimpleValue("3"), SimpleValue("1"))
//        Assertions.assertThat(map.get(SimpleKey("0")).readAll()).containsExactlyElementsOf(testValues)
//    }

    @Test
    fun testKeySet() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("3"), SimpleValue("4"))
        Assertions.assertThat(map.readAllKeySet()).containsOnly(SimpleKey("0"), SimpleKey("3"))
        Assertions.assertThat(map.readAllKeySet().size).isEqualTo(2)
    }

    @Test
    fun testReplaceValues() = runTest {
        val map = redisson.getListMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("3"), SimpleValue("4"))
        val values = Arrays.asList(SimpleValue("11"), SimpleValue("12"), SimpleValue("12"))
        val oldValues = map.replaceValues(SimpleKey("0"), values)
        Assertions.assertThat(oldValues).containsExactly(SimpleValue("1"))
        val allValues = map.getAll(SimpleKey("0"))
        Assertions.assertThat(allValues).containsExactlyElementsOf(values)
    }
}