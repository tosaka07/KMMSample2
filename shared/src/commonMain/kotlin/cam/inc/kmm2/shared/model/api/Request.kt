package cam.inc.kmm2.shared.model.api

data class Request<T>(val path: String, val method: HTTPMethod, val headers: Map<String, String> = mapOf()) {}