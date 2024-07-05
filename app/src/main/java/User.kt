import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class User(
    open val id: String = "empty user",
    open var password: String = "empty password",
    open var name: String = "empty name",
    open var nickname: String = "empty nickname",
    open var intro :String = "",
    open val myPosts: MutableList<Post> = mutableListOf(),
    open val myLikes: MutableList<Like> = mutableListOf(),
    open var profileImageResource: Int = 1
) : Parcelable
