package business.usecase

import data.repository.AppRepository
import vo.Either
import vo.Failure

class DeleteUserUseCase (private val repo: AppRepository): UseCase<Int, Unit?>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Unit?>) -> Unit) {
        param?.let { onResult(repo.deleteUser(it)) }
    }

}