package vo


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

sealed class Either<out L, out R> {

    data class Left<out L>(val left: L) : Either<L, Nothing>()

    data class Right<out R>(val right: R) : Either<Nothing, R>()

    val isRight
        get() = this is Right<R>

    val isLeft
        get() = this is Left<L>

    fun either(fl: (L) -> Unit, fr: (R) -> Unit) {
        when (this) {
            is Left -> fl(left)
            is Right -> fr(right)
        }
    }


}

fun <L, R, newR> Either<L, R>.mapRight(transform: (R)->newR): Either<L,newR>{
    var newValue : newR? = null
    this.either({
        newValue = null
    },{
        newValue = transform(it)
    })
    return newValue?.let { right(it) } ?: left(this.leftOrNull()!!)
}


fun <L> Either<L, Any?>.leftOrNull(): L? {
    var result: L? = null
    this.either(
        {
            result = it
        },
        {
            result = null
        }
    )

    return result
}

fun <R> Either<Any, R>.rightOrNull(): R? {
    var result: R? = null
    this.either(
        {
            result = null
        },
        {
            result = it
        }
    )

    return result
}

fun <L> left(left: L) = Either.Left(left)

fun <R> right(right: R) = Either.Right(right)

fun <R> Flow<R>.either(action: (exception: Exception) -> Failure): Flow<Either<Failure, R>> =
    flow<Either<Failure, R>> {
        collect {
            emit(right(it))
        }
    }.catch {
        it.printStackTrace()
        emit(left(action(Exception(it))))
    }


fun <T, R> DbResult<T>.toEither(
    onSuccess: (T) -> R,
    onError: ((Throwable) -> Failure)
): Either<Failure, R> {
    return when (this) {
        is DbResult.Success -> right(onSuccess(body))
        is DbResult.Error -> left(onError(exception))
    }
}

fun <T, R> DbResult<T>.toEither(
    onSuccess: (T) -> R
): Either<Failure, R> {
    return toEither(
        onSuccess,
        {
            Failure.DbError((this as DbResult.Error).exception)
        })
}

fun <T> DbResult<T>.toEither(
): Either<Failure, T> {
    return toEither(
        {
            it
        },
        {
            Failure.DbError((this as DbResult.Error).exception)
        })
}