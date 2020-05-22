package presentation.module

import business.usecase.InsertFilmUseCase
import business.usecase.exec
import data.repository.AppRepository
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.host
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.flow.collect
import model.Film
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.koin.core.qualifier.qualifier
import org.koin.ktor.ext.inject
import vo.Failure
import vo.case

fun Application.routingModule() {

    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound)
        }

        exception<Failure.EmptyData> {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    install(Locations)

    install(ContentNegotiation) {
        gson {}
    }

    intercept(ApplicationCallPipeline.Call) {
        println("Interceptor: ${call.request.uri} from ${call.request.host()}")
    }


    routing {


        val repo: AppRepository by inject()

        post<context.Film> {
            println(it.name)
            println(call.receive<model.Film>())
        }

        get<context.Film> {
            call.respondText { "HELLO WORLD ${it.name}" }
        }


        get {
            val useCase = InsertFilmUseCase(repo)
            exec(useCase, InsertFilmUseCase.Param("StarWars", "Lucas", 234000)).collect {
                it.case(success = {}) }



        }


    }
}
