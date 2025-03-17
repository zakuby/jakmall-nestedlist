package jakmall.nestedlist.data.remote.model

sealed class NetworkResponse<out T> {
    data class NetworkSuccess<T>(val data: T) : NetworkResponse<T>()
    data object NetworkError : NetworkResponse<Nothing>()
}