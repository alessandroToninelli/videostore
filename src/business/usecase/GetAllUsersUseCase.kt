package business.usecase

import data.repository.AppRepository
import model.Film
import model.User
import vo.Either
import vo.Failure

class GetAllUsersUseCase (private val repo: AppRepository): UseCase<Nothing, List<User>>(){
    override suspend fun exec(param: Nothing?, onResult: (Either<Failure, List<User>>) -> Unit) {
        onResult(repo.getAllUsers())
    }

}