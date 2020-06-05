package module

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.locations.Locations

class LocationModule : AppModule{
    override fun install(application: Application) {
        application.install(Locations)
    }

}