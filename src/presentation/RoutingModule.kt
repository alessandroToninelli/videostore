package presentation

import bootstrapModule
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.request.host
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import util.klog
import vo.ErrorResponse
import vo.doErrorResponse
import java.lang.NumberFormatException

fun Application.routingModule() {

    install(StatusPages) {

        exception<IllegalArgumentException> {
            klog.error { it.localizedMessage }
            it.message?.let {typeError ->
                val error = ErrorResponse.Type.values().firstOrNull { it.toString() == typeError } ?: ErrorResponse.Type.ERROR
                call.respond(HttpStatusCode.BadRequest, doErrorResponse(error))
            }
        }

        exception<NumberFormatException> {
            klog.error { it.localizedMessage }
            call.respond(HttpStatusCode.BadRequest, doErrorResponse(ErrorResponse.Type.INVALID_NUMBER))
        }

        exception<ParameterConversionException> {
            klog.error { it.localizedMessage }
            call.respond(HttpStatusCode.BadRequest, doErrorResponse(ErrorResponse.Type.INVALID_PARAM))
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
