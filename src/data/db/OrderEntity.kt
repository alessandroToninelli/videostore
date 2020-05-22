package data.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class OrderEntity(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<OrderEntity>(OrderTable)

    var date by OrderTable.date
    var film by FilmEntity referencedOn OrderTable.film
    var user by UserEntity referencedOn OrderTable.user
}