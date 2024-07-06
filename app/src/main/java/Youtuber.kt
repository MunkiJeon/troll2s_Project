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
    override val myPosts: MutableList<Post> =  mutableListOf(),
    override val myLikes : MutableList<Like> = mutableListOf(),
    var profileIcon: Int = -1) : User(id, password, name, nickname, "", myPosts, myLikes, 0), Parcelable