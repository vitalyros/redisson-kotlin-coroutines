package org.vitaliros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test

class RedissonKeysDerivedTest : CoroutinesTest() {

    @Test
    fun testGetKeys() = runTest {
        val keys = redisson.getKeys()
        redisson.getBucket<Int>("test1").set(1)
        redisson.getBucket<Int>("test2").set(1)
        val k = keys.getKeys()
        assertThat(k).contains("test1", "test2")
    }

    @Test
    fun testKeysIterablePattern() = runTest {
        redisson.getBucket<String>("test1").set("someValue")
        redisson.getBucket<String>("test2").set("someValue")
        redisson.getBucket<String>("test12").set("someValue")
        val iterator: Iterator<String> = redisson.getKeys().getKeysByPattern("test?").iterator()
        var size = 0
        while (iterator.hasNext()) {
            val key = iterator.next()
            assertThat(key).isIn("test1", "test2")
            size += 1
        }
        assertThat(size).isEqualTo(2)
    }

    @Test
    fun testRandomKey() = runTest {
        val bucket = redisson.getBucket<String>("test1")
        bucket.set("someValue1")
        val bucket2 = redisson.getBucket<String>("test2")
        bucket2.set("someValue2")
        assertThat(redisson.getKeys().randomKey()).isIn("test1", "test2")
        redisson.getKeys().delete("test1")
        Assert.assertEquals("test2", redisson.getKeys().randomKey())
        redisson.getKeys().flushdb()
        Assert.assertNull(redisson.getKeys().randomKey())
    }

//todo: uncomment after implementing getMap
//    @Test
//    fun testDeleteByPattern() = runTest {
//        val bucket = redisson.getBucket<String>("test1")
//        bucket.set("someValue")
//        val map = redisson.getMap("test2")
//        map.fastPut("1", "2")
//        Assert.assertEquals(2, redisson.getKeys().deleteByPattern("test?").toInt())
//    }
//
//    @Test
//    fun testMassDelete() {
//        val bucket = redisson.getBucket<String>("test")
//        bucket.set("someValue")
//        val map = redisson.getMap("map2")
//        map.fastPut("1", "2")
//        Assert.assertEquals(2, redisson.getKeys().delete("test", "map2").toInt())
//        Assert.assertEquals(0, redisson.getKeys().delete("test", "map2").toInt())
//    }
}