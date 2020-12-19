package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions
import org.junit.Test
import org.redisson.api.RSetMultimap
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit

class SetMultimapDerivedTest : CoroutinesTest() {
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

// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//   
//    @Test
//    fun testRemoveAll2() = runTest {
//        val testMap = redisson.getSetMultimap<String, Long>("test-2")
//        testMap.fastRemove(*testMap.readAllKeySet().toTypedArray())
//        testMap.put("t1", 1L)
//        testMap.put("t1", 2L)
//        testMap.put("t1", 3L)
//        val set = testMap["t1"]
//        set.removeAll(Arrays.asList(1L, 2L))
//        Assertions.assertThat(testMap.size()).isOne()
//        Assertions.assertThat(testMap["t1"].size()).isEqualTo(1)
//        testMap.fastRemove(*testMap.readAllKeySet().toTypedArray())
//        Assertions.assertThat(testMap.size()).isZero()
//        Assertions.assertThat(testMap["t1"].size()).isZero()
//    }


// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testGetAdd() = runTest {
//        val multimap1 = redisson.getSetMultimap<String, Int>("myMultimap1")
//        val one = multimap1.get("1")
//        val two = multimap1.get("2")
//        val four = multimap1.get("4")
//        one.add(1)
//        one.add(2)
//        one.add(3)
//        two.add(5)
//        two.add(6)
//        four.add(7)
//        Assertions.assertThat(multimap1.readAllKeySet().iterator()).containsOnly("1", "2", "4")
//        Assertions.assertThat(multimap1.keySize()).isEqualTo(3)
//        Assertions.assertThat(multimap1.get("1").iterator().iterator()).containsOnly(1, 2, 3)
//        Assertions.assertThat(multimap1.get("2").iterator().iterator()).containsOnly(5, 6)
//        Assertions.assertThat(multimap1.get("4").iterator().iterator()).containsOnly(7)
//    }

// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testGetAddAll() = runTest {
//        val multimap1  = redisson.getSetMultimap<String, Int>("myMultimap1")
//        val one = multimap1.get("1")
//        val two = multimap1.get("2")
//        val four = multimap1.get("4")
//        one.addAll(Arrays.asList(1, 2, 3))
//        two.addAll(Arrays.asList(5, 6))
//        four.addAll(Arrays.asList(7))
//        Assertions.assertThat(multimap1.readAllKeySet().iterator()).containsOnly("1", "2", "4")
//        Assertions.assertThat(multimap1.keySize()).isEqualTo(3)
//        Assertions.assertThat(multimap1.get("1").iterator()).containsOnly(1, 2, 3)
//        Assertions.assertThat(multimap1.get("2").iterator()).containsOnly(5, 6)
//        Assertions.assertThat(multimap1.get("4").iterator()).containsOnly(7)
//    }


// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testGetRemove() = runTest {
//        val multimap1 = redisson.getSetMultimap<String, Int>("myMultimap1")
//        val one = multimap1.get("1")
//        val two = multimap1.get("2")
//        val four = multimap1.get("4")
//        one.add(1)
//        one.add(2)
//        one.add(3)
//        two.add(5)
//        two.add(6)
//        four.add(7)
//        Assertions.assertThat(one.remove(1)).isTrue()
//        Assertions.assertThat(one.remove(2)).isTrue()
//        Assertions.assertThat(two.remove(5)).isTrue()
//        Assertions.assertThat(four.remove(7)).isTrue()
//        Assertions.assertThat(multimap1.readAllKeySet().iterator()).containsOnly("1", "2")
//        Assertions.assertThat(multimap1.keySize()).isEqualTo(2)
//        Assertions.assertThat(multimap1.get("1").iterator()).containsOnly(3)
//        Assertions.assertThat(multimap1.get("2").iterator()).containsOnly(6)
//    }
    
// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testGetRemoveAll() = runTest {
//        val multimap1 = redisson.getSetMultimap<Any, Any>("myMultimap1")
//        val one = multimap1.get("1")
//        val two = multimap1.get("2")
//        val four = multimap1.get("4")
//        one.add(1)
//        one.add(2)
//        one.add(3)
//        two.add(5)
//        two.add(6)
//        four.add(7)
//        Assertions.assertThat(one.removeAll(Arrays.asList(1, 2, 3))).isTrue()
//        Assertions.assertThat(two.removeAll(Arrays.asList(5, 6))).isTrue()
//        Assertions.assertThat(four.removeAll(Arrays.asList(7))).isTrue()
//        Assertions.assertThat(four.removeAll(Arrays.asList(9))).isFalse()
//        Assertions.assertThat(multimap1.readAllKeySet()).isEmpty()
//        Assertions.assertThat(multimap1.keySize()).isEqualTo(0)
//    }


// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//   
//    @Test
//    fun testSize() = runTest {
//        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        Assertions.assertThat(map.size()).isEqualTo(2)
//        map.fastRemove(SimpleKey("0"))
//        val s = map.get(SimpleKey("0"))
//        Assertions.assertThat(s.readAll()).isEmpty()
//        Assertions.assertThat(map.size()).isEqualTo(0)
//    }

// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//   
//    @Test
//    fun testKeySize() = runTest {
//        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("1"), SimpleValue("3"))
//        Assertions.assertThat(map.keySize()).isEqualTo(2)
//        Assertions.assertThat(map.readAllKeySet().size).isEqualTo(2)
//        map.fastRemove(SimpleKey("0"))
//        val s = map.get(SimpleKey("0"))
//        Assertions.assertThat(s.readAll()).isEmpty()
//        Assertions.assertThat(map.keySize()).isEqualTo(1)
//    }

// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//   
//    @Test
//    fun testPut() = runTest {
//        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("0"), SimpleValue("3"))
//        map.put(SimpleKey("0"), SimpleValue("3"))
//        map.put(SimpleKey("3"), SimpleValue("4"))
//        Assertions.assertThat(map.size()).isEqualTo(4)
//        val s1 = map.get(SimpleKey("0"))
//        Assertions.assertThat(s1.iterator()).containsOnly(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"))
//        val allValues = map.getAll(SimpleKey("0"))
//        Assertions.assertThat(allValues.iterator()).containsOnly(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"))
//        val s2 = map.get(SimpleKey("0"))
//        Assertions.assertThat(s2.iterator()).containsOnly(SimpleValue("4"))
//    }
    
// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//    
//    @Test
//    fun testRemoveAllFromCollection() = runTest {
//        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
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
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("0"), SimpleValue("2"))
        map.put(SimpleKey("0"), SimpleValue("3"))
        val values = map.removeAll(SimpleKey("0"))
        Assertions.assertThat(values.iterator()).containsOnly(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"))
        Assertions.assertThat(map.size()).isZero()
        val values2 = map.removeAll(SimpleKey("0"))
        Assertions.assertThat(values2).isEmpty()
    }

    @Test
    fun testFastRemove() = runTest {
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("1"))).isTrue()
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("2"))).isTrue()
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("2"))).isFalse()
        Assertions.assertThat(map.put(SimpleKey("0"), SimpleValue("3"))).isTrue()
        val removed = map.fastRemove(SimpleKey("0"), SimpleKey("1"))
        Assertions.assertThat(removed).isEqualTo(1)
        Assertions.assertThat(map.size()).isZero()
    }

    @Test
    fun testContainsKey() = runTest {
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        Assertions.assertThat(map.containsKey(SimpleKey("0"))).isTrue()
        Assertions.assertThat(map.containsKey(SimpleKey("1"))).isFalse()
    }

    @Test
    fun testContainsValue() = runTest {
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        Assertions.assertThat(map.containsValue(SimpleValue("1"))).isTrue()
        Assertions.assertThat(map.containsValue(SimpleValue("0"))).isFalse()
    }

    @Test
    fun testContainsEntry() = runTest {
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        Assertions.assertThat(map.containsEntry(SimpleKey("0"), SimpleValue("1"))).isTrue()
        Assertions.assertThat(map.containsEntry(SimpleKey("0"), SimpleValue("2"))).isFalse()
    }


// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testRemove() = runTest {
//        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
//        map.put(SimpleKey("0"), SimpleValue("1"))
//        map.put(SimpleKey("0"), SimpleValue("2"))
//        map.put(SimpleKey("0"), SimpleValue("3"))
//        Assertions.assertThat(map.remove(SimpleKey("0"), SimpleValue("2"))).isTrue()
//        Assertions.assertThat(map.remove(SimpleKey("0"), SimpleValue("5"))).isFalse()
//        Assertions.assertThat(map.get(SimpleKey("0")).size()).isEqualTo(2)
//        Assertions.assertThat(map.getAll(SimpleKey("0")).size).isEqualTo(2)
//    }


// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testPutAll() = runTest {
//        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
//        val values = Arrays.asList(SimpleValue("1"), SimpleValue("2"), SimpleValue("3"))
//        Assertions.assertThat(map.putAll(SimpleKey("0"), values)).isTrue()
//        Assertions.assertThat(map.putAll(SimpleKey("0"), Arrays.asList(SimpleValue("1")))).isFalse()
//        Assertions.assertThat(map.get(SimpleKey("0")).iterator()).containsOnlyElementsOf(values)
//    }

    @Test
    fun testKeySet() = runTest {
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("3"), SimpleValue("4"))
        Assertions.assertThat(map.readAllKeySet().iterator()).containsOnly(SimpleKey("0"), SimpleKey("3"))
        Assertions.assertThat(map.readAllKeySet().size).isEqualTo(2)
    }


    @Test
    fun testExpire() = runTest {
        val map = redisson.getSetMultimap<String, String>("String")
        map.put("1", "2")
        map.put("2", "3")
        map.expire(100, TimeUnit.MILLISECONDS)
        Thread.sleep(500)
        Assertions.assertThat(map.size()).isZero()
    }

    @Test
    fun testReplaceValues() = runTest {
        val map = redisson.getSetMultimap<SimpleKey, SimpleValue>("test1")
        map.put(SimpleKey("0"), SimpleValue("1"))
        map.put(SimpleKey("3"), SimpleValue("4"))
        val values = Arrays.asList(SimpleValue("11"), SimpleValue("12"))
        val oldValues = map.replaceValues(SimpleKey("0"), values)
        Assertions.assertThat(oldValues.iterator()).containsOnly(SimpleValue("1"))
        val allValues = map.getAll(SimpleKey("0"))
        Assertions.assertThat(allValues.iterator()).containsOnlyElementsOf(values)
    }

    @Test
    fun testExpireAt() = runTest {
        val map = redisson.getSetMultimap<String, String>("String")
        map.put("1", "2")
        map.put("2", "3")
        map.expireAt(System.currentTimeMillis() + 100)
        Thread.sleep(500)
        Assertions.assertThat(map.size()).isZero()
    }

    @Test
    fun testClearExpire() = runTest {
        val map = redisson.getSetMultimap<String, String>("String")
        map.put("1", "2")
        map.put("2", "3")
        map.expireAt(System.currentTimeMillis() + 100)
        map.clearExpire()
        Thread.sleep(500)
        Assertions.assertThat(map.size()).isEqualTo(2)
    }

// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testDelete() = runTest {
//        val map = redisson.getSetMultimap<String, String>("String")
//        map.put("1", "2")
//        map.put("2", "3")
//        Assertions.assertThat(map.delete()).isTrue()
//        val map2 = redisson.getSetMultimap<String, String>("simple1")
//        Assertions.assertThat(map2.delete()).isFalse()
//        val multiset = redisson.getSetMultimap<String, String>("test")
//        multiset.put("1", "01")
//        multiset.put("1", "02")
//        multiset.put("1", "03")
//        val set = multiset["1"]
//        set.delete()
//        Assertions.assertThat(multiset.size()).isZero()
//        Assertions.assertThat(multiset["1"].size()).isZero()
//    }


// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//
//    @Test
//    fun testRename() = runTest {
//        val map = redisson.getSetMultimap<String, String>("String")
//        map.put("1", "2")
//        map.put("2", "3")
//        map.rename("simple2")
//        val map2 = redisson.getSetMultimap<String, String>("simple2")
//        Assertions.assertThat(map2.size()).isEqualTo(2)
//        Assertions.assertThat(map2["1"].iterator()).containsOnly("2")
//        Assertions.assertThat(map2["2"].iterator()).containsOnly("3")
//        val map3 = redisson.getSetMultimap<String, String>("simple")
//        Assertions.assertThat(map3.isExists()).isFalse()
//        Assertions.assertThat(map.readAllKeySet().isEmpty()).isTrue()
//    }

// todo fix after RedissonSetMultimapReactive.get cast bug in redisson has been fix
// Caused by: java.lang.ClassCastException: class org.redisson.RedissonListMultimap cannot be cast to class org.redisson.api.RSetMultimap (org.redisson.RedissonListMultimap and org.redisson.api.RSetMultimap are in unnamed module of loader 'app') at org.redisson.reactive.RedissonSetMultimapReactive.get(RedissonSetMultimapReactive.java:51)
//   
//    @Test
//    fun testRenamenx() = runTest {
//        val map = redisson.getSetMultimap<String, String>("String")
//        map.put("1", "2")
//        map.put("2", "3")
//        val map2 = redisson.getSetMultimap<String, String>("simple2")
//        map2.put("4", "5")
//        Assertions.assertThat(map.renamenx("simple2")).isFalse()
//        Assertions.assertThat(map.size()).isEqualTo(2)
//        Assertions.assertThat(map["1"].iterator()).containsOnly("2")
//        Assertions.assertThat(map["2"].iterator()).containsOnly("3")
//        Assertions.assertThat(map2["4"].iterator()).containsOnly("5")
//        Assertions.assertThat(map.renamenx("simple3")).isTrue()
//        val map3 = redisson.getSetMultimap<String, String>("simple")
//        Assertions.assertThat(map3.isExists()).isFalse()
//        Assertions.assertThat(map3.readAllKeySet().isEmpty()).isTrue()
//        val map4  = redisson.getSetMultimap<String, String>("simple3")
//        Assertions.assertThat(map4.size()).isEqualTo(2)
//        Assertions.assertThat(map4["1"].iterator()).containsOnly("2")
//        Assertions.assertThat(map4["2"].iterator()).containsOnly("3")
//    }
}