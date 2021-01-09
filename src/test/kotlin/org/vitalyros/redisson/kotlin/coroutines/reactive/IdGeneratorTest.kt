package org.vitalyros.redisson.kotlin.coroutines.reactive

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class IdGeneratorTest : CoroutinesTest() {
    @Test
    fun testEmpty() = runTest {
        val generator = redisson.getIdGenerator("test")
        for (i in 1L..100103L) {
            assertThat(generator.nextId()).isEqualTo(i)
        }
    }

    @Test
    fun testInit() = runTest {
        val generator = redisson.getIdGenerator("test")
        assertThat(generator.tryInit(12, 2931)).isTrue()
        assertThat(generator.tryInit(0, 1000)).isFalse()
        for (i in 12L..5000L) {
            assertThat(generator.nextId()).isEqualTo(i)
        }
    }
}