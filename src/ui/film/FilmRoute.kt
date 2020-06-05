package ui.film

import business.usecase.GetFilmUseCase
import business.usecase.exec
import context.FilmPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class FilmRoute (private val getFilmUseCase: GetFilmUseCase) : WebRoute {
    override fun makeRoute(route: Route) {
        route.get<FilmPath> {
            val id = requireNotNull(it.id){ ErrorResponse.Type.MISSING_ID}
            exec(getFilmUseCase, id).collect {
                call.respondResource(it)
            }
        }
    }

}