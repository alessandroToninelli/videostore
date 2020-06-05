package ui.user

import business.usecase.GetUserUseCase
import business.usecase.exec
import context.UserPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class UserRoute(private val getUserUseCase: GetUserUseCase) : WebRoute {
    override fun makeRoute(route: Route) {
        route.get<UserPath> {
            val id = requireNotNull(it.id) { ErrorResponse.Type.MISSING_ID }
            exec(getUserUseCase, id).collect { call.respondResource(it) }
        }
    }


}