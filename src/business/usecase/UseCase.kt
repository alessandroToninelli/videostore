package business.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import util.suspendCoroutineWithTimeout
import vo.Either
import vo.Failure
import vo.Resource
import kotlin.coroutines.resume

abstract class UseCase<P, R> : BaseUseCase<P, R>() {

    abstract override suspend fun exec(param: P?, onResult: (Either<Failure, R>) -> Unit)

    override fun start(param: P?, timeout: Long): Flow<Resource<R>> = callbackFlow {
        val resource = suspendCoroutineWithTimeout<Resource<R>>(timeout) {cont ->
            launch {
                exec(param) { result ->
                    result.either(
                        {
                            val resource: Resource.Error<R> = Resource.Error(it)
                            cont.resume(resource)
                        },
                        {
                            val resource: Resource.Success<R> = Resource.Success(it)
                            cont.resume(resource)
                        })
                }
            }
            cont.invokeOnCancellation { println("Job Cancelled")}
        }

        resource?.let { sendBlocking(it)} ?: sendBlocking(Resource.Error(Failure.TimeOutException))
        close()

        awaitClose { println("stream closed") }
    }
}

abstract class BaseUseCase<in P, out R> {

    operator fun invoke(param: P?, timeout: Long): Flow<Resource<R>> {
        return start(param, timeout).flowOn(Dispatchers.Default)
    }

    protected abstract suspend fun exec(param: P?, onResult: (Either<Failure, R>) -> Unit)

    abstract fun start(param: P?, timeout: Long): Flow<Resource<R>>
}


fun <P, R> exec(
    useCase: BaseUseCase<P, R>,
    param: P? = null,
    timeout: Long = 60000

) = useCase(param, timeout)
