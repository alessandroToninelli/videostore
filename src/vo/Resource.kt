package vo

sealed class Resource<out T>(
    val status: Status,
    open val data: T? = null,
    open val failure: Exception? = null
) {

    data class Success<out T>(override val data: T? = null) : Resource<T>(Status.SUCCESS, data)
    data class Error<out T>(override val failure: Failure, override val data: T? = null) :
        Resource<T>(Status.ERROR, data, failure)

}

inline fun <T> Resource<T>.case(
    success: (r: Resource.Success<T>) -> Unit = {},
    error: (r: Resource.Error<T>) -> Unit = {}
) {
    when (this.status) {
        Status.SUCCESS -> success(this as Resource.Success<T>)
        Status.ERROR -> error(this as Resource.Error<T>)
    }
}

