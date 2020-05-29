package business.usecase

import data.repository.AppRepository
import model.UserType
import vo.*

class InsertUserUseCase(private val repo: AppRepository): UseCase<InsertUserUseCase.Param, Int>(){

    data class Param(val name: String, val surname: String, val email: String, val type: UserType = UserType.USER)

    override suspend fun exec(param: Param?, onResult: (Either<Failure, Int>) -> Unit) {
        param?.let {
            onResult(repo.insertUser(it.name, it.surname, it.email, it.type))
        }
    }
}