package util

import kotlinx.coroutines.*

fun <T> List<T>.nullIfEmpty(): List<T>? {
    return this.ifEmpty { null }
}

suspend inline fun <T> suspendCoroutineWithTimeout(
    timeout: Long,
    crossinline block: (CancellableContinuation<T>) -> Unit
) = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine(block = block)
}
