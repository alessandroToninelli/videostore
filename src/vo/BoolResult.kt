package vo

data class BoolResult(
    val status: Status,
    val failure: Failure ? = null
)

