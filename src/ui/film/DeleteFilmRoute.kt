package ui.film

import business.usecase.DeleteFilmUseCase
import business.usecase.exec
import context.FilmPath
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class DeleteFilmRoute(private val deleteFilmUseCase: DeleteFilmUseCase) : WebRoute {
    override fun makeRoute(route: Route) {
        route.delete<FilmPath> {
            val id = requireNotNull(it.id) { ErrorResponse.Type.MISSING_ID }
            exec(deleteFilmUseCase, id).collect {
                call.respondResource(it)
            }
        }
    }
}