package context

import io.ktor.locations.Location

//film routes

@Location("/film")
data class FilmRoute(val title: String? = null)

@Location("/films")
class FilmListRoute()

//user route

@Location("/user")
data class UserRoute(val id: Int)

@Location("users")
class UserListRoute()