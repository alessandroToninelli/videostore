package data.db

import org.jetbrains.exposed.dao.id.IntIdTable

object FilmTable: IntIdTable() {
    val title = varchar("title", 50)
    val director = varchar("director", 50)
    val duration = long("duration")
}