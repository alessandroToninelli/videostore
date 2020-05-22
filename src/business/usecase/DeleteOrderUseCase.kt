package business.usecase

import data.repository.AppRepository
import vo.Either
import vo.Failure

class DeleteOrderUseCase (private val repo: AppRepository): UseCase<Int, Any>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Any>) -> Unit) {
        param?.let {  }
    }

}