package data.db

import model.Film
import model.toFilm
import model.toFilms
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

interface FilmDao {

    suspend fun insert(title: String, director: String, durationTimeMillis: Long): DbResponse<Int>
    suspend fun delete(id: Int): DbResponse<Unit?>
    suspend fun getById(id: Int): DbResponse<Film?>
    suspend fun getAll(): DbResponse<List<Film>>

}

class FilmDaoImpl() : FilmDao {

    init {
        transaction { SchemaUtils.create(FilmTable) }
    }

    override suspend fun insert(title: String, director: String, durationTimeMillis: Long): DbResponse<Int> {
        return dbQuery {
            FilmEntity.new {
                this.title = title
                this.director = director
                this.duration = durationTimeMillis
            }.id.value
        }
    }

    override suspend fun delete(id: Int): DbResponse<Unit?> {
        return dbQuery { FilmEntity.findById(id)?.delete() }
    }

    override suspend fun getById(id: Int): DbResponse<Film?> {
        return dbQuery {
            FilmEntity.findById(id)?.toFilm()
        }
    }

    override suspend fun getAll(): DbResponse<List<Film>> {
        return dbQuery { FilmEntity.all().toList().toFilms() }
    }

}


