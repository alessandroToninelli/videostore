package ui.order

import business.usecase.GetUserOrdersUseCase
import business.usecase.exec
import context.OrderListPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class OrderListByUserRoute(private val getUserOrdersUseCase: GetUserOrdersUseCase): WebRoute {
    override fun makeRoute(route: Route) {
        route.get<OrderListPath.User> {
            val userId = requireNotNull(it.id){ ErrorResponse.Type.MISSING_ID}
            exec(getUserOrdersUseCase, userId).collect { call.respondResource(it) }
        }
    }


}