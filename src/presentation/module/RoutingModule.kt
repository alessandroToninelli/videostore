package presentation.module

import business.service.AppService
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.request.host
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.flow.collect
import org.koin.ktor.ext.inject
import vo.Failure

fun Application.routingModule() {

    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
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

        filmModule()

        userModule()

        orderModule()

    }
}
