import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Like(val checkedUser: User,
                val checkedDate: LocalDateTime = LocalDateTime.now()) : Parcelable