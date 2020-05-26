package util

import com.google.gson.Gson
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.response.defaultTextContentType
import io.ktor.response.respond
import kotlinx.coroutines.*
import vo.*

fun <T> List<T>.nullIfEmpty(): List<T>? {
    return this.ifEmpty { null }
}

suspend inline fun <T> suspendCoroutineWithTimeout(
    timeout: Long,
    crossinline block: (CancellableContinuation<T>) -> Unit
) = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine(block = block)
}

suspend fun <T> ApplicationCall.respondResource(resource: Resource<T>, errorStatus: HttpStatusCode = HttpStatusCode.InternalServerError) {
    resource.case(
        success = {
            respond(HttpStatusCode.OK, it.data ?: "null")
        },
        error = {
            respond(errorStatus, it.failure.toErrorResponse())
        })
}