package presentation.module

import business.service.AppService
import context.FilmListRoute
import context.FilmRoute
import io.ktor.application.call
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
import vo.ErrorResponse

fun Route.filmModule() {

    val service: AppService by inject()

    get<FilmListRoute> {
       service.getFilmList().collect { call.respondResource(it) }
    }

    get<FilmRoute> {
        call.respondText { "film Detail" }
    }

    post<FilmRoute> {

        val param = call.receiveParameters()
        val title = param["title"]
            ?: kotlin.run { call.respond(ErrorResponse(Type.INVALID_TITLE)); return@post }
        val director = param["director"]
            ?: kotlin.run { call.respond(ErrorResponse(Type.INVALID_DIRECTOR)); return@post }
        val durationTimeMillisString = param["duration"]
            ?: kotlin.run { call.respond(ErrorResponse(Type.INVALID_DURATION)); return@post }

        val durationTimeMillisLong =
            kotlin.runCatching { durationTimeMillisString.toLong() }.getOrNull()
                ?: kotlin.run{ call.respond(ErrorResponse(Type.INVALID_DURATION)); return@post }

        service.insertNewFilm(title, director, durationTimeMillisLong).collect {
            call.respondResource(it)
        }
    }

}