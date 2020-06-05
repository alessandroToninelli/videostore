package ui.film

import business.usecase.GetAllFilmsUseCase
import business.usecase.exec
import context.FilmListPath
import io.ktor.application.call
import io.ktor.locations.get
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import ui.WebRoute
import util.respondResource

class FilmListRoute(private val getAllFilmsUseCase: GetAllFilmsUseCase): WebRoute {
    override fun makeRoute(route: Route) {
        route.get<FilmListPath> {
            exec(getAllFilmsUseCase).collect { call.respondResource(it) }
        }
    }

}