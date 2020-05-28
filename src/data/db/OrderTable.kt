package data.db

import model.User
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.datetime


object OrderTable : IntIdTable() {
    val film = reference("film", FilmTable, ReferenceOption.CASCADE)
    val user = reference("client", UserTable.id, ReferenceOption.CASCADE)
    val date = datetime("date")
}