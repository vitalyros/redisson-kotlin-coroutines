package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.*
import org.junit.Test
import org.redisson.client.codec.StringCodec
import java.util.*

class BucketsDerivedTest : CoroutinesTest() {
    @Test
    fun testGet() = runTest {
        redisson.getBucket<Any>("test1").set("someValue1")
        redisson.getBucket<Any>("test2").delete()
        redisson.getBucket<Any>("test3").set("someValue3")
        redisson.getBucket<Any>("test4").delete()
        val result: Map<String, String> = redisson.getBuckets().get("test1", "test2", "test3", "test4")
        val expected: MutableMap<String, String> = HashMap()
        expected["test1"] = "someValue1"
        expected["test3"] = "someValue3"
        assertEquals(expected, result)
    }

    @Test
    fun testCodec() = runTest {
        val buckets = redisson.getBuckets(StringCodec.INSTANCE)
        var items = buckets.get<String>("buckets:A", "buckets:B", "buckets:C").toMutableMap()
        items["buckets:A"] = "XYZ"
        items["buckets:B"] = "OPM"
        items["buckets:C"] = "123"
        buckets.set(items)
        items = buckets.get<String>("buckets:A", "buckets:B", "buckets:C").toMutableMap()
        assertEquals(3, items.size.toLong())
        assertEquals("XYZ", items["buckets:A"])
    }

    @Test
    fun testSet() = runTest {
        val buckets: MutableMap<String, Int> = HashMap()
        buckets["12"] = 1
        buckets["41"] = 2
        redisson.getBuckets().set(buckets)
        val r1 = redisson.getBucket<Int>("12")
        assertThat(r1.get()).isEqualTo(1)
        val r2= redisson.getBucket<Int>("41")
        assertThat(r2.get()).isEqualTo(2)
    }

    @Test
    fun testTrySet() = runTest {
        redisson.getBucket<Any>("12").set("341")
        val buckets: MutableMap<String, Int?> = HashMap()
        buckets["12"] = 1
        buckets["41"] = 2
        assertThat(redisson.getBuckets().trySet(buckets)).isFalse()
        val r2 = redisson.getBucket<Any>("41")
        assertThat(r2.get()).isNull()
        val buckets2: MutableMap<String, Int?> = HashMap()
        buckets2["61"] = 1
        buckets2["41"] = 2
        assertThat(redisson.getBuckets().trySet(buckets2)).isTrue()
        val r1 = redisson.getBucket<Any>("61")
        assertThat(r1.get()).isEqualTo(1)
        val r3 = redisson.getBucket<Any>("41")
        assertThat(r3.get()).isEqualTo(2)
    }
}