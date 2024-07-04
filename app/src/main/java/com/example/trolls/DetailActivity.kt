package com.example.trolls

import Comment
import Like
import Post
import User
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    //  sample data
    val user = User("test999", "test123", "test", "MG", mutableListOf(), mutableListOf())
    val comment = Comment("나는 김밥 나는 김밥", user)
    val like = mutableListOf(
        Like(user)
    )
    val post = Post(
        1,
        1,
        "",
        "나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다 나는 말하는 감자다",
        user,
        mutableListOf(comment),
        like
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // user의 프로필 이미지, 닉네임
        val detailIvProfileImg = findViewById<ImageView>(R.id.detail_iv_profile_img)
        val detailProfileNickname = findViewById<TextView>(R.id.detail_profile_nickname)

        // post의 이미지, 내용
        val detailIvPostImg = findViewById<ImageView>(R.id.detail_iv_post_img)
        val detailTvContent = findViewById<TextView>(R.id.detail_tv_content)

        // 좋아요, 댓글 버튼 -> 댓글 버튼 누르면 바텀 시트가 올라옴
        val detailBtnLike = findViewById<ImageButton>(R.id.detail_btn_like)
        val detailBtnComment = findViewById<ImageButton>(R.id.detail_btn_like)
        // 모든 댓글을 볼 수 있는 텍스트뷰 (버튼)
        val DetailTvShowAllComment = findViewById<TextView>(R.id.detail_tv_show_all_comment)

        // 누가 좋아요를 눌렀는지 볼 수 있는 목록 -> 누르면 바텀 시트가 올라옴
        val detailLikers = findViewById<TextView>(R.id.detail_likers)

//        setDataToView(detailIvProfileImg, post.writerUser)
        setDataToView(detailProfileNickname, post.writerUser.nickname)
        setDataToView(detailIvPostImg, post.imageResource)
        setDataToView(detailTvContent, post.content)
    }

    /**
     * 메인 화면에서 받아온 데이터들을 위젯에 적용하는 함수 (TextView와 ImageView만 쓰기 때문에 when문의 조건을 두 개만 설정함)
     *
     * @param view : 세팅할 위젯
     * @param data : 메인 화면에서 받아온 데이터 중 셋팅할 위젯에 해당하는 데이터
     * @author 신지원
     */
    fun <T> setDataToView(view: View, data: T) {
        when {
            view is TextView && data is String -> view.text = data
            view is ImageView && data is Int -> data
        }
    }
}