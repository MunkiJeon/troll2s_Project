data class User(
    var id: String,
    var password: String,
    var name: String,
    var nickname: String,
    var intro :String,
    val myPost: MutableList<Post> = mutableListOf(),
    val myLikes: MutableList<Like> = mutableListOf(),
    val profileImageResource: Int
)
