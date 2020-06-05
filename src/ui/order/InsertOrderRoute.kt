package ui.order

import business.usecase.InsertOrderUseCase
import business.usecase.exec
import context.OrderPath
import io.ktor.application.call
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class InsertOrderRoute(private val insertOrderUseCase: InsertOrderUseCase) : WebRoute {
    override fun makeRoute(route: Route) {
        route.post<OrderPath> {
            val param = call.receiveParameters()
            val filmId = requireNotNull(param["filmId"]?.toInt()) { ErrorResponse.Type.MISSING_FILM_ID }
            val userId = requireNotNull(param["userId"]?.toInt()) { ErrorResponse.Type.MISSING_USER_ID }
            exec(insertOrderUseCase, InsertOrderUseCase.Param(filmId, userId)).collect { call.respondResource(it) }
        }
    }


}