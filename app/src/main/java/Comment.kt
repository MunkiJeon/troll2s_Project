import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(val content: String,
                   val writeUser: User) : Parcelable
