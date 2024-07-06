package com.example.trolls

import Comment
import Like
import Post
import User
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView


class MyPageActivity : AppCompatActivity() {
    /**
     * 더미 데이터 정의
     */
    private lateinit var userData: User

    var jaeseon = User(
        "yoo",
        "1234",
        "유재선",
        "jaeseon's nickname",
        "hi!!!!hello",
        mutableListOf(),
        mutableListOf(),
        R.drawable.dummy_image03
    )
    var jiwon = User(
        "shin",
        "1234",
        "신지원",
        "jiwon's nickname",
        "hi!!!!hello",
        mutableListOf(),
        mutableListOf(),
        R.drawable.dummy_image02
    )
    var moonki = User(
        "jeon",
        "1234",
        "전문기",
        "moonki's nickname",
        "hi!!!!hello",
        mutableListOf(),
        mutableListOf(),
        R.drawable.dummy_image05
    )
    var rian = User(
        "kim",
        "1234",
        "김리안",
        "rian's nickname",
        "hi!!!!hello",
        mutableListOf(),
        mutableListOf(),
        R.drawable.dummy_image04
    )
    var seungmo = User(
        "seong",
        "1234",
        "성승모",
        "seungmo's nickname",
        "hi!!!!hello",
        mutableListOf(),
        mutableListOf(),
        R.drawable.dummy_image03
    )

    val logInedUser = jaeseon

    val comment1_1 = Comment("댓글입니다1_1", jaeseon)
    val comment1_2 = Comment("댓글입니다1_2", jaeseon)
    val comment1_3 = Comment("댓글입니다1_3", jaeseon)
    val comment2_1 = Comment("댓글입니다2_1", jiwon)
    val comment2_2 = Comment("댓글입니다2_2", jiwon)
    val comment2_3 = Comment("댓글입니다2_3", jiwon)
    val comment3_1 = Comment("댓글입니다3_1", moonki)
    val comment3_2 = Comment("댓글입니다3_2", moonki)
    val comment3_3 = Comment("댓글입니다3_3", moonki)
    val comment4_1 = Comment("댓글입니다4_1", seungmo)
    val comment4_2 = Comment("댓글입니다4_2", seungmo)
    val comment4_3 = Comment("댓글입니다4_3", seungmo)
    val comment5_1 = Comment("댓글입니다5_1", rian)
    val comment5_2 = Comment("댓글입니다5_2", rian)
    val comment5_3 = Comment("댓글입니다5_3", rian)

    //TODO: type miss match
    val post_panni1 = Post(
        1,
        R.drawable.dummy_image03,
        "빠니보틀 제목1",
        "빠니보틀 본문1",
        jaeseon,
        comments = mutableListOf(),
        likes = mutableListOf(Like(jiwon))
    )
    val post_panni2 = Post(
        2, R.drawable.dummy_image04, "빠니보틀 제목2", "빠니보틀 본문2", jaeseon, comments = mutableListOf()
    )
    val post_kwack1 =
        Post(3, R.drawable.dummy_image06, "곽튜브 제목1", "곽튜브 본문2", jaeseon, comments = mutableListOf())
    val post_kwack2 =
        Post(4, R.drawable.dummy_image07, "곽튜브 제목2", "곽튜브 본문2", jaeseon, comments = mutableListOf())


    val like1_1 = Like(jaeseon)
    val like1_2 = Like(jiwon)
    val like1_3 = Like(moonki)
    val like2_1 = Like(jaeseon)
    val like2_2 = Like(jiwon)
    val like2_3 = Like(moonki)
    val like3_1 = Like(moonki)
    val like3_2 = Like(seungmo)
    val like3_3 = Like(rian)
    val like4_1 = Like(seungmo)
    val like4_2 = Like(rian)
    val like4_3 = Like(moonki)
    val like5_1 = Like(rian)
    val like5_2 = Like(moonki)
    val like5_3 = Like(jiwon)

