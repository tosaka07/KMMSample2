package cam.inc.kmm2.shared

expect class Platform() {
    val platform: String
}

class ArrayTest {
    val list: List<String> = listOf()
    var array: Array<String> = arrayOf()
    var arrayList: ArrayList<String> = arrayListOf()
    var mutableList: MutableList<String> = mutableListOf()
}
