package business.usecase

import data.repository.AppRepository
import vo.Either
import vo.Failure

class DeleteOrderUseCase (private val repo: AppRepository): UseCase<Int, Boolean>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Boolean>) -> Unit) {
        param?.let { onResult(repo.deleteOrder(it)) }
    }

}