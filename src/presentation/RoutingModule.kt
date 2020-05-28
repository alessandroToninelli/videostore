package presentation

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.request.host
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.routing
import vo.ErrorResponse
import vo.doErrorResponse

fun Application.routingModule() {

    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respond(HttpStatusCode.NotFound)
        }

        exception<IllegalArgumentException> {
            it.message?.let {typeError ->
                call.respond(HttpStatusCode.BadRequest, doErrorResponse(ErrorResponse.Type.valueOf(typeError)))
            }
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

        filmModule()

        userModule()

        orderModule()

    }
}
