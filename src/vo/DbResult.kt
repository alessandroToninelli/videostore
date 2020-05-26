package vo

import org.jetbrains.exposed.sql.transactions.transaction

sealed class DbResult<T> {

    companion object {

        fun <T> create(error: Throwable): DbResult<T> {
            return Error(error)
        }

        fun <T> create(data: T): DbResult<T> {
            return Success(data)
        }
    }

    data class Success<T>(
        val body: T
    ) : DbResult<T>()


    data class Error<T>(
        val exception: Throwable
    ) : DbResult<T>()

}

suspend fun <T> dbQuery(block: () -> T): DbResult<T> {
    return transaction {
        try {
            val response = block()
            DbResult.create(response)
        } catch (e: Throwable) {
//            e.printStackTrace()
            DbResult.create<T>(e)
        }
    }
}





