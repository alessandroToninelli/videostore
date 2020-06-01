package presentation

import business.service.AppService
import context.UserListRoute
import context.UserRoute
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import model.UserType
import org.koin.ktor.ext.inject
import util.respondResource
import vo.ErrorResponse

fun Route.userModule(){

    val service: AppService by inject()

    post<UserRoute>{

        val param = call.receiveParameters()

        val name = requireNotNull(param["name"]){ ErrorResponse.Type.MISSING_NAME}
        val surname = requireNotNull(param["surname"]){ ErrorResponse.Type.MISSING_SURNAME}
        val email = requireNotNull(param["email"]){ ErrorResponse.Type.MISSING_EMAIL}

        println(" $name $surname $email")
        service.insertNewUser(name, surname, email, UserType.ADMIN).collect { call.respondResource(it) }
    }

    get<UserRoute>{
        val id = requireNotNull(it.id){ ErrorResponse.Type.MISSING_ID }
        service.getSingleUser(id).collect { call.respondResource(it) }
    }

    get<UserListRoute>{
        val type = requireNotNull(it.type){ErrorResponse.Type.MISSING_USER_TYPE}
        when(type){
            "u" -> service.getUserList()
            "a" -> service.getAdminUsers()
            else -> throw IllegalArgumentException(ErrorResponse.Type.MISSING_USER_TYPE.toString())
        }.collect { call.respondResource(it) }
    }

    delete<UserRoute>{
        val id = requireNotNull(it.id){ErrorResponse.Type.MISSING_ID}
        service.deleteUser(id).collect { call.respondResource(it) }
    }



}