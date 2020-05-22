package vo

sealed class Resource<out T>(
    val status: Status,
    open val data: T? = null,
    open val exception: Exception? = null
) {

    data class Success<out T>(override val data: T? = null) : Resource<T>(Status.SUCCESS, data)
    data class Loading<out T>(override val data: T? = null) : Resource<T>(Status.LOADING, data)
    data class Error<out T>(override val exception: Failure, override val data: T? = null) :
        Resource<T>(Status.ERROR, data, exception)

}

inline fun <T> Resource<T>.case(
    loading: (r: Resource.Loading<T>) -> Unit = {},
    success: (r: Resource.Success<T>) -> Unit = {},
    error: (r: Resource.Error<T>) -> Unit = {}
) {
    when (this.status) {
        Status.SUCCESS -> success(this as Resource.Success<T>)
        Status.ERROR -> error(this as Resource.Error<T>)
        Status.LOADING -> loading(this as Resource.Loading<T>)
    }
}

