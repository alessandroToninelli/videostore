package vo

sealed class Failure(open val msg: String) : Exception(msg) {

    data class Error(val e: Throwable) : Failure(e.localizedMessage)
    data class DbError(val exception: Throwable): Failure(exception.localizedMessage)
    object TimeOutException : Failure("Timeout exceeded")
}



