package model

import data.db.FilmEntity

data class Film(val id: Int, val title: String, val director: String, val durationTimeMillis: Long)

fun FilmEntity.toFilm(): Film{
    return Film(this.id.value, this.title, this.director, this.duration)
}

fun List<FilmEntity>.toFilms(): List<Film>{
    return this.map { it.toFilm() }
}
