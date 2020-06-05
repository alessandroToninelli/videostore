package ui.user

import business.usecase.InsertUserUseCase
import business.usecase.exec
import context.UserPath
import io.ktor.application.call
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import model.UserType
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class InsertUserRoute(private val insertUserUseCase: InsertUserUseCase) : WebRoute {
    override fun makeRoute(route: Route) {
        route.post<UserPath> {

            val param = call.receiveParameters()

            val name = requireNotNull(param["name"]) { ErrorResponse.Type.MISSING_NAME }
            val surname = requireNotNull(param["surname"]) { ErrorResponse.Type.MISSING_SURNAME }
            val email = requireNotNull(param["email"]) { ErrorResponse.Type.MISSING_EMAIL }

            exec(
                insertUserUseCase,
                InsertUserUseCase.Param(name, surname, email, UserType.ADMIN)
            ).collect { call.respondResource(it) }
        }
    }

}