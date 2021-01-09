# redisson-kotlin-coroutines
A wrapper for Redisson Reactive API to use in kotlin coroutines


# Usage

1. Use your build tool to link the dependency

maven:
```
<dependency>
     <groupId>com.github.vitalyros</groupId>
     <artifactId>redisson-kotlin-coroutines-reactive</artifactId>
     <version>0.0.1</version>
</dependency>
```

gradle:
```
compile group: 'com.github.vitalyros', name: 'redisson-kotlin-coroutines-reactive', version: '0.0.1'
```

2. Import the `coroutines` extension function and use it to create the RedissonCoroutinesClient api from the RedissonReactiveClient api. The api can be used in coroutines and is non-blocking.
```
import org.vitalyros.redisson.kotlin.coroutines.RedissonCoroutinesClient
import org.vitalyros.redisson.kotlin.coroutines
...
val redisson: RedissonCoroutinesClient = Redisson.createReactive().coroutines()
...
runBlocking {
    val bucket = redisson.getBucket<String>("test")
    bucket.set("someValue")
    val result = bucket.get()
}
```

# Goals
- Adopt Redisson API for kotlin coroutines
- Implement the API by wrapping Reactive Implementation
- Test by adopting existing Redisson tests

# Implemented API Checklist

- [x] Keys
- [x] Bucket
- [x] Buckets
- [ ] IdGenerator
- [x] Semaphore
- [x] PermitExpirableSemaphore
- [x] Lock
- [x] ReadWriteLock
- [x] List
- [x] Map
- [x] Set
- [ ] ScoredSortedSet
- [ ] LexSortedSet
- [x] ListMultimap
- [x] SetMultimap
- [ ] Topic
- [ ] ReliableTopic
- [ ] PatternTopic
- [ ] Queue
- [ ] RingBuffer
- [ ] BlockingQueue
- [ ] BlockingDeque
- [ ] TransferQueue
- [ ] Deque
- [x] AtomicLong
- [x] AtomicDouble
- [ ] Remote
- [ ] Script
- [ ] Transaction
- [ ] NodesGroup
- [ ] Batch
- [ ] SetCache
- [ ] MapCache
- [ ] HyperLogLog 
- [ ] CountDownLatch
- [ ] Stream
- [ ] BinaryStream
- [ ] TimeSeries
- [ ] Geo