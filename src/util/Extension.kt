package util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import kotlinx.coroutines.*
import vo.*

suspend inline fun <T> suspendCoroutineWithTimeout(
    timeout: Long,
    crossinline block: (CancellableContinuation<T>) -> Unit
) = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine(block = block)
}


inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, object : TypeToken<T>(){}.type)


suspend fun <T> ApplicationCall.respondResource(resource: Resource<T>, errorStatus: HttpStatusCode = HttpStatusCode.InternalServerError) {
    resource.case(
        success = {
            respond(HttpStatusCode.OK, it.data ?: "null")
        },
        error = {
            respond(errorStatus, it.failure.toErrorResponse())
        })
}
