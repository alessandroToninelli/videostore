package ui.order

import business.usecase.GetFilmOrdersUseCase
import business.usecase.exec
import context.OrderListPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class OrderListByFilmRoute(private val getFilmOrdersUseCase: GetFilmOrdersUseCase): WebRoute {
    override fun makeRoute(route: Route) {
        route.get<OrderListPath.Film> {
            val filmId = requireNotNull(it.id){ ErrorResponse.Type.MISSING_ID}
            exec(getFilmOrdersUseCase, filmId).collect { call.respondResource(it) }
        }
    }


}