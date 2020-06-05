package context

import io.ktor.locations.Location

//film routes

@Location("/film")
data class FilmPath(val id: Int? = null)

@Location("/films")
class FilmListPath()

//user route

@Location("/user")
data class UserPath(val id: Int? = null)

@Location("users")
class UserListPath(val type: String)


//orer route
@Location("/order")
data class OrderPath(val id: Int? = null)

@Location("/orders")
class OrderListPath {
    @Location("/user")
    data class User(val parent: OrderListPath, val id: Int? = null)

    @Location("/film")
    data class Film(val parent: OrderListPath, val id: Int? = null)
}
