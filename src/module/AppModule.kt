package module

import io.ktor.application.Application

interface AppModule {

    fun install(application: Application)

}