package util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.koin.core.Koin
import vo.*
import kotlin.reflect.full.isSubclassOf

suspend inline fun <T> suspendCoroutineWithTimeout(
    timeout: Long,
    crossinline block: (CancellableContinuation<T>) -> Unit
) = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine(block = block)
}

val klog = KotlinLogging.logger("Logger")

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, object : TypeToken<T>(){}.type)


suspend fun <T> ApplicationCall.respondResource(resource: Resource<T>, errorStatus: HttpStatusCode = HttpStatusCode.InternalServerError) {
    resource.case(
        success = {res ->
            res.data?.let { respond(HttpStatusCode.OK, it as Any) } ?: respond(HttpStatusCode.NoContent, "null")
        },
        error = {
            respond(errorStatus, it.failure.toErrorResponse())
        })
}


//inline fun <reified T> Koin.getAllOfType(): Collection<T>{
//
////    val a = _scopeRegistry.rootScope._scopeDefinition.definitions.filter { it.primaryType.isSubclassOf(T::class) }
//
//}
