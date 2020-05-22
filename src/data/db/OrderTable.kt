package data.db

import model.User
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime


object OrderTable : IntIdTable() {
    val film = reference("film", FilmTable)
    val user = reference("client", UserTable.id).uniqueIndex()
    val date = datetime("date")
}