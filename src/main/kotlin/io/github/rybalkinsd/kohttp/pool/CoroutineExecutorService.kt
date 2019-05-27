package io.github.rybalkinsd.kohttp.pool

import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.CoroutineContext

/**
 * @Author: evgeny.vorobyev
 * @Since: 0.10.0
 */
class CoroutineExecutorService(
        private val executorService: ExecutorService = ForkJoinPool()
) : ExecutorService by executorService,
        ExecutorCoroutineDispatcher() {
    override val executor: Executor
        get() = this

    override fun close() {
        throw UnsupportedOperationException("${CoroutineExecutorService::class.java.name} can not be closed")
    }

    // todo research about DefaultExecutor.enqueue how it works, how can we implement same logic
    // todo watch Roman Elizarov articles/youtube videos and other about coroutines and its logic
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        println("Dispatch request with context ${context::class.java.name}")
        executorService.execute(block)
    }
}

fun main() {
    val context = CoroutineExecutorService()
    val task = GlobalScope.async(context = context) {
        println(Thread.currentThread().name)
        withContext(context = context) {
            println(Thread.currentThread().name)
        }
    }
    runBlocking {
        task.await()
    }
}
