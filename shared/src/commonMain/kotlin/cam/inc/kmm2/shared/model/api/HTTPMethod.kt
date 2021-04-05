package cam.inc.kmm2.shared.model.api

import io.ktor.http.HttpMethod

enum class HTTPMethod {
    Get, Post, Put, Patch, Delete, Head, Options;

    fun toKtor(): HttpMethod {
       return when (this) {
            Get -> HttpMethod.Get
            Post -> HttpMethod.Post
            Put -> HttpMethod.Put
            Patch -> HttpMethod.Patch
            Delete -> HttpMethod.Delete
            Head -> HttpMethod.Head
            Options -> HttpMethod.Options
        }
    }
}