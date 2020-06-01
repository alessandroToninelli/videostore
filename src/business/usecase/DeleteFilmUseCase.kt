package business.usecase

import data.repository.AppRepository
import vo.*

class DeleteFilmUseCase(private val repo: AppRepository) : UseCase<Int, BoolResult>() {
    override suspend fun exec(param: Int?, onResult: (Either<Failure, BoolResult>) -> Unit) {
        param?.let {
            onResult(repo.deleteFilm(it).mapRight { it.toBoolResult(doErrorResponse(ErrorResponse.Type.MISSING_ID)) })
        }
    }

}