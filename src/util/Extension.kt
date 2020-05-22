package util

import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun <T> List<T>.nullIfEmpty(): List<T>? {
    return this.ifEmpty { null }
}
