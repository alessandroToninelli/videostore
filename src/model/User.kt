package model

import data.db.UserEntity

data class User(val id: Int, val name: String, val surname: String, val email: String, val type: UserType)

fun UserEntity.toUser(): User{
    return User(id.value, name, surname, email, type)
}

fun List<UserEntity>.toUsers(): List<User> = this.map { it.toUser() }