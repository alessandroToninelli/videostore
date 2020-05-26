package data.db

import model.User
import model.UserType
import model.toUser
import model.toUsers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import vo.DbResult
import vo.dbQuery

interface UserDao {

    suspend fun insert(name: String, surname: String, email: String, type: UserType): DbResult<Int>
    suspend fun delete(id: Int): DbResult<Boolean>
    suspend fun getById(userId: Int): DbResult<User?>
    suspend fun getAll(): DbResult<List<User>>

}


class UserDaoImpl : UserDao {

    init {

        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    override suspend fun insert(name: String, surname: String, email: String, type: UserType): DbResult<Int> {
        return dbQuery {
            UserTable.insert { }
            UserEntity.new {
                this.name = name
                this.surname = surname
                this.email = email
                this.type = type
            }.id.value
        }
    }

    override suspend fun delete(id: Int): DbResult<Boolean> {
        return dbQuery {
            UserEntity.findById(id)?.delete() != null
        }
    }

    override suspend fun getById(userId: Int): DbResult<User?> {
        return dbQuery { UserEntity.findById(userId)?.toUser() }
    }

    override suspend fun getAll(): DbResult<List<User>> {
        return dbQuery { UserEntity.all().toList().toUsers() }
    }

}

