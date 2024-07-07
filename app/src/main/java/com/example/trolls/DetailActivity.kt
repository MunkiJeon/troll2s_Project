package com.example.trolls

import Comment
import Like
import Post
import User
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
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
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailActivity : AppCompatActivity() {
    // 임시로 post1로 설정
    // 재선님 페이지와 연결하면 재선님 페이지에서 해당 포스트와 로그인한 유저를 받아와서 post1 부분을 받아온 데이터로 바꿔야함

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

    // 지우면 안되는 변수들
    lateinit var parentLayout: LinearLayout
    lateinit var detailEtCommentBox: EditText
    lateinit var detailTvCommentSubmitBtn: TextView
    lateinit var detailBtnLike: ImageButton
    lateinit var detailSv: NestedScrollView
    lateinit var imm: InputMethodManager
    val commentLayouts = mutableListOf<View>()
    var isClick = false

    init {
        user1 = User("test999", "test123", "test", "test1", "", posts, likes, R.drawable.sample_img1)
        user2 = User("test999", "test123", "test", "test2", "", posts, likes, R.drawable.sample_img1)
        user3 = User("test999", "test123", "test", "test3", "", posts, likes, R.drawable.sample_img1)
        user4 = User("test999", "test123", "test", "test4", "", posts, likes, R.drawable.sample_img1)
        user5 = User("test999", "test123", "test", "test5", "", posts, likes, R.drawable.sample_img1)
        user6 = User("test999", "test123", "test", "test6", "", posts, likes, R.drawable.sample_img1)
        user7 = User("test999", "test123", "test", "test7", "", posts, likes, R.drawable.sample_img1)
        user8 = User("test999", "test123", "test", "test8", "", posts, likes, R.drawable.sample_img2)
        loginedUser = user7

        post1 = Post(R.drawable.sample_img1, "Title 1", "Content 1", user1, comments = comments, likes = likes)
        post2 = Post(R.drawable.sample_img1, "Title 2", "Content 2", user1, comments = comments, likes = likes)
        posts.add(post1)
        posts.add(post2)

        val like1 = Like(user1)
        val like2 = Like(user2)
        val like3 = Like(user3)
        val like4 = Like(user4)
        val like5 = Like(user5)
        val like6 = Like(user6)
        val like7 = Like(user7)
        val like8 = Like(user8)
        likes.add(like1)
        likes.add(like2)
        likes.add(like3)
        likes.add(like4)
        likes.add(like5)
        likes.add(like6)
        likes.add(like8)

        val comment1 = Comment("나는 김밥 나는 김밥", user1)
        val comment2 = Comment("댓글 1", user1)
        val comment3 = Comment("댓글 2", user2)
        val comment4 = Comment("댓글 3", user3)
        val comment5 = Comment("댓글 4", user4)
        val comment6 = Comment("댓글 5", user5)
        val comment7 = Comment("댓글 6", user6)
        val comment8 = Comment("댓글 7", user7)
        val comment9 = Comment("댓글 8", user5)
        val comment10 = Comment("댓글 9", user7)
        val comment11 = Comment("댓글 10", user2)
        val comment12 = Comment("댓글 11", user8)
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

        // -----------------------------------------------------------------------------------------------------------------------

        // 필요한 변수들 선언

        // 레이아웃
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        detailSv = findViewById(R.id.detail_sv)

        // 댓글을 보여주는 레이아웃
        parentLayout = findViewById(R.id.detail_lo_comment_list)

        // 글을 쓴 사람의 프로필과 닉네임
        val detailIvProfileImg = findViewById<ImageView>(R.id.detail_iv_profile_img)
        val detailProfileNickname = findViewById<TextView>(R.id.detail_profile_nickname)

        // 글의 이미지와 내용
        val detailIvPostImg = findViewById<ImageView>(R.id.detail_iv_post_img)
        val detailTvContent = findViewById<TextView>(R.id.detail_tv_content)

        // 버튼
        detailBtnLike = findViewById<ImageButton>(R.id.detail_btn_like).apply {
            // 버튼 셋팅
            // 로그인한 유저가 이미 좋아요를 눌렀다면 ic_like_full 리소스를 세팅하고, 누르지 않았다면 ic_like_full로 리소스 셋팅
            if (post1.likes.contains(Like(loginedUser))) {
                setImageResource(R.drawable.ic_like_full)

                // 클릭했는지 확인하는 변수
                isClick = true
            } else {
                setImageResource(R.drawable.ic_like_full)
                isClick = false
            }
        }
        val detailBtnComment = findViewById<ImageButton>(R.id.detail_btn_comment)

        // 좋아요 목록
        val detailLikers = findViewById<TextView>(R.id.detail_likers)

        // 댓글을 작성할 수 있는 부분
        val detailLoCommentBox = findViewById<ConstraintLayout>(R.id.detail_lo_comment_box)
        val detailIvCommentProfileImg = findViewById<ImageView>(R.id.detail_iv_comment_profile_img)
        detailEtCommentBox = findViewById<EditText>(R.id.detail_et_comment_box)
        detailTvCommentSubmitBtn = findViewById<TextView>(R.id.detail_tv_comment_submit_btn).apply {
            // 댓글 작성하고 게시하는 버튼의 텍스트를 설정
            text = "게시"
        }

        // 댓글 위에 있는 라인
        val detailViewLine = findViewById<View>(R.id.detail_view_line)

        // 키보드를 제어하기 위한 변수
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        // -----------------------------------------------------------------------------------------------------------------------

        // 메인 화면에서 받아온 데이터 셋팅

        // 글을 쓴 사람의 프로필 셋팅
        // 프로필 이미지 셋팅 / 동그랗게 만듦
        Glide.with(detailIvProfileImg)
            .load(post1.writerUser.profileImageResource)
            .apply(RequestOptions.circleCropTransform())
            .into(detailIvProfileImg)

        // 닉네임 셋팅
        detailProfileNickname.text = post1.writerUser.nickname

        // 글의 이미지와 내용 셋팅
        detailIvPostImg.setImageResource(post1.imageResource)
        detailTvContent.text = post1.content

        // 댓글 작성란 셋팅
        // 로그인한 유저의 프로필 이미지 셋팅
        Glide.with(detailIvCommentProfileImg)
            .load(loginedUser.profileImageResource)
            .apply(RequestOptions.circleCropTransform())
            .into(detailIvCommentProfileImg)

        // 좋아요 목록 부분 텍스트 셋팅
        showLikers(detailLikers)

        // 댓글 리스트 안에 있는 요소들을 동적으로 추가하는 로직
        post1.comments.forEach {
            // 댓글 레이아웃을 추가하는 함수
            addCommentLayout(it)
        }

        // -----------------------------------------------------------------------------------------------------------------------

        // 댓글 작성하는 부분의 레이아웃이 키보드에 가려지는 이슈가 있었음
        // 댓글 작성하는 부분을 키보드 위로 올려주는 코드

        // mainLayout의 인셋 리스너
        // insets는 레이아웃에서 사용되는 공간, 요소 간의 여백이나 패딩을 의미
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->

            // 시스템바의 인셋
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // 키보드의 인셋
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            // 시스템 바 인셋을 mainLayout에 설정
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // etailLoCommentBox 뷰의 레이아웃 파라미터를 가져와서 ConstraintLayout.LayoutParams로 캐스팅
            // 이를 통해 detailLoCommentBox의 레이아웃 속성을 동적으로 조작할 수 있음
            val params = detailLoCommentBox.layoutParams as ConstraintLayout.LayoutParams

            // 10dp를 픽셀 값으로 변환하여 defaultMarginBottom에 저장
            val defaultMarginBottom = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10f,
                resources.displayMetrics
            ).toInt()

            // 키보드가 올라왔을 때 안올라와 있을 때의 마진 설정
            params.bottomMargin = if (imeInsets.bottom > 0) {
                imeInsets.bottom + defaultMarginBottom
            } else {
                defaultMarginBottom
            }
            detailLoCommentBox.layoutParams = params

            // 키보드가 올라왔을 때 패딩 재설정
            // 뷰가 너무 높이 올라가는 것을 방지
            if (imeInsets.bottom > 0) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            }

            // 최종 인셋 반환
            insets
        }

        // -----------------------------------------------------------------------------------------------------------------------

        // 버튼을 클릭했을 때 동작하는 코드

        // 댓글 게시 버튼 눌렀을 때 댓글 추가하는 로직
        // 키보드를 보여줌
        imm.showSoftInput(detailEtCommentBox, InputMethodManager.SHOW_IMPLICIT)

        // detailEtCommentBox에 텍스트가 입력되면 submit 버튼 부분의 색깔이 바뀌는 코드
        changedBtnColor(detailEtCommentBox)

        // 댓글을 작성하고 submit 버튼을 누르면 댓글이 추가되는 함수
        setSubmitButtonListenerForNewComment()

        // 좋아요 버튼 클릭 리스너
        // 좋아요 버튼이 이미 눌렸으면 클릭 취소를 해야하므로 해당 글의 좋아요 목록에서 삭제 후, 이미지 리소스 변경
        // 좋아요 버튼이 안눌려있다면 해당 글의 좋아요 목록에 추가 후, 이미지 리소스 변경
        detailBtnLike.setOnClickListener {
            if (isClick) {
                detailBtnLike.setImageResource(R.drawable.ic_like)
                post1.likes.remove(Like(loginedUser))
                isClick = false
            } else {
                detailBtnLike.setImageResource(R.drawable.ic_like_full)
                post1.likes.add(Like(loginedUser))
                isClick = true
            }
        }

        // 댓글 버튼 클릭 리스너
        detailBtnComment.setOnClickListener {

            // 댓글 부분으로 스크롤
            detailSv.smoothScrollTo(0, detailViewLine.top)
        }

        // 텍스트를 누르면 좋아요 목록을 바텀 시트로 보여주는 함수
        detailLikers.setOnClickListener {
            // 바텀시트 객체 생성
            val bottomSheetDialog = BottomSheetDialog(this)

            // 바텀 시트의 레이아웃 설정
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_like)

            // 좋아요 목록 레이아웃
            val bottomSheetLayout = bottomSheetDialog.findViewById<LinearLayout>(R.id.bottom_sheet_like_lo_list)

            // 좋아요 목록이 비어있지 않다면
            if (likes.isNotEmpty()) {

                // 좋아요 목록 순회, 좋아요 목록에 있는 좋아요 객체를 하나씩 꺼내옴
                likes.forEach { like ->

                    // 좋아요 레이아웃 인플레이트
                    val likeLayout =
                        layoutInflater.inflate(R.layout.layout_bottom_sheet_like_list, null)

                    // 좋아요 누른 유저의 프로필 셋팅
                    // 프로필 이미지
                    val profileImage =
                        likeLayout.findViewById<ImageView>(R.id.layout_bottom_sheet_like_list_iv_profile_img)

                    // 이미지 동그랗게 만듦
                    Glide.with(profileImage)
                        .load(like.checkedUser.profileImageResource)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImage)

                    // 닉네임
                    val profileNickName =
                        likeLayout.findViewById<TextView>(R.id.detail_comment_list_tv_nickname).apply {
                            text = like.checkedUser.nickname
                        }

                    // layoutParams 설정
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, 30)
                    }

                    // 좋아요 레이아웃의 layoutParams에 layoutParams 넣음
                    likeLayout.layoutParams = layoutParams

                    // 바텀시트 레이아웃에 좋아요 레이아웃 추가
                    bottomSheetLayout?.addView(likeLayout)
                }
            }

            // 바텀시트 표시
            bottomSheetDialog.show()
        }
    }

    // -----------------------------------------------------------------------------------------------------------------------

    // 직접 작성한 함수 부분

    /**
     * 처음 실행할 때 post에 있는 댓글들을 보여줘야 하므로 댓글 레이아웃을 부모뷰에 추가하는 함수
     *
     * @param comment : 추가할 댓글 객체
     * @author 신지원
     */
    fun addCommentLayout(comment: Comment) {

        // 부모뷰에 추가할 댓글 레이아웃
        val commentLayout = layoutInflater.inflate(R.layout.layout_comment_list, null)

        // commentLayout 리스트에 해당 레이아웃 추가
        commentLayouts.add(commentLayout)

        // 댓글을 쓴 사람의 프로필 셋팅

        // 프로필 이미지
        val detailCommentListProfileImg = commentLayout.findViewById<ImageView>(R.id.detail_comment_list_iv__profile_img)

        // 프로필 이미지를 동그랗게 만듦
        Glide.with(detailCommentListProfileImg)
            .load(comment.writeUser.profileImageResource)
            .apply(RequestOptions.circleCropTransform())
            .into(detailCommentListProfileImg)

        // 프로필 닉네임
        commentLayout.findViewById<TextView>(R.id.detail_comment_list_tv_nickname).apply {
            // 댓글을 작성한 유저의 닉네임으로 셋팅
            text = comment.writeUser.nickname
        }

        // 댓글의 내용 셋팅
        commentLayout.findViewById<TextView>(R.id.detail_comment_list_tv_comment).apply {

            // 추가하려는 댓글의 내용으로 셋팅
            text = comment.content

            // 댓글을 누르면 수정 가능하게 해야하므로 클릭 리스너 달아줌
            setOnClickListener {
                // 수정하려는 댓글의 유저와 로그인한 유저가 같으면
                if (comment.writeUser == loginedUser) {

                    // detailEtCommentBox에 포커스 받을 수 있도록 요청한 뒤 포커스를 받았다면
                    if (detailEtCommentBox.requestFocus()) {

                        // 키보드를 보여줌
                        imm.showSoftInput(detailEtCommentBox, InputMethodManager.SHOW_IMPLICIT)

                        // submit 버튼의 텍스트를 "수정"으로 바꿈
                        detailTvCommentSubmitBtn.text = "수정"

                        // detailEtCommentBox에 텍스트를 작성했다면 submit 버튼의 색깔을 바꾸는 함수
                        changedBtnColor(detailEtCommentBox)

                        // 댓글을 수정하는 함수
                        setSubmitButtonListenerForEditComment(comment, this)
                    }
                } else { // 수정하려는 댓글의 유저와 로그인한 유저가 같지않으면

                    // toast 메세지 띄움
                    Toast.makeText(this@DetailActivity, "내 댓글만 수정할 수 있습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // layoutParams 설정
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 30)
        }

        // 댓글 레이아웃의 layoutParams에 설정한 layoutParams 넣어줌
        commentLayout.layoutParams = layoutParams

        // 부모뷰에 댓글 레이아웃 추가
        parentLayout.addView(commentLayout)

        // 댓글 삭제 버튼
        // 로그인한 유저의 댓글만 삭제하게 해야함
        // 로그인한 유저와 해당 댓글을 작성한 유저가 같으면
        if (loginedUser == comment.writeUser) {

            // 댓글 삭제 버튼 클릭리스너
            commentLayout.findViewById<ImageButton>(R.id.detail_comment_list_ib_delete).setOnClickListener {

                // 댓글 레이아웃이 비어있지 않다면
                if (commentLayouts.isNotEmpty()) {

                    // 부모뷰에서 해당 댓글 레이아웃 삭제
                    parentLayout.removeViewAt(commentLayouts.indexOf(commentLayout))

                    // 댓글 레이아웃 리스트에서 해당 댓글 레이아웃 삭제
                    commentLayouts.remove(commentLayout)

                    // 댓글이 삭제됨을 알리는  toast 메세지 출력
                    Toast.makeText(this@DetailActivity, "댓글이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                }

                // 글의 댓글 리스트에서 해당 댓글 삭제
                post1.comments.remove(comment)
            }
        } else { // 로그인한 유저와 해당 댓글을 작성한 유저가 같지 않으면

            // 댓글 삭제 버튼을 보이지 않게 설정
            commentLayout.findViewById<ImageButton>(R.id.detail_comment_list_ib_delete).visibility = View.GONE
        }
    }

    /**
     * 좋아요를 아무도 안눌렀을 때, 한명만 눌렀을 때, 한 명 초과가 눌렀을 때의 텍스트를 셋팅하는 함수
     *
     * @param view : 좋아요를 누가 눌렀는지 보여주는 텍스트뷰
     * @author 신지원
     */
    fun showLikers(view: TextView) {

        // 해당 글의 좋아요 리스트가 비어있지 않다면
        if (post1.likes.isNotEmpty()) {
            when {

                // 사이즈가 1이라면
                post1.likes.size == 1 -> {
                    view.visibility = View.VISIBLE
                    view.text = "${post1.likes.iterator().next().checkedUser.nickname}님이 좋아합니다"
                }

                // 사이즈가 1 초과라면
                else -> {
                    view.visibility = View.VISIBLE
                    view.text = "${post1.likes.iterator().next().checkedUser.nickname}님 외 여러명이 좋아합니다"
                }
            }
        } else { // 해당 글의 좋아요가 비어있다면

            // 해당 텍스트뷰를 보이지 않게 설정
            view.visibility = View.GONE
        }
    }

    /**
     * editText에 텍스트를 작성했을 때 submit 버튼의 색깔을 바꾸는 함수
     *
     * @param editText : 텍스트가 작성되었는지 감지하기 위한 editText
     * @author 신지원
     */
    fun changedBtnColor(editText: EditText) {

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            // 텍스트가 변경되는 도중 호출
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // 텍스트가 비어있다면
                if (s.isNullOrEmpty()) {

                    // submit 버튼의 색깔 설정
                    detailTvCommentSubmitBtn.setTextColor(
                        ContextCompat.getColor(this@DetailActivity, R.color.divider)
                    )
                } else { // 텍스트가 비어있지 않다면

                    // submit 버튼의 색깔 설정
                    detailTvCommentSubmitBtn.setTextColor(
                        ContextCompat.getColor(this@DetailActivity, R.color.button)
                    )
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * 새 댓글을 작성했을 때의  submit 버튼 클릭 리스너
     *
     * @author 신지원
     */
    fun setSubmitButtonListenerForNewComment() {

        // detailEtCommentBox의 힌트를 셋팅
        detailEtCommentBox.hint = "댓글을 작성해주세요"

        detailTvCommentSubmitBtn.setOnClickListener {

            // detailEtCommentBox가 비어있지 않다면
            if (detailEtCommentBox.text.isNotEmpty()) {

                // 댓글 레이아웃을 추가하는 함수 (댓글 객체에 detailEtCommentBox의 텍스트와 로그인 유저를 담아 넘김)
                addCommentLayout(Comment(detailEtCommentBox.text.toString(), loginedUser))

                // 댓글을 작성하고 submit 버튼을 눌렀을 때 detailEtCommentBox의 텍스트를 비워줌
                detailEtCommentBox.text = null

                // detailSv의 bottom으로 스크롤
                detailSv.smoothScrollTo(0, detailSv.bottom)

                // 키보드 숨김
                imm.hideSoftInputFromWindow(detailEtCommentBox.windowToken, 0)

                // 포커스 제거
                detailEtCommentBox.clearFocus()

                // submit 버튼의 색깔을 바꿔주는 함수 호출
                changedBtnColor(detailEtCommentBox)
            } else { // detailEtCommentBox가 비어있다면

                // 댓글이 입력되지 않음을 알리는 toast 메세지 호출
                Toast.makeText(this, "댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 댓글을 수정할 때의 submit 버튼 클릭 리스너
     *
     * @param comment : 수정할 댓글의 객체
     * @param detailCommentListTvComment : 수정할 댓글의 텍스트뷰
     *
     * @author 신지원
     */
    fun setSubmitButtonListenerForEditComment(comment: Comment, detailCommentListTvComment: TextView) {

        // detailEtCommentBox의 힌트 셋팅
        detailEtCommentBox.hint = "수정할 댓글을 입력해주세요"

        // 댓글을 수정하고 submit 버튼을 눌렀을 때
        detailTvCommentSubmitBtn.setOnClickListener {

            // detailEtCommentBox가 비어있지 않다면
            if (detailEtCommentBox.text.isNotEmpty()) {

                // 수정할 댓글의 텍스트를 수정한 텍스트로 바꿈
                detailCommentListTvComment.text = detailEtCommentBox.text

                // 수정할 댓글의 객체도 수정
                comment.content = detailEtCommentBox.text.toString()

                // 댓글이 수정됨을 알리는 toast 메세지 띄움
                Toast.makeText(this, "댓글이 수정되었습니다", Toast.LENGTH_SHORT).show()

                // 댓글을 작성하고 submit 버튼을 눌렀을 때 detailEtCommentBox의 텍스트를 비워줌
                detailEtCommentBox.text = null

                // detailEtCommentBox 포커스 삭제
                detailEtCommentBox.clearFocus()

                // 키보드 숨김
                imm.hideSoftInputFromWindow(detailEtCommentBox.windowToken, 0)

                // 다시 submit 버튼의 텍스트를 "게시"로 바꿈
                detailTvCommentSubmitBtn.text = "게시"
            } else { // detailEtCommentBox가 비어있다면

                // 댓글이 비어있음을 알리는 toast 메세지 띄움
                Toast.makeText(this, "수정할 댓글을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
