package vo

import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.sql.BatchUpdateException
import java.sql.SQLException

class ErrorResponse(val error: Type, val message: String = "") {

    enum class Type {
        INVALID_TITLE,
        INVALID_DIRECTOR,
        INVALID_DURATION,
        INVALID_NAME,
        INVALID_SURNAME,
        INVALID_EMAIL,
        DB_ERROR,
        ERROR,
        TIMEOUT
    }

}

fun Failure.toErrorResponse(): ErrorResponse {
    return when (this) {
        is Failure.DbError -> ErrorResponse(ErrorResponse.Type.DB_ERROR, this.msg)
        is Failure.TimeOutException -> ErrorResponse(ErrorResponse.Type.TIMEOUT, msg)
        else -> ErrorResponse(ErrorResponse.Type.ERROR, this.msg)
    }
}