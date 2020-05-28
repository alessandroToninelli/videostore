package context

import io.ktor.locations.Location

//film routes

@Location("/film")
data class FilmRoute(val id: Int? = null)

@Location("/films")
class FilmListRoute()

//user route

@Location("/user")
data class UserRoute(val id: Int? = null)

@Location("users")
class UserListRoute(val type: String)


//orer route
