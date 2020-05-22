package data.db

import model.User
import model.UserType
import model.toUser
import model.toUsers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

interface UserDao {

    suspend fun insert(name: String, surname: String, email: String, type: UserType): DbResponse<Int>
    suspend fun delete(id: Int): DbResponse<Unit?>
    suspend fun getById(userId: Int): DbResponse<User?>
    suspend fun getAll(): DbResponse<List<User>>

}


class UserDaoImpl : UserDao {

    init {

        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    override suspend fun insert(name: String, surname: String, email: String, type: UserType): DbResponse<Int> {
        return dbQuery {
            UserTable.insert {  }
            UserEntity.new {
                this.name = name
                this.surname = surname
                this.email = email
                this.type = type
            }.id.value
        }
    }

    override suspend fun delete(id: Int): DbResponse<Unit?> {
        return dbQuery {
            UserEntity.findById(id)?.delete()
        }
    }

    override suspend fun getById(userId: Int): DbResponse<User?> {
        return dbQuery { UserEntity.findById(userId)?.toUser() }
    }

    override suspend fun getAll(): DbResponse<List<User>> {
        return dbQuery { UserEntity.all().toList().toUsers() }
    }

}

