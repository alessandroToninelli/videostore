package data.db

import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

sealed class DbResponse<T> {

    companion object {

        fun <T> create(error: Throwable): DbResponse<T> {
            return Error(error)
        }

        fun <T> create(data: T): DbResponse<T> {
            return Success(data)
        }
    }

    data class Success<T>(
        val body: T
    ) : DbResponse<T>()


    data class Error<T>(
        val exception: Throwable
    ) : DbResponse<T>()

}

suspend fun <T> dbQuery(block: () -> T): DbResponse<T>{
    return transaction {
        try {
            val response = block()
            DbResponse.create(response)
        } catch (e: Throwable) {
            e.printStackTrace()
            DbResponse.create<T>(e)
        }
    }
}




