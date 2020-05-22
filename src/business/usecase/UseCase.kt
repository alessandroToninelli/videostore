package business.usecase

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import vo.Either
import vo.Failure
import vo.Resource

abstract class UseCase<P, R> : AbstractUseCase<P, R>() {

    abstract override suspend fun exec(param: P?, onResult: (Either<Failure, R>) -> Unit)

    override fun start(param: P?): Flow<Resource<R>> = callbackFlow {
        exec(param) { result ->
            result.either(
                {
                    sendBlocking(Resource.Error(it))
                    close()
                },
                {
                    sendBlocking(Resource.Success(it))
                    close()
                })
        }
        awaitClose { println("stream closed") }
    }
}

abstract class AbstractUseCase<in P, out R> {

    operator fun invoke(param: P?): Flow<Resource<R>> {
        return start(param).onStart { emit(Resource.Loading()) }.flowOn(Dispatchers.Default)
    }

    protected abstract suspend fun exec(param: P?, onResult: (Either<Failure, R>) -> Unit)

    abstract fun start(param: P?): Flow<Resource<R>>
}


fun <P, R> exec(
    useCase: AbstractUseCase<P, R>,
    param: P? = null

): Flow<Resource<R>> {
    return useCase(param)

}
