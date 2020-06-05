package ui.order

import business.usecase.GetOrderUseCase
import business.usecase.exec
import context.OrderPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class OrderRoute (private val getOrderUseCase: GetOrderUseCase): WebRoute{
    override fun makeRoute(route: Route) {
        route.get<OrderPath>{
            val id = requireNotNull(it.id){ ErrorResponse.Type.MISSING_ID}
            exec(getOrderUseCase, id).collect { call.respondResource(it) }
        }
    }

}