package org.vitalyros.redisson.kotlin.coroutines.reactive

import kotlinx.coroutines.reactive.awaitSingle
import org.redisson.api.RIdGeneratorReactive
import org.vitalyros.redisson.kotlin.coroutines.RIdGeneratorCoroutines

class IdGeneratorCoroutinesReactive(val wrapped: RIdGeneratorReactive): RIdGeneratorCoroutines {
    override suspend fun tryInit(value: Long, allocationSize: Long): Boolean =  wrapped.tryInit(value, allocationSize).awaitSingle()

    override suspend fun nextId(): Long = wrapped.nextId().awaitSingle()
}