package business.usecase

import data.repository.AppRepository
import vo.Either
import vo.Failure

class DeleteFilmUseCase (private val repo: AppRepository): UseCase<Int, Boolean>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Boolean>) -> Unit) {
        param?.let { onResult(repo.deleteFilm(it)) }
    }

}