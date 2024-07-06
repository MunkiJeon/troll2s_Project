import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    var content: String,
    val writeUser: User) : Parcelable
