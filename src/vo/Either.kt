package vo


import data.db.DbResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.math.log

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


fun <T, R> DbResponse<T>.toEither(
    onSuccess: (T) -> R,
    onError: ((Throwable) -> Failure)
): Either<Failure, R> {
    return when (this) {
        is DbResponse.Success -> right(onSuccess(body))
        is DbResponse.Error -> left(onError(exception))
    }
}

fun <T, R> DbResponse<T>.toEither(
    onSuccess: (T) -> R
): Either<Failure, R> {
    return toEither(
        onSuccess,
        {
            Failure.Error((this as DbResponse.Error).exception)
        })
}

fun <T> DbResponse<T>.toEither(
): Either<Failure, T> {
    return toEither(
        {
            it
        },
        {
            Failure.Error((this as DbResponse.Error).exception)
        })
}