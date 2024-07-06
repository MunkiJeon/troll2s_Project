package com.example.trolls

import Comment
import Like
import Post
import User
import android.content.Context
import android.content.res.ColorStateList
import android.hardware.input.InputManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

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
    val user4: User
    val user5: User
    val user6: User
    val user7: User
    val user8: User
    val loginedUser: User
    lateinit var parentLayout: LinearLayout
    lateinit var detailEtCommentBox: EditText
    lateinit var detailTvCommentSubmitBtn: TextView
    lateinit var detailSv: NestedScrollView
    lateinit var imm: InputMethodManager
    val commentLayouts = mutableListOf<View>()

    init {
        user1 =
            User("test999", "test123", "test", "test1", "", posts, likes, R.drawable.sample_img1)
        user2 =
            User("test999", "test123", "test", "test2", "", posts, likes, R.drawable.sample_img2)
        user3 =
            User("test999", "test123", "test", "test3", "", posts, likes, R.drawable.sample_img2)
        user4 =
            User("test999", "test123", "test", "test4", "", posts, likes, R.drawable.sample_img2)
        user5 =
            User("test999", "test123", "test", "test5", "", posts, likes, R.drawable.sample_img2)
        user6 =
            User("test999", "test123", "test", "test6", "", posts, likes, R.drawable.sample_img2)
        user7 =
            User("test999", "test123", "test", "test7", "", posts, likes, R.drawable.sample_img2)
        user8 =
            User("test999", "test123", "test", "test8", "", posts, likes, R.drawable.sample_img2)
        loginedUser = user8

        post1 = Post(
            1,
            R.drawable.sample_img1,
            "Title 1",
            "Content 1",
            user1,
            comments = comments,
            likes = likes
        )
        post2 = Post(
            2,
            R.drawable.sample_img1,
            "Title 2",
            "Content 2",
            user1,
            comments = comments,
            likes = likes
        )
        posts.add(post1)
        posts.add(post2)

        val like1 = Like(user1)
        val like2 = Like(user2)
        val like3 = Like(user3)
        val like4 = Like(user4)
        val like5 = Like(user5)
        val like6 = Like(user6)
        val like7 = Like(user7)
        likes.add(like1)
        likes.add(like2)
        likes.add(like3)
        likes.add(like4)
        likes.add(like5)
        likes.add(like6)
        likes.add(like7)

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
        detailSv = findViewById<NestedScrollView>(R.id.detail_sv)

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
        detailEtCommentBox = findViewById<EditText>(R.id.detail_et_comment_box)
        detailTvCommentSubmitBtn = findViewById<TextView>(R.id.detail_tv_comment_submit_btn)

        // 부모 레이아웃
        parentLayout = findViewById<LinearLayout>(R.id.detail_lo_comment_list)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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
        showLikers(detailLikers)

        // -----------------------------------------------------------------------------------------

        // 댓글 리스트 안에 있는 요소들을 동적으로 추가하는 로직
        post1.comments.forEach {
            addCommentLayout(it)
        }

        // -----------------------------------------------------------------------------------------


        // 버튼들 클릭 리스너

        detailBtnComment.setOnClickListener{
            detailSv.smoothScrollTo(0, detailViewLine.top)
        }

        // 댓글 게시 버튼 눌렀을 때 댓글 추가하는 로직
        imm.showSoftInput(detailEtCommentBox, InputMethodManager.SHOW_IMPLICIT)
        changedBtnColor(detailEtCommentBox)
        setSubmitButtonListenerForNewComment()

        var isClick = false
        // 좋아요 버튼 클릭 동작 로직
        detailBtnLike.setOnClickListener {
            if (!isClick) {
                detailBtnLike.setImageResource(R.drawable.ic_like_full)
                post1.likes.add(Like(loginedUser))
                showLikers(detailLikers)
                isClick = true
            } else {
                detailBtnLike.setImageResource(R.drawable.ic_like)
                post1.likes.removeLast()
                showLikers(detailLikers)
                isClick = false
            }
        }

        detailLikers.setOnClickListener {
            // BottomSheetDialog 생성 및 설정
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_like)

            // BottomSheetDialog에 포함된 레이아웃의 뷰들 참조
            val bottomSheetLayout =
                bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_like_lo_list)

            // 좋아요 목록이 비어있는 경우 처리
            if (likes.isEmpty()) {
                Toast.makeText(this, "좋아요가 없습니다", Toast.LENGTH_SHORT).show()
            } else {
                // 좋아요 목록이 비어있지 않은 경우, 목록 순회하며 레이아웃에 추가
                likes.forEach { like ->
                    val likeLayout =
                        layoutInflater.inflate(R.layout.layout_bottom_sheet_like_list, null)

                    // 프로필 이미지 설정
                    val profileImage =
                        likeLayout.findViewById<ImageView>(R.id.layout_bottom_sheet_like_list_iv_profile_img)
                    Glide.with(profileImage)
                        .load(like.checkedUser.profileImageResource)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage)

                    // 프로필 이름 설정
                    val profileName =
                        likeLayout.findViewById<TextView>(R.id.detail_comment_list_tv_nickname)
                    profileName.text = like.checkedUser.nickname

                    // 레이아웃 파라미터 설정
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, 30) // 왼쪽, 위, 오른쪽, 아래 마진 설정
                    }
                    likeLayout.layoutParams = layoutParams

                    // BottomSheetDialog의 LinearLayout에 추가
                    bottomSheetLayout?.addView(likeLayout)
                }
            }

            // BottomSheetDialog 표시
            bottomSheetDialog.show()
        }

    }

    fun addCommentLayout(comment: Comment) {
        // 미리 만들어둔 댓글 레이아웃
        val commentLayout =
            layoutInflater.inflate(R.layout.layout_comment_list, null)

        // 댓글을 쓴 유저 이미지
        val detailCommentListProfileImg =
            commentLayout.findViewById<ImageView>(R.id.detail_comment_list_iv__profile_img)

        commentLayouts.add(commentLayout)

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
                setOnClickListener {
                    // EditText에 포커스를 설정
                    if (detailEtCommentBox.requestFocus()) {
                        // 키보드를 보이게 하는 부분
                        imm.showSoftInput(detailEtCommentBox, InputMethodManager.SHOW_IMPLICIT)

                        detailTvCommentSubmitBtn.text = "수정"
                        changedBtnColor(detailEtCommentBox)
                        setSubmitButtonListenerForEditComment(comment, this)
                    }
                }
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

        // 댓글 삭제 버튼
        commentLayout.findViewById<ImageButton>(R.id.detail_comment_list_ib_delete)
            .setOnClickListener {
                if (commentLayouts.isNotEmpty()) {
                    parentLayout.removeViewAt(commentLayouts.indexOf(commentLayout))
                    commentLayouts.remove(commentLayout)
                }
                post1.comments.remove(comment)
            }
    }

    fun showLikers(view: TextView) {
        if (post1.likes.isNotEmpty()) {
            when {
                post1.likes.size == 1 -> {
                    view.visibility = View.VISIBLE
                    view.text = "${post1.likes.get(0).checkedUser.nickname}님이 좋아합니다"
                }

                else -> {
                    view.visibility = View.VISIBLE
                    view.text = "${post1.likes.get(0).checkedUser.nickname}님 외 여러명이 좋아합니다"
                }
            }
        } else {
            view.visibility = View.GONE
        }
    }

    fun changedBtnColor(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    detailTvCommentSubmitBtn.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.divider
                        )
                    )
                } else {
                    detailTvCommentSubmitBtn.setTextColor(
                        ContextCompat.getColor(
                            this@DetailActivity,
                            R.color.button
                        )
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    // 댓글 수정 및 등록 로직
    fun setSubmitButtonListenerForNewComment() {
        detailTvCommentSubmitBtn.setOnClickListener {
            if (detailEtCommentBox.text.isNotEmpty()) {
                addCommentLayout(Comment(detailEtCommentBox.text.toString(), loginedUser))
                detailEtCommentBox.text = null
                detailSv.smoothScrollTo(0, detailSv.bottom)
                imm.hideSoftInputFromWindow(detailEtCommentBox.windowToken, 0)
                detailEtCommentBox.clearFocus()
                changedBtnColor(detailEtCommentBox)
                setSubmitButtonListenerForNewComment()
            } else {
                Toast.makeText(this, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setSubmitButtonListenerForEditComment(
        comment: Comment,
        detailCommentListTvComment: TextView,
    ) {
        detailTvCommentSubmitBtn.setOnClickListener {
            if (detailEtCommentBox.text.isNotEmpty()) {
                detailCommentListTvComment.text = detailEtCommentBox.text
                comment.content = detailEtCommentBox.text.toString()
                Toast.makeText(this, "댓글이 수정되었습니다", Toast.LENGTH_SHORT).show()
                detailEtCommentBox.text = null
                detailEtCommentBox.clearFocus()
                imm.hideSoftInputFromWindow(detailEtCommentBox.windowToken, 0)
                detailTvCommentSubmitBtn.text = "게시"
                setSubmitButtonListenerForNewComment()
            } else {
                Toast.makeText(this, "수정할 댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}