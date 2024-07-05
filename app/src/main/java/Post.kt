import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(var id: Int,
           val imageResource: Int,
           val title: String,
           val content: String,
           val writerUser: User,
           val youtuber: Youtuber = Youtuber("panni", "1234", "빠니보틀", "panni",  profileIcon = 1),
           val comments: MutableList<Comment> = mutableListOf(),
           val likes: MutableList<Like> = mutableListOf()) : Parcelable