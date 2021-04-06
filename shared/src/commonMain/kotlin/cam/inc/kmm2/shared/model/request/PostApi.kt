package cam.inc.kmm2.shared.model.request

import cam.inc.kmm2.shared.model.api.HTTPMethod
import cam.inc.kmm2.shared.model.api.Request
import cam.inc.kmm2.shared.model.entity.Post

object PostApi {
    fun posts(): Request<List<Post>> {
        val path = "/posts"
        val method = HTTPMethod.Get
        return Request(path, method)
    }
}