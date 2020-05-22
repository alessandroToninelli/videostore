package context

import io.ktor.locations.Location

@Location("/film")
data class Film(val name: String = "default")