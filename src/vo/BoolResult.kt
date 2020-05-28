package vo

data class BoolResult(
    val status: Status,
    val error: ErrorResponse? = null
)


fun Boolean.toBoolResult(error: ErrorResponse? = null) = when (this) {
    true -> BoolResult(Status.SUCCESS)
    false -> BoolResult(Status.ERROR, error)
}
