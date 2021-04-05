package cam.inc.kmm2.shared.model.api

sealed class ApiException: Exception() {
    data class Response(val statusCode: Int): ApiException()
    object Decoding: ApiException()
    object Network: ApiException()
}