    // Activity Result에 콜백 등록 (MainActivity.kt)
    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            resultDataId = result
            if (resultDataId!!.data!!.getStringExtra("TARGET") == "profile") {
                //변경된 프로필 정보 업데이트
                userData.name = resultDataId!!.data!!.getStringExtra("NAME") ?: "이름없음"
                userData.nickname = resultDataId!!.data!!.getStringExtra("NICK") ?: "닉네임 없음"
                userData.profileImageResource = resultDataId!!.data!!.getIntExtra("IMG", 0)

                //변경된 프로필 정보 적용
                Glide.with(this).load(userData.profileImageResource)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(90)))
                    .into(mypage_iv_profilepic)
                mypage_tv_myid.setText(userData.id)
                mypage_tv_myname.setText(userData.name)
                mypage_tv_nickname.setText(userData.nickname)
            } else if(resultDataId!!.data!!.getStringExtra("TARGET") == "intro"){
                //변경된 소개 업데이트
                userData.intro = resultDataId!!.data!!.getStringExtra("INTRO") ?: ""
                //변경된 소개 적용
                mypage_tv_intro.setText(userData.intro)
            } else {
                //변경된 정보 업데이트
                userData.name = resultDataId!!.data!!.getStringExtra("NAME") ?: "이름없음"
                userData.nickname = resultDataId!!.data!!.getStringExtra("NICK") ?: "닉네임 없음"
                userData.profileImageResource = resultDataId!!.data!!.getIntExtra("IMG", 0)
                userData.intro = resultDataId!!.data!!.getStringExtra("INTRO") ?: ""

                //변경된 정보 적용
                Glide.with(this).load(userData.profileImageResource)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(90)))
                    .into(mypage_iv_profilepic)
                mypage_tv_myid.setText(userData.id)
                mypage_tv_myname.setText(userData.name)
                mypage_tv_nickname.setText(userData.nickname)
                mypage_tv_intro.setText(userData.intro)
            }
        }
    }
    private var resultDataId: ActivityResult? = null
    private fun convertDpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics
        ).toInt()
    }

    //프로필 설정 박스 부분
    private lateinit var mypage_iv_profilepic: ImageView
    private lateinit var mypage_tv_myid: TextView
    private lateinit var mypage_tv_myname: TextView
    private lateinit var mypage_tv_nickname: TextView

    // 소개말 설정 부분
    private lateinit var mypage_tv_intro: TextView

    //내가 작성한 글
    private var posts = mutableListOf(
        post_panni1,
        post_panni2,
        post_kwack1,
        post_kwack2,
    )

    //내가 좋아요한 글
    private var likes = mutableListOf(
        like1_1, like1_2, like1_3,
        like2_1, like2_2, like2_3,
        like3_1, like3_2, like3_3,
        like4_1, like4_2, like4_3,
        like5_1, like5_2, like5_3,)

    override fun onCreate(savedInstanceState: Bundle?) {
        //메인화면에서 불러온 데이터 셋팅
        userData = intent.getParcelableExtra("loginUser") ?: intent.getParcelableExtra("LIKE") ?: moonki

//        Log.d("PostSize", userData.myPosts.size.toString())
//        Log.d("LikesSize", userData.myLikes.size.toString())

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //프로필 변경용 Intent
        var editIntent = Intent(this, EditPage::class.java)

        //최상단 Top Layer
        var mypage_tv_title = findViewById<TextView>(R.id.mypage_tv_title)
        var mypage_btn_gomain = findViewById<Button>(R.id.mypage_btn_gomain)
        var mypage_btn_gosetting = findViewById<Button>(R.id.mypage_btn_gosetting)

        //TODO: 프로필 설정 박스 부분
        var mypage_btn_profilechange = findViewById<Button>(R.id.mypage_btn_profilechange)
        mypage_iv_profilepic = findViewById<CircleImageView>(R.id.mypage_iv_profilepic)
        mypage_tv_myid = findViewById<TextView>(R.id.mypage_tv_myid)
        mypage_tv_myname = findViewById<TextView>(R.id.mypage_tv_myname)
        mypage_tv_nickname = findViewById<TextView>(R.id.mypage_tv_nickname)

        //TODO: 소개말 설정 부분
        mypage_tv_intro = findViewById<TextView>(R.id.mypage_tv_intro).apply {
            if(intent.getStringExtra("TARGET") != "like"){
                setOnClickListener {
                    editIntent.apply {
                        putExtra("INTRO", mypage_tv_intro.text)
                        putExtra("TARGET", "intro")
                    }
                    getResultText.launch(editIntent)
                }
            }
        }

        //TODO: 내가 작성한 글
        var mypage_lo_mypost = this.findViewById<LinearLayout>(R.id.mypage_lo_myposts)
        var postIntent = Intent(this, DetailActivity::class.java)
        for (post in posts) {
            val circleView = CircleImageView(this)
            circleView.setImageResource(post.imageResource) // drawable id
            circleView.setOnClickListener {
                postIntent.putExtra("IMG", post.imageResource)
                startActivity(postIntent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            val circleView_params = LinearLayout.LayoutParams(
                convertDpToPixel(90), // width
                convertDpToPixel(90) // height
            )
            circleView_params.marginEnd = convertDpToPixel(15)
            circleView.layoutParams = circleView_params
            mypage_lo_mypost.addView(circleView)
        }

        //TODO: 내가 좋아요한 글
        var mypage_tv_mylike = findViewById<TextView>(R.id.mypage_tv_mylike)
        var mypage_lo_likelist = this.findViewById<HorizontalScrollView>(R.id.mypage_lo_likelist)
        var mypage_lo_mylikes = this.findViewById<LinearLayout>(R.id.mypage_lo_mylikes)
        var likeIntent = Intent(this, MyPageActivity::class.java)
        for (like in likes) {
            val circleView = CircleImageView(this)
            circleView.setImageResource(like.checkedUser.profileImageResource) // drawable id
            circleView.setOnClickListener{
                likeIntent.putExtra("LIKE", like.checkedUser)
                likeIntent.putExtra("TARGET","like")
                    startActivity(likeIntent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            val circleView_params = LinearLayout.LayoutParams(
                convertDpToPixel(90), // width
                convertDpToPixel(90) // height
            )
            circleView_params.marginEnd = convertDpToPixel(15)
            circleView.layoutParams = circleView_params
            mypage_lo_mylikes.addView(circleView)
        }

        //화면 셋팅 부분
        Glide.with(this)//this = MainActivity
            //바꿀 이미지 리소스
            .load(userData.profileImageResource)
            //apply = 이미지 옵션
            .apply(RequestOptions.bitmapTransform(RoundedCorners(90)))
            //into = 어디다 그릴지
            .into(mypage_iv_profilepic)
        mypage_tv_myid.setText(userData.id)
        mypage_tv_myname.setText(userData.name)
        mypage_tv_nickname.setText(userData.nickname)

        if(intent.getStringExtra("TARGET") == "like"){
            mypage_tv_title.setText(userData.id +"'s Page")
            mypage_btn_profilechange.visibility = View.INVISIBLE
            mypage_tv_mylike.visibility = View.INVISIBLE
            mypage_lo_likelist.visibility = View.INVISIBLE
        } else {
            mypage_btn_profilechange.visibility = View.VISIBLE
            mypage_tv_mylike.visibility = View.VISIBLE
            mypage_lo_likelist.visibility = View.VISIBLE
        }

        mypage_btn_gomain.setOnClickListener {
            finish()
        }

        mypage_btn_gosetting.setOnClickListener {
            var optionIntent = Intent(this, OptionActivity::class.java)
            startActivity(optionIntent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        mypage_btn_profilechange.setOnClickListener {
            editIntent.apply {
                putExtra("ID", userData.id)
                putExtra("NAME", userData.name)
                putExtra("NICK", userData.nickname)
                putExtra("IMG", userData.profileImageResource)
                putExtra("TARGET", "profile")
            }
            getResultText.launch(editIntent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

    }
}