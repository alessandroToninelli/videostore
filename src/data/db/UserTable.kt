package data.db

import model.UserType
import org.jetbrains.exposed.dao.id.IntIdTable

//db table
object UserTable : IntIdTable() {
    val name = varchar("name", 50)
    val surname = varchar("surname", 50)
    val email = varchar("email", 50)
    val type = customEnumeration("userType", "UserType", {UserType.valueOf(it as String)}, {UserTypeObject("UserType", it)})
}

