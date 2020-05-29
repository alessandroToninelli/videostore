package business.usecase

import data.repository.AppRepository
import vo.*

class InsertOrderUseCase(private val repository: AppRepository): UseCase<InsertOrderUseCase.Param, Int>(){

    data class Param(val filmId: Int, val userId: Int)

    override suspend fun exec(param: Param?, onResult: (Either<Failure, Int>) -> Unit) {
        param?.let { onResult(repository.insertOrder(it.userId, it.filmId)) }
    }
}