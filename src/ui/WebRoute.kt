package ui

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpMethod
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.Route
import kotlin.reflect.KClass

interface WebRoute{
    fun makeRoute(route: Route)
}