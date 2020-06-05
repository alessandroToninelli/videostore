package ui.user

import business.usecase.GetAdminUsersUseCase
import business.usecase.GetAllUsersUseCase
import business.usecase.exec
import context.UserListPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class UserListRoute(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getAdminUsersUseCase: GetAdminUsersUseCase
) : WebRoute {
    override fun makeRoute(route: Route) {
        route.get<UserListPath> {
            val type = requireNotNull(it.type) { ErrorResponse.Type.MISSING_USER_TYPE }
            when (type) {
                "u" -> exec(getAllUsersUseCase)
                "a" -> exec(getAdminUsersUseCase)
                else -> throw IllegalArgumentException(ErrorResponse.Type.MISSING_USER_TYPE.toString())
            }.collect { call.respondResource(it) }
        }
    }

}