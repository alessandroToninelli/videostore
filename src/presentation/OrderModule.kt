package presentation

import business.service.AppService
import context.OrderListRoute
import context.OrderRoute
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import org.koin.ktor.ext.inject
import util.respondResource
import vo.ErrorResponse

fun Route.orderModule(){

    val service: AppService by inject()


    get<OrderListRoute.Film> {
        val filmId = requireNotNull(it.id){ErrorResponse.Type.MISSING_ID}
        service.getAllOrdersByFilm(filmId).collect { call.respondResource(it) }
    }

    get<OrderListRoute.User> {
        val userId = requireNotNull(it.id){ErrorResponse.Type.MISSING_ID}
        service.getAllOrdersByUser(userId).collect { call.respondResource(it) }
    }

    get<OrderRoute>{
        val id = requireNotNull(it.id){ErrorResponse.Type.MISSING_ID}
        service.getSingleOrder(id).collect { call.respondResource(it) }
    }

    post<OrderRoute> {
        val param = call.receiveParameters()
        val filmId = requireNotNull(param["filmId"]?.toInt()){ErrorResponse.Type.MISSING_FILM_ID}
        val userId = requireNotNull(param["userId"]?.toInt()){ErrorResponse.Type.MISSING_USER_ID}
        service.insertNewOrder(filmId, userId).collect { call.respondResource(it) }
    }

    delete<OrderRoute> {
        val id = requireNotNull(it.id){ErrorResponse.Type.MISSING_ID}
        service.deleteOrder(id).collect { call.respondResource(it) }
    }



}