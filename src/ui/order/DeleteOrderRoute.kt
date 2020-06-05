package ui.order

import business.usecase.DeleteOrderUseCase
import business.usecase.exec
import context.OrderPath
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class DeleteOrderRoute(private val deleteOrderUseCase: DeleteOrderUseCase) : WebRoute {
    override fun makeRoute(route: Route) {
        route.delete<OrderPath> {
            val id = requireNotNull(it.id) { ErrorResponse.Type.MISSING_ID }
            exec(deleteOrderUseCase, id).collect { call.respondResource(it) }
        }

    }
}