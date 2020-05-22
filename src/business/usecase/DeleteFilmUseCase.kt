package business.usecase

import data.repository.AppRepository
import vo.Either
import vo.Failure

class DeleteFilmUseCase (private val repo: AppRepository): UseCase<Int, Unit?>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Unit?>) -> Unit) {
        param?.let { onResult(repo.deleteFilm(it)) }
    }

}