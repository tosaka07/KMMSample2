package cam.inc.kmm2.shared.model.useCase

import cam.inc.kmm2.shared.model.MainScopeProvider
import cam.inc.kmm2.shared.model.entity.PostList
import cam.inc.kmm2.shared.model.repository.PostRepository
import com.futuremind.koru.ToNativeClass
import com.futuremind.koru.ToNativeInterface

@ToNativeInterface(name = "PostListFetchIosUseCase")
interface PostListFetchUseCase {
    suspend operator fun invoke(): PostList
}

@ToNativeClass(name = "PostListFetchIosUseCaseImpl", launchOnScope = MainScopeProvider::class)
class PostListFetchUseCaseImpl(
    private val postRepository: PostRepository
): PostListFetchUseCase {
    override suspend fun invoke(): PostList {
        return PostList(postRepository.fetch())
    }
}