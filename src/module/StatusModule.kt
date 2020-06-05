package module

import data.repository.AppRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import util.klog
import vo.ErrorResponse
import vo.doErrorResponse
import java.lang.NumberFormatException

class StatusModule : AppModule {

    override fun install(application: Application) {

        application.install(StatusPages) {

            exception<IllegalArgumentException> {
                klog.error { it.localizedMessage }
                it.message?.let { typeError ->
                    val error = ErrorResponse.Type.values().firstOrNull { it.toString() == typeError }
                        ?: ErrorResponse.Type.ERROR
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
    }

}

