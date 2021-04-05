package cam.inc.kmm2.shared.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class Post(val userId: Int, val id: Int, val title: String, val body: String) {

}