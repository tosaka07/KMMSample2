package cam.inc.kmm2.shared.model.repository

import cam.inc.kmm2.shared.model.api.ApiClient
import cam.inc.kmm2.shared.model.entity.Post
import cam.inc.kmm2.shared.model.request.PostApi

interface PostRepository {
    suspend fun fetch(): List<Post>
}

class PostRepositoryImpl(private val client: ApiClient): PostRepository {
    override suspend fun fetch(): List<Post> {
        val request = PostApi.posts()
        return client.perform(request)
    }
}