package context

import io.ktor.locations.Location

//film routes

@Location("/film")
data class FilmRoute(val id: String? = null)

@Location("/films")
class FilmListRoute()

//user route

@Location("/user")
data class UserRoute(val id: Int? = null)

@Location("users")
class UserListRoute(val type: String)


//orer route
@Location("/order")
data class OrderRoute(val id: Int? = null)

@Location("/orders")
class OrderListRoute {
    @Location("/user")
    data class User(val parent: OrderListRoute, val id: Int? = null)

    @Location("/film")
    data class Film(val parent: OrderListRoute, val id: Int? = null)
}
