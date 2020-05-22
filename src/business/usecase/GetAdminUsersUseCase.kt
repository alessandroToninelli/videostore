package business.usecase

import data.repository.AppRepository
import model.User
import model.UserType
import vo.Either
import vo.Failure
import vo.left
import vo.right

class GetAdminUsersUseCase(private val repo: AppRepository): UseCase<Nothing, List<User>>(){
    override suspend fun exec(param: Nothing?, onResult: (Either<Failure, List<User>>) -> Unit) {
        val users = repo.getAllUsers()
        users.either(
            {
                onResult(left(it))
            },{
                val adminUser = it.filter { it.type == UserType.ADMIN }
                onResult(right(adminUser))
            })
    }

}