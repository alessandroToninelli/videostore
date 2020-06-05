package ui.film

import business.usecase.InsertFilmUseCase
import business.usecase.exec
import context.FilmPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource
import vo.ErrorResponse

class InsertFilmRoute(private val insertFilmUseCase: InsertFilmUseCase) : WebRoute {

    override fun makeRoute(route: Route) {
        route.post<FilmPath> {

            val param = call.receiveParameters()
            val title = requireNotNull(param["title"]) { ErrorResponse.Type.MISSING_TITLE }
            val director = requireNotNull(param["director"]) { ErrorResponse.Type.MISSING_DIRECTOR }
            val durationTimeMillis = requireNotNull(param["duration"]?.toLong()) { ErrorResponse.Type.MISSING_DURATION }

            exec(insertFilmUseCase, InsertFilmUseCase.Param(title, director, durationTimeMillis)).collect {
                call.respondResource(it)
            }
        }
    }


}