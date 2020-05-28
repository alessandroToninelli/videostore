package vo

import javax.xml.transform.ErrorListener


class ErrorResponse(val errType: Type, val message: String) {

    enum class Type {
        INVALID_TITLE,
        INVALID_DIRECTOR,
        INVALID_DURATION,
        INVALID_ID,
        INVALID_NAME,
        INVALID_SURNAME,
        INVALID_USER_TYPE,
        INVALID_EMAIL,
        INVALID_USER_ID,
        INVALID_FILM_ID,
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