class Post(val id: Int,
            val imageResource: Int,
            val title: String,
            val content: String,
            val writerUser: User,
            val comments: MutableList<Comment> = mutableListOf(),
            val likes: MutableList<Like> = mutableListOf()
) {
    private var postNumber = 1
}