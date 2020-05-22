package business.usecase
import data.repository.AppRepository
import model.Film
import vo.Either
import vo.Failure

class GetFilmUseCase (private val appRepository: AppRepository): UseCase<Int, Film?>(){
    override suspend fun exec(param: Int?, onResult: (Either<Failure, Film?>) -> Unit) {
        param?.let { onResult(appRepository.getFilmById(it)) }
    }

}