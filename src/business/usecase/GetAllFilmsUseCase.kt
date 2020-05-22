package business.usecase

import data.repository.AppRepository
import model.Film
import vo.Either
import vo.Failure

class GetAllFilmsUseCase (private val appRepository: AppRepository): UseCase<Nothing, List<Film>>(){
    override suspend fun exec(param: Nothing?, onResult: (Either<Failure, List<Film>>) -> Unit) {
        onResult(appRepository.getAllFilms())
    }

}