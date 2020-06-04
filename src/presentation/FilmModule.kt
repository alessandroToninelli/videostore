package presentation

import business.service.AppService
import context.FilmListRoute
import context.FilmRoute
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import org.koin.ktor.ext.inject
import util.respondResource
import vo.ErrorResponse.Type

fun Route.filmModule() {

    val service: AppService by inject()

    get<FilmListRoute> {
       service.getFilmList().collect { call.respondResource(it) }
    }

    get<FilmRoute> {
        val id = requireNotNull(it.id){Type.MISSING_ID}
        service.getSingleFilm(id).collect {

            call.respondResource(it)
        }
    }

    post<FilmRoute> {

        val param = call.receiveParameters()
        val title = requireNotNull(param["title"]){Type.MISSING_TITLE}
        val director = requireNotNull(param["director"]){Type.MISSING_DIRECTOR}
        val durationTimeMillis = requireNotNull(param["duration"]?.toLong()){Type.MISSING_DURATION}

        service.insertNewFilm(title, director, durationTimeMillis).collect {
            call.respondResource(it)
        }
    }

    delete<FilmRoute> {
        val id = requireNotNull(it.id){Type.MISSING_ID}
        service.deleteFilm(id).collect {
            call.respondResource(it)
        }
    }

}