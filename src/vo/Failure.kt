package vo

sealed class Failure(val msg: String, val exception: Throwable = Exception(msg)) : Exception(msg) {

    object UnknownError : Failure("Unknown Exception")
    class Error(e: Throwable) : Failure(e.localizedMessage, e)
    class EmptyData(msg: String = "No Data") : Failure(msg)
    class LoginError(msg: String) : Failure(msg)
    object TimeOutException : Failure("Timeout exceeded")
}



