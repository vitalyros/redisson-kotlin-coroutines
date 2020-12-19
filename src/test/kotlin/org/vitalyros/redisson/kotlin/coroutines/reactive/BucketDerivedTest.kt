package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.delay
import org.junit.Assert
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

open class BucketDerivedTest : CoroutinesTest() {

    @Test
    fun testExpire() = runTest {
        val bucket= redisson.getBucket<String>("test1")
        bucket.set("someValue", 1, TimeUnit.SECONDS)
        Assert.assertNotNull(bucket.get())
        delay(1100)
        Assert.assertNull(bucket.get())
    }

    @Test
    fun testRenamenx() = runTest {
        val bucket = redisson.getBucket<String>("test")
        bucket.set("someValue")
        val bucket2 = redisson.getBucket<String>("test2")
        bucket2.set("someValue2")
        Assert.assertTrue(bucket.renamenx("test1"))
        val oldBucket = redisson.getBucket<String>("test")
        Assert.assertNull(oldBucket.get())
        val newBucket = redisson.getBucket<String>("test1")
        Assert.assertEquals("someValue", newBucket.get())
        Assert.assertFalse(newBucket.renamenx("test2"))
    }

    @Test
    fun testRename() = runTest {
        val bucket = redisson.getBucket<String>("test")
        bucket.set("someValue")
        bucket.rename("test1")
        val oldBucket = redisson.getBucket<String>("test")
        Assert.assertNull(oldBucket.get())
        val newBucket = redisson.getBucket<String>("test1")
        Assert.assertEquals("someValue", newBucket.get())
    }


    @Test
    fun testSetGet() = runTest {
        val bucket = redisson.getBucket<String>("test")
        Assert.assertNull(bucket.get())
        val value = "somevalue"
        bucket.set(value)
        Assert.assertEquals(value, bucket.get())
    }


    @Test
    fun testSetDelete() = runTest {
        val bucket = redisson.getBucket<String>("test")
        val value = "somevalue"
        bucket.set(value)
        Assert.assertEquals(value, bucket.get())
        Assert.assertTrue(bucket.delete())
        Assert.assertNull(bucket.get())
        Assert.assertFalse(bucket.delete())
    }


    @Test
    fun testSetExist() = runTest {
        val bucket = redisson.getBucket<String>("test")
        Assert.assertNull(bucket.get())
        val value = "somevalue"
        bucket.set(value)
        Assert.assertEquals(value, bucket.get())
        Assert.assertTrue(bucket.isExists())
    }

    @Test
    fun testSetDeleteNotExist() = runTest {
        val bucket = redisson.getBucket<String>("test")
        Assert.assertNull(bucket.get())
        val value = "somevalue"
        bucket.set(value)
        Assert.assertEquals(value, bucket.get())
        Assert.assertTrue(bucket.isExists())
        bucket.delete()
        Assert.assertFalse(bucket.isExists())
    }

    @Test
    fun testFindPattern() = runTest {
        val names: Collection<String> = Arrays.asList("test:testGetPattern:one", "test:testGetPattern:two")
        val vals: Collection<String> = Arrays.asList("one-val", "two-val")
        redisson.getBucket<String>("test:three").set("three-val")
        redisson.getBucket<String>("test:testGetPattern:one").set("one-val")
        redisson.getBucket<String>("test:testGetPattern:two").set("two-val")

        val buckets = redisson.findBuckets<String>("test:testGetPattern:*")
        Assert.assertEquals(2, buckets.size.toLong())
        Assert.assertTrue(names.contains(buckets[0].name))
        Assert.assertTrue(names.contains(buckets[1].name))
        Assert.assertTrue(vals.contains(buckets[0].get()))
        Assert.assertTrue(vals.contains(buckets[1].get()))

        for (bucket in buckets) {
            bucket.delete()
        }
    }

}
