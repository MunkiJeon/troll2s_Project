import java.time.LocalDateTime

data class Like(val checkedUser: User,
                val checkedDate: LocalDateTime = LocalDateTime.now())
