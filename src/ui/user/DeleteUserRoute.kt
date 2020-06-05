package ui.user

import business.usecase.DeleteUserUseCase
import business.usecase.exec
import context.UserPath
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class DeleteUserRoute(private val deleteUserUseCase: DeleteUserUseCase): WebRoute {
    override fun makeRoute(route: Route) {
        route.delete<UserPath>{
            val id = requireNotNull(it.id){ ErrorResponse.Type.MISSING_ID}
            exec(deleteUserUseCase, id).collect { call.respondResource(it) }
        }
    }
}