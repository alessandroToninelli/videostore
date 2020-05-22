package business.usecase

import data.repository.AppRepository
import model.Order
import vo.Either
import vo.Failure

class GetFilmOrdersUseCase(private val repo: AppRepository): UseCase<Int, List<Order>>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, List<Order>>) -> Unit) {
        param?.let { onResult(repo.getOrderByFilm(it)) }
    }

}