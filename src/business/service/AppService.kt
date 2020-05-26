package business.service

import business.usecase.*
import kotlinx.coroutines.flow.Flow
import model.Film
import model.Order
import model.User
import model.UserType
import vo.BoolResult
import vo.Resource


class AppService (
    private val deleteFilmUseCase: DeleteFilmUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getAdminUsersUseCase: GetAdminUsersUseCase,
    private val getAllFilmsUseCase: GetAllFilmsUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getFilmOrdersUseCase: GetFilmOrdersUseCase,
    private val getFilmUseCase: GetFilmUseCase,
    private val getUserOrdersUseCase: GetUserOrdersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val insertOrderUseCase: InsertOrderUseCase,
    private val insertFilmUseCase: InsertFilmUseCase
){

    fun deleteFilm(id: Int): Flow<Resource<Boolean>>{
        return exec(deleteFilmUseCase, id)
    }

    fun deleteOrder(id: Int): Flow<Resource<Boolean>>{
        return exec(deleteOrderUseCase, id)
    }

    fun deleteUser(id: Int): Flow<Resource<Boolean>>{
        return exec(deleteUserUseCase, id)
    }

    fun getAdminUsers(): Flow<Resource<List<User>>>{
        return exec(getAdminUsersUseCase)
    }

    fun getFilmList(): Flow<Resource<List<Film>>>{
        return exec(getAllFilmsUseCase)
    }

    fun getUserList(): Flow<Resource<List<User>>>{
        return exec(getAllUsersUseCase)
    }

    fun getAllOrdersByFilm(filmId: Int): Flow<Resource<List<Order>>>{
        return exec(getFilmOrdersUseCase, filmId )
    }

    fun getAllOrdersByUser(userId: Int): Flow<Resource<List<Order>>>{
        return exec(getUserOrdersUseCase, userId)
    }

    fun getSingleFilm(id: Int): Flow<Resource<Film?>>{
        return exec(getFilmUseCase, id)
    }

    fun getSingleUser(id: Int): Flow<Resource<User?>>{
        return exec(getUserUseCase, id)
    }

    fun insertNewUser(name: String, surname: String, email: String, type: UserType): Flow<Resource<Int>>{
        return exec(insertUserUseCase, InsertUserUseCase.Param(name, surname, email, type))
    }

    fun insertNewFilm(title: String, director: String, durationTimeMillis: Long): Flow<Resource<BoolResult>>{
        return exec(insertFilmUseCase, InsertFilmUseCase.Param(title, director, durationTimeMillis))
    }

    fun insertNewOrder(filmId: Int, userId: Int): Flow<Resource<Int>>{
        return exec(insertOrderUseCase, InsertOrderUseCase.Param(filmId, userId))
    }

}