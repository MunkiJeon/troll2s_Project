import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Like(val checkedUser: User) : Parcelable