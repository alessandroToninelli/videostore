package module

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson

class ContentNegotiationModule : AppModule{
    override fun install(application: Application) {
        application.install(ContentNegotiation){
            gson {  }
        }
    }

}