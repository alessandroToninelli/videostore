package business.usecase

import data.repository.AppRepository
import vo.Either
import vo.Failure

class InsertFilmUseCase(private val repo: AppRepository): UseCase<InsertFilmUseCase.Param, Int>(){

    override suspend fun exec(param: Param?, onResult: (Either<Failure, Int>) -> Unit) {
        param?.let {
            onResult(repo.insertFilm(it.title, it.director, it.durationTimeMillis))
        }
    }

    data class Param(val title: String, val director: String, val durationTimeMillis: Long)
}