package business.usecase

import data.repository.AppRepository
import model.User
import vo.Either
import vo.Failure

class GetUserUseCase (private val repo: AppRepository): UseCase<Int, User?>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, User?>) -> Unit) {
        param?.let { onResult(repo.getUserById(it)) }
    }

}