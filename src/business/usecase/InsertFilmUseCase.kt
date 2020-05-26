package business.usecase

import data.repository.AppRepository
import vo.*

class InsertFilmUseCase(private val repo: AppRepository): UseCase<InsertFilmUseCase.Param, BoolResult>(){

    override suspend fun exec(param: Param?, onResult: (Either<Failure, BoolResult>) -> Unit) {
        param?.let {
            val result  = repo.insertFilm(it.title, it.director, it.durationTimeMillis).mapRight { BoolResult(Status.SUCCESS) }
            onResult(result)

        }
    }

    data class Param(val title: String, val director: String, val durationTimeMillis: Long)
}