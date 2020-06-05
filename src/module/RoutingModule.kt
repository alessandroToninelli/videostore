package module

import business.usecase.UseCase
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Locations
import io.ktor.request.host
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import org.koin.ktor.ext.getKoin
import ui.WebRoute
import util.klog
import vo.ErrorResponse
import vo.doErrorResponse
import java.lang.NumberFormatException
import kotlin.reflect.full.isSubclassOf

class RoutingModule(private val webRoutes: List<WebRoute>): AppModule{
    override fun install(application: Application) {
        with(application){

            intercept(ApplicationCallPipeline.Call) {
                println("Interceptor: ${call.request.uri} from ${call.request.host()}")
            }

            routing {
                webRoutes.onEach { it.makeRoute(this) }
            }
        }
    }

}