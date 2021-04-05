import SwiftUI
import shared
import Combine

func greet() -> String {
    return Greeting().greeting()
}

enum AppError: LocalizedError {
    case client(ClientError)
    case server

    enum ClientError: LocalizedError {
        case unexpected
        case throwable(KotlinThrowable)
    }
}

struct Post {
    var userId: Int
    var id: Int
    var title: String
    var body: String

    init(_ kotlinPost: shared.Post) {
        self.userId = Int(kotlinPost.userId)
        self.id = Int(kotlinPost.id)
        self.title = kotlinPost.title
        self.body = kotlinPost.body
    }
}

func futureOf<O>(wrapper: KoruSuspendWrapper<O>) -> AnyPublisher<O, AppError> {
    var job: Kotlinx_coroutines_coreJob?
    return Deferred {
        Future<O, AppError> { promise in
            job = wrapper.subscribe(
                onSuccess: { output in
                    if let output = output {
                        promise(.success(output))
                    } else {
                        promise(.failure(AppError.client(.unexpected)))
                    }
                },
                onThrow: { throwable in
                    promise(.failure(AppError.client(.throwable(throwable))))
                }
            )
        }
        .handleEvents(receiveCancel: { job?.cancel(cause: nil) })
    }.eraseToAnyPublisher()
}

extension PostListFetchIosUseCase {
    func invoke() -> AnyPublisher<PostList, AppError> {
        futureOf(wrapper: invoke())
    }

    func callAsFunction() -> AnyPublisher<PostList, AppError> {
        invoke()
    }
}

class TestViewModel: ObservableObject {
    @Published var posts: [Post] = []

    var cancellables: Set<AnyCancellable> = .init()

    let postUseCase: PostListFetchIosUseCase = PostListFetchIosUseCaseImpl(
        wrapped: PostListFetchUseCaseImpl(
            postRepository: PostRepositoryImpl(
                client: ApiClient(
                    baseURL: "https://jsonplaceholder.typicode.com"
                )
            )
        )
    )

    func load() {
        postUseCase()
            .sink { _ in

            } receiveValue: { [weak self] (list) in
                self?.posts = list.list.map(Post.init)
            }
            .store(in: &cancellables)
    }
}

struct ContentView: View {

    @ObservedObject var viewModel: TestViewModel = .init()

    var body: some View {
        Button(greet()) {
            viewModel.load()
        }
        List(viewModel.posts, id: \.id) { post in
            Text(post.title)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
