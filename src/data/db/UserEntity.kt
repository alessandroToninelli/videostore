package data.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

//single entity (row) of a table
class UserEntity (id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<UserEntity>(UserTable)

    var name by UserTable.name
    var surname by UserTable.surname
    var email by UserTable.email
    var type by UserTable.type

    val orders by OrderEntity referrersOn OrderTable.user

}