import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Like(val checkedUser: User) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Like) return false
        return checkedUser.id == other.checkedUser.id
    }

    override fun hashCode(): Int {
        return checkedUser.id.hashCode()
    }
}