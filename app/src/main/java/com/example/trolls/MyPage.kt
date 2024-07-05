package com.example.trolls

import Comment
import Like
import Post
import User
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream


class MyPage : AppCompatActivity() {
    /**
     * 더미 데이터 정의
     */
    private var userData = User("jeon", "1234","전문기", "moonki's nickname","hi!!!!hello",mutableListOf(),mutableListOf(),R.drawable.dummy_image01)

    var jaeseon = User("yoo", "1234","유재선", "jaeseon's nickname","hi!!!!hello",mutableListOf(),mutableListOf(),R.drawable.dummy_image01)
    var jiwon = User("shin", "1234","신지원", "jiwon's nickname","hi!!!!hello",mutableListOf(),mutableListOf(),R.drawable.dummy_image01)
    var moonki = User("jeon", "1234","전문기", "moonki's nickname","hi!!!!hello",mutableListOf(),mutableListOf(),R.drawable.dummy_image01)
    var rian = User("kim", "1234","김리안", "rian's nickname","hi!!!!hello",mutableListOf(),mutableListOf(),R.drawable.dummy_image01)
    var seungmo = User("seong", "1234","성승모", "seungmo's nickname","hi!!!!hello",mutableListOf(),mutableListOf(),R.drawable.dummy_image01)

    val logInedUser = jaeseon

    val post_panni1 = Post(1, R.drawable.dummy_image02, "빠니보틀 제목1","빠니보틀 본문1", jaeseon, mutableListOf(), likes = mutableListOf(Like(jiwon)))
    val post_panni2 = Post(2, R.drawable.dummy_image02, "빠니보틀 제목2","빠니보틀 본문2", jaeseon, mutableListOf())
    val post_kwack1 = Post(3, R.drawable.dummy_image01, "곽튜브 제목1","곽튜브 본문2", jaeseon, mutableListOf())
    val post_kwack2 = Post(4, R.drawable.dummy_image01, "곽튜브 제목2","곽튜브 본문2", jaeseon, mutableListOf())


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
            if (resultDataId!!.data!!.getStringExtra("TARGET") == "profile"){
                mypage_iv_profilepic.setImageResource(resultDataId!!.data!!.getIntExtra("IMG",0))
                mypage_tv_myid.setText(resultDataId!!.data!!.getStringExtra("ID") ?: throw Exception("No ID"))
                mypage_tv_myname.setText(resultDataId!!.data!!.getStringExtra("NAME") ?: throw Exception("No Name"))
                mypage_tv_nickname.setText(resultDataId!!.data!!.getStringExtra("NICK") ?: throw Exception("No Nick"))

                userData.id = resultDataId!!.data!!.getStringExtra("ID") ?: throw Exception("No ID")
                userData.name = resultDataId!!.data!!.getStringExtra("NAME") ?: throw Exception("No Name")
                userData.nickname = resultDataId!!.data!!.getStringExtra("NICK") ?: throw Exception("No Nickname")
            } else{
                mypage_tv_intro.setText(resultDataId!!.data!!.getStringExtra("INTRO") ?: throw Exception("No Intro"))
            }
        }
    }
    private var resultDataId: ActivityResult? = null

    //프로필 설정 박스 부분
    private lateinit var mypage_iv_profilepic: ImageView
    private lateinit var mypage_tv_myid: TextView
    private lateinit var mypage_tv_myname: TextView
    private lateinit var mypage_tv_nickname: TextView

    // 소개말 설정 부분
    private lateinit var mypage_tv_intro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var mypage_iv_logo = findViewById<Button>(R.id.mypage_iv_logo)
        //프로필 설정 박스 부분
        var mypage_btn_profilechange = findViewById<Button>(R.id.mypage_btn_profilechange)
        mypage_iv_profilepic = findViewById<ImageView>(R.id.mypage_iv_profilepic)
        mypage_tv_myid = findViewById<TextView>(R.id.mypage_tv_myid)
        mypage_tv_myname = findViewById<TextView>(R.id.mypage_tv_myname)
        mypage_tv_nickname = findViewById<TextView>(R.id.mypage_tv_nickname)
        // 소개말 설정 부분
        mypage_tv_intro = findViewById<TextView>(R.id.mypage_tv_intro)
        //내가 작성한 글

        //내가 좋아요한 글

        mypage_iv_profilepic.setImageResource(userData.profileImageResource)
        mypage_tv_myid.setText(userData.id)
        mypage_tv_myname.setText(userData.name)
        mypage_tv_nickname.setText(userData.nickname)

        mypage_tv_intro.setText(userData.intro)

        mypage_iv_logo.setOnClickListener {
            finish()
        }

        var editIntent = Intent(this, EditPage::class.java)

        mypage_btn_profilechange.setOnClickListener{
            editIntent.apply {
                putExtra("ID",mypage_tv_myid.text)
                putExtra("NAME",mypage_tv_myname.text)
                putExtra("NICK",mypage_tv_nickname.text)
                putExtra("IMG",userData.profileImageResource)
                putExtra("TARGET","profile")
            }
            getResultText.launch(editIntent)
        }

        mypage_tv_intro.setOnClickListener{
            editIntent.apply {
                putExtra("INTRO",mypage_tv_intro.text)
                putExtra("TARGET","intro")
            }
            getResultText.launch(editIntent)
        }
    }
}