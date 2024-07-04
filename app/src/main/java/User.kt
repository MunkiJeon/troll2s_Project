data class User(val id: String,
                var password: String,
                val name: String,
                var nickname: String,
                val myPost: MutableList<Post> = mutableListOf(),
                val myLikes: MutableList<Like> = mutableListOf()
)
