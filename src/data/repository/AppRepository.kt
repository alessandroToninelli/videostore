package data.repository

import data.db.*
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.supervisorScope
import model.*
import util.nullIfEmpty
import vo.*
import javax.sql.DataSource

interface AppRepository {

    //film
    suspend fun getAllFilms(): Either<Failure, List<Film>>

    suspend fun getFilmById(filmId: Int): Either<Failure, Film?>

    suspend fun insertFilm(title: String, director: String, durationTimeMillis: Long): Either<Failure, Int>

    suspend fun deleteFilm(filmId: Int): Either<Failure, Unit?>

    //user
    suspend fun getAllUsers(): Either<Failure, List<User>>

    suspend fun getUserById(userId: Int): Either<Failure, User?>

    suspend fun insertUser(name: String, surname: String, email: String, type: UserType): Either<Failure, Int>

    suspend fun deleteUser(userId: Int): Either<Failure, Unit?>

    //order
    suspend fun insertOrder(userId: Int, filmId: Int): Either<Failure, Int>

    suspend fun getOrderByUser(userId: Int): Either<Failure, List<Order>>

    suspend fun getOrderByFilm(filmId: Int): Either<Failure, List<Order>>

    suspend fun deleteOrder(orderId: Int): Either<Failure, Unit?>

}


class AppRepositoryImpl(
    private val filmDao: FilmDao,
    private val userDao: UserDao,
    private val orderDao: OrderDao
) : AppRepository {

    override suspend fun getAllFilms(): Either<Failure, List<Film>> {
        return filmDao.getAll().toEither()
    }

    override suspend fun getFilmById(filmId: Int): Either<Failure, Film?> {
        return filmDao.getById(filmId).toEither()
    }

    override suspend fun insertFilm(title: String, director: String, durationTimeMillis: Long): Either<Failure, Int> {
        return filmDao.insert(title, director, durationTimeMillis).toEither()
    }

    override suspend fun deleteFilm(filmId: Int): Either<Failure, Unit?> {
        return filmDao.delete(filmId).toEither()
    }

    override suspend fun getAllUsers(): Either<Failure, List<User>> {
        return userDao.getAll().toEither()
    }

    override suspend fun getUserById(userId: Int): Either<Failure, User?> {
        return userDao.getById(userId).toEither()
    }

    override suspend fun insertUser(
        name: String,
        surname: String,
        email: String,
        type: UserType
    ): Either<Failure, Int> {
        return userDao.insert(name, surname, email, type).toEither { it }
    }

    override suspend fun deleteUser(userId: Int): Either<Failure, Unit?> {
        return userDao.delete(userId).toEither()
    }

    override suspend fun insertOrder(userId: Int, filmId: Int): Either<Failure, Int> {
        return orderDao.insert(userId, filmId).toEither()
    }

    override suspend fun getOrderByUser(userId: Int): Either<Failure, List<Order>> {
        return orderDao.getOrdersByUser(userId).toEither()
    }

    override suspend fun getOrderByFilm(filmId: Int): Either<Failure, List<Order>> {
        return orderDao.getOrdersByFilm(filmId).toEither()
    }

    override suspend fun deleteOrder(orderId: Int): Either<Failure, Unit?> {
        return orderDao.delete(orderId).toEither()
    }


}