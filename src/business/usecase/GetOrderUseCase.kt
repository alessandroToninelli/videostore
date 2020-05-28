package business.usecase

import data.repository.AppRepository
import model.Order
import vo.Either
import vo.Failure

class GetOrderUseCase (private val repo: AppRepository): UseCase<Int, Order?>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Order?>) -> Unit) {
        param?.let { onResult(repo.getOrderById(it)) }
    }

}