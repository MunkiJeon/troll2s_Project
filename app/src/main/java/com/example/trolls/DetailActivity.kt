package com.example.trolls

import Comment
import Like
import Post
import User
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {
    // sample data 선언
    val posts = mutableListOf<Post>()
    val likes = mutableListOf<Like>()
    val comments = mutableListOf<Comment>()
    var post1: Post
    var post2: Post
    val user1: User
    val user2: User
    val user3: User
    val loginedUser: User
    lateinit var parentLayout: LinearLayout
    val commentLayouts = mutableListOf<View>()

    init {
        user1 = User("test999", "test123", "test", "test1", "",  posts, likes, R.drawable.sample_img1)
        user2 = User("test999", "test123", "test", "test2", "",  posts, likes, R.drawable.sample_img2)
        user3 = User("test999", "test123", "test", "test3", "",  posts, likes,  R.drawable.sample_img2)
        loginedUser = user3

        post1 = Post(1, R.drawable.sample_img1, "Title 1", "Content 1", user1, comments = comments, likes = likes)
        post2 = Post(2, R.drawable.sample_img1, "Title 2", "Content 2", user1, comments = comments, likes = likes)
        posts.add(post1)
        posts.add(post2)

        val like1 = Like(user1)
        val like2 = Like(user2)
        likes.add(like1)
        likes.add(like2)

        val comment1 = Comment("나는 김밥 나는 김밥", user1)
        val comment2 = Comment("댓글 1", user1)
        val comment3 = Comment("댓글 2", user1)
        val comment4 = Comment("댓글 3", user1)
        val comment5 = Comment("댓글 4", user1)
        val comment6 = Comment("댓글 5", user1)
        val comment7 = Comment("댓글 6", user1)
        val comment8 = Comment("댓글 7", user1)
        val comment9 = Comment("댓글 8", user1)
        val comment10 = Comment("댓글 9", user1)
        val comment11 = Comment("댓글 10", user1)
        val comment12 = Comment("댓글 11", user1)
        comments.apply {
            add(comment1)
            add(comment2)
            add(comment3)
            add(comment4)
            add(comment5)
            add(comment6)
            add(comment7)
            add(comment8)
            add(comment9)
            add(comment10)
            add(comment11)
            add(comment12)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 필요한 변수들 선언
        // 스크롤뷰
        val detailSv = findViewById<NestedScrollView>(R.id.detail_sv)

        // 댓글 모두 보기 텍스트뷰
        val detailViewLine = findViewById<View>(R.id.detail_view_line)

        // user의 프로필 이미지, 닉네임
        val detailIvProfileImg = findViewById<ImageView>(R.id.detail_iv_profile_img)
        val detailProfileNickname = findViewById<TextView>(R.id.detail_profile_nickname)

        // post의 이미지, 내용
        val detailIvPostImg = findViewById<ImageView>(R.id.detail_iv_post_img)
        val detailTvContent = findViewById<TextView>(R.id.detail_tv_content)

        // 좋아요, 댓글 버튼 -> 댓글 버튼 누르면 바텀 시트가 올라옴
        val detailBtnLike = findViewById<ImageButton>(R.id.detail_btn_like)
        val detailBtnComment = findViewById<ImageButton>(R.id.detail_btn_comment)

        // 누가 좋아요를 눌렀는지 볼 수 있는 목록 -> 누르면 바텀 시트가 올라옴
        val detailLikers = findViewById<TextView>(R.id.detail_likers)

        // 댓글 입력창
        val detailIvCommentProfileImg = findViewById<ImageView>(R.id.detail_iv_comment_profile_img)
        val detailEtCommentBox = findViewById<EditText>(R.id.detail_et_comment_box)
        val detailTvCommentSubmitBtn = findViewById<TextView>(R.id.detail_tv_comment_submit_btn)

        // 부모 레이아웃
        parentLayout = findViewById<LinearLayout>(R.id.detail_lo_comment_list)

        // -----------------------------------------------------------------------------------------

        // 메인 화면에서 받아온 데이터 셋팅
        Glide.with(detailIvProfileImg)
            .load(post1.writerUser.profileImageResource)
            .apply(RequestOptions.circleCropTransform())
            .into(detailIvProfileImg)

        detailProfileNickname.text = post1.writerUser.nickname
        detailIvPostImg.setImageResource(post1.imageResource)
        detailTvContent.text = post1.content
        detailIvCommentProfileImg.setImageResource(post1.imageResource)
        Glide.with(detailIvCommentProfileImg)
            .load(post1.writerUser.profileImageResource)
            .apply(RequestOptions.circleCropTransform())
            .into(detailIvCommentProfileImg)

        // -----------------------------------------------------------------------------------------

        // 댓글 리스트 안에 있는 요소들을 동적으로 추가하는 로직
        post1.comments.forEach {
            addCommentLayout(it)
        }

        // -----------------------------------------------------------------------------------------

        // 버튼들 클릭 리스너
        detailBtnComment.setOnClickListener {
            detailSv.smoothScrollTo(0, detailViewLine.top)
        }

        // 버튼이 눌려졌는지 아닌지 체크하는 변수 (초기값 : false)
        var checkedBtn = false
        // 이미지가 바뀌었는지 체크하는 변수 (초기값 : false)
        var isImageChanged = false

        // 좋아요 버튼 클릭 동작 로직
        detailBtnLike.setOnClickListener {

            // 버튼을 누르면 꽉 찬 하트로 바꾸고, 받아온 post 객체의 좋아요 리스트에 로그인된 유저를 추가함
            if (!checkedBtn && !isImageChanged) {
                detailBtnLike.setImageResource(R.drawable.ic_like_full)
                checkedBtn = true
                isImageChanged = true
                post1.likes.add(Like(loginedUser))

                // 버튼을 한 번 더 누르면 빈 하트로 바꾸고, 좋아요 리스트에 로그인된 유저를 삭제
            } else if (checkedBtn && isImageChanged) {
                detailBtnLike.setImageResource(R.drawable.ic_like)
                checkedBtn = false
                isImageChanged = false
                post1.likes.remove(Like(loginedUser))
            }
        }

        detailTvCommentSubmitBtn.setOnClickListener {
            if (detailEtCommentBox.text.isNotEmpty()) {
                val detailLoCommentList = findViewById<LinearLayout>(R.id.detail_lo_comment_list)
                addCommentLayout(Comment(detailEtCommentBox.text.toString(), loginedUser))
                detailEtCommentBox.text = null

            } else {
                Toast.makeText(this, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun addCommentLayout(comment: Comment) {
        // 미리 만들어둔 댓글 레이아웃
        val commentLayout =
            layoutInflater.inflate(R.layout.layout_comment_list, null)

        // 댓글을 쓴 유저 이미지
        val detailCommentListProfileImg =
            commentLayout.findViewById<ImageView>(R.id.detail_comment_list_iv__profile_img)

        // 유저 이미지 원형으로 만들기
        Glide.with(detailCommentListProfileImg)
            .load(comment.writeUser.profileImageResource)
            .apply(RequestOptions.circleCropTransform())
            .into(detailCommentListProfileImg)

        // 댓글을 쓴 유저 닉네임
        val detailCommentListTvNickname =
            commentLayout.findViewById<TextView>(R.id.detail_comment_list_tv_nickname).apply {
                text = comment.writeUser.nickname
            }

        // 댓글 내용
        val detailCommentListTvComment =
            commentLayout.findViewById<TextView>(R.id.detail_comment_list_tv_comment).apply {
                text = comment.content
            }

        // 레이아웃 속성 설정
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            // 왼쪽, 위, 오른쪽, 아래 마진 설정
            setMargins(0, 0, 0, 30)
        }
        commentLayout.layoutParams = layoutParams

        // 부모 레이아웃에 댓글 레이아웃 추가
        parentLayout.addView(commentLayout)

        commentLayout.findViewById<ImageButton>(R.id.detail_comment_list_ib_delete).setOnClickListener {
            parentLayout.removeViewInLayout(commentLayout)
            post1.comments.remove(comment)
        }
    }
}