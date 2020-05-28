package presentation.module

import business.service.AppService
import context.FilmListRoute
import context.FilmRoute
import io.ktor.application.call
import io.ktor.locations.delete
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import kotlinx.coroutines.flow.collect
import org.koin.ktor.ext.inject
import util.respondResource
import vo.ErrorResponse.Type
import kotlin.contracts.contract

fun Route.filmModule() {

    val service: AppService by inject()

    get<FilmListRoute> {
       service.getFilmList().collect { call.respondResource(it) }
    }

    get<FilmRoute> {
        val id = requireNotNull(it.id){Type.INVALID_ID}
        service.getSingleFilm(id).collect {
            call.respondResource(it)
        }
    }

    post<FilmRoute> {

        val param = call.receiveParameters()
        val title = requireNotNull(param["title"]){Type.INVALID_TITLE}
        val director = requireNotNull(param["director"]){Type.INVALID_DIRECTOR}
        val durationTimeMillis = requireNotNull(param["duration"]?.toLong()){Type.INVALID_DURATION}



        service.insertNewFilm(title, director, durationTimeMillis).collect {
            call.respondResource(it)
        }
    }

    delete<FilmRoute> {
        val id = requireNotNull(it.id){Type.INVALID_ID}
        service.deleteFilm(id).collect {
            call.respondResource(it)
        }
    }

}