package vo

class ErrorResponse(val errType: Type, val message: String) {

    enum class Type {
        MISSING_TITLE,
        MISSING_DIRECTOR,
        MISSING_DURATION,
        MISSING_ID,
        MISSING_NAME,
        MISSING_SURNAME,
        MISSING_USER_TYPE,
        MISSING_EMAIL,
        MISSING_USER_ID,
        MISSING_FILM_ID,
        INVALID_NUMBER,
        INVALID_PARAM,
        DB_ERROR,
        ERROR,
        TIMEOUT
    }

}


fun doErrorResponse(type: ErrorResponse.Type, msg: String = ""): ErrorResponse{
    return ErrorResponse(type, msg)
}

fun Failure.toErrorResponse(): ErrorResponse {
    return when (this) {
        is Failure.DbError -> ErrorResponse(ErrorResponse.Type.DB_ERROR, this.msg)
        is Failure.TimeOutException -> ErrorResponse(ErrorResponse.Type.TIMEOUT, msg)
        else -> ErrorResponse(ErrorResponse.Type.ERROR, this.msg)
    }
}