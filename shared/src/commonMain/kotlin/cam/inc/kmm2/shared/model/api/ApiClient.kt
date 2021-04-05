package cam.inc.kmm2.shared.model.api

import cam.inc.kmm2.shared.extension.mergeReduce
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class ApiClient(val baseURL: String) {
    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
        install(Logging)
    }

    suspend inline fun <reified T> perform(request: Request<T>, headers: Map<String, String> = mapOf()): T {
        try {
            return client.request<T> {
                url(baseURL + request.path)
                method = request.method.toKtor()
                val headers = request.headers
                    .mergeReduce(headers)
                if (headers.isNotEmpty()) {
                    headers {
                        headers
                            .forEach {
                                append(it.key, it.value)
                            }
                    }
                }
            }

        } catch (e: Exception) {
            print(e)
            throw e
        }
    }
}

