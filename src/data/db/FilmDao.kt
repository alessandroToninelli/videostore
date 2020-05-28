package data.db

import model.Film
import model.toFilm
import model.toFilms
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import vo.DbResult
import vo.dbQuery

interface FilmDao {

    suspend fun insert(title: String, director: String, durationTimeMillis: Long): DbResult<Int>
    suspend fun delete(id: Int): DbResult<Boolean>
    suspend fun getById(id: Int): DbResult<Film?>
    suspend fun getAll(): DbResult<List<Film>>

}

class FilmDaoImpl() : FilmDao {

    init {
        transaction { SchemaUtils.create(FilmTable) }

    }

    override suspend fun insert(title: String, director: String, durationTimeMillis: Long): DbResult<Int> {
        return dbQuery {
            FilmEntity.new {
                this.title = title
                this.director = director
                this.duration = durationTimeMillis
            }.id.value
        }
    }

    override suspend fun delete(id: Int): DbResult<Boolean> {
        return dbQuery { FilmEntity.findById(id)?.delete() != null }
    }

    override suspend fun getById(id: Int): DbResult<Film?> {
        return dbQuery {
            FilmEntity.findById(id)?.toFilm()
        }
    }

    override suspend fun getAll(): DbResult<List<Film>> {
        return dbQuery { FilmEntity.all().toList().toFilms() }
    }

}


