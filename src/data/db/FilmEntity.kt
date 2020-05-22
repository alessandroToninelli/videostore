package data.db

import model.Film
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FilmEntity(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<FilmEntity>(FilmTable)

    var title by FilmTable.title
    var director by FilmTable.director
    var duration by FilmTable.duration

    val orders by OrderEntity referrersOn OrderTable.film
}