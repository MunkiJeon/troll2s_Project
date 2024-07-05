import java.time.LocalDateTime

data class Like(val checkedUser: User,
                val checkedDate: LocalDateTime = LocalDateTime.now()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true // 같은 참조인 경우 true 반환
        if (javaClass != other?.javaClass) return false // 클래스가 다르면 false 반환

        // 필드를 비교하여 동등성 판단
        other as Like
        return checkedUser == other.checkedUser
    }

    override fun hashCode(): Int {
        return checkedUser.hashCode()
    }
}
