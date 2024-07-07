package com.example.trolls

import Comment
import Dummy
import Like
import Post
import User
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Pair
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
    private val dummy = Dummy()

    /**
     * 더미 데이터 정의
     */
    private lateinit var userData: User

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

    private val posts = dummy.posts
    private val likes = dummy.likes

    //내가 작성한 글
//    private var posts = mutableListOf(
//        post_panni1,
//        post_panni2,
//        post_kwack1,
//        post_kwack2,
//    )
//
//    //내가 좋아요한 글
//    private var likes = mutableListOf(
//        like1_1, like1_2, like1_3,
//        like2_1, like2_2, like2_3,
//        like3_1, like3_2, like3_3,
//        like4_1, like4_2, like4_3,
//        like5_1, like5_2, like5_3,)

    override fun onCreate(savedInstanceState: Bundle?) {
        //메인화면에서 불러온 데이터 셋팅
        userData = intent.getParcelableExtra("loginUser") ?: intent.getParcelableExtra("LIKE") ?: throw Exception("유저 정보가 없습니다.")

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
            circleView.transitionName = "iv_main_image"
            circleView.setImageResource(post.imageResource) // drawable id

            circleView.setOnClickListener {
//                postIntent.putExtra("IMG", post.imageResource)
                postIntent.putExtra("activity from", "MyPage")
                val options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(circleView, "iv_main_image"),)
                postIntent.putExtra("loginedUser", userData)
                postIntent.putExtra("post", post)
                startActivity(postIntent, options.toBundle())
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