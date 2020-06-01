package business.usecase

import data.repository.AppRepository
import vo.*

class DeleteUserUseCase(private val repo: AppRepository) : UseCase<Int, BoolResult>() {
    override suspend fun exec(param: Int?, onResult: (Either<Failure, BoolResult>) -> Unit) {
        param?.let {
            onResult(
                repo.deleteUser(it).mapRight { it.toBoolResult(doErrorResponse(ErrorResponse.Type.MISSING_ID)) })
        }
    }

}