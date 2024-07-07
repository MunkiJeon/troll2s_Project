import android.graphics.drawable.Icon
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Youtuber(
    override val id: String = "empty id",
    override var password: String = "empty password",
    override var name: String = "empty name",
    override var nickname: String = "empty nickname",
    override var intro: String = "",
    override val myPosts: MutableList<Post> =  mutableListOf(),
    override val myLikes : MutableList<Like> = mutableListOf(),
    override var profileImageResource: Int = 0,
    var profileIcon: Int = -1) : User(id, password, name, nickname, intro = "", myPosts, myLikes, profileImageResource), Parcelable