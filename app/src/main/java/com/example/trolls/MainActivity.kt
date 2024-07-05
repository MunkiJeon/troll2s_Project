package com.example.trolls

import Comment
import Like
import Post
import User
import Youtuber
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    val youtuberToPosts = mutableMapOf<Youtuber, MutableList<Post>>()
    val youtubers = mutableListOf<Youtuber>()
    val posts = mutableListOf<Post>()

    //TODO: Intro 추가
    val jaeseon = User("yoo", "1234","유재선", "jaeseon's nickname")
    val jiwon = User("shin", "1234","신지원", "jiwon's nickname")
    val moonki = User("jeon", "1234","전문기", "moonki's nickname")
    val rian = User("kim", "1234","김리안", "rian's nickname")
    val seungmo = User("seong", "1234","성승모", "seungmo's nickname")

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

    val logInedUser = jaeseon

    val panni = Youtuber("panni", "1234", "빠니보틀", "panni", profileIcon = R.drawable.panni_bottle_profile_image)
    val kwack = Youtuber("kwack", "1234", "곽튜브", "kwack",profileIcon = R.drawable.kwack_profile_image)
    val wonji = Youtuber("wonji", "1234", "원지", "wonji", profileIcon = R.drawable.wonji_profile_image)
    val soy = Youtuber("soy", "1234", "쏘이", "soy", profileIcon = R.drawable.soy_profile_image)

    val post_panni1 = Post(1, R.drawable.post_main_image_panni1, "빠니보틀 제목1","빠니보틀 본문1", jaeseon, panni, likes = mutableListOf(Like()))
    val post_panni2 = Post(2, R.drawable.post_main_image_panni2, "빠니보틀 제목2","빠니보틀 본문2", jaeseon, panni)
    val post_kwack1 = Post(3, R.drawable.post_main_image_kwack2, "곽튜브 제목1","곽튜브 본문2", jaeseon, kwack)
    val post_kwack2 = Post(4, R.drawable.post_main_image_kwack1, "곽튜브 제목2","곽튜브 본문2", jaeseon, kwack)


    init{
        posts.add(post_panni1)
        posts.add(post_panni2)
        posts.add(post_kwack1)
        posts.add(post_kwack2)


        youtuberToPosts[panni] = mutableListOf(post_panni1, post_panni2)
        youtuberToPosts[kwack] = mutableListOf(post_kwack1, post_kwack2)
        youtubers.add(panni)
        youtubers.add(kwack)
        youtubers.add(wonji)
        youtubers.add(soy)

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val main_page_btn_my_page = findViewById<Button>(R.id.main_page_btn_my_page)

        main_page_btn_my_page.setOnClickListener{
            // 마이페이지로 loginUser 전송
            val intent_to_My_page = Intent(this, MyPageActivity::class.java)
            intent_to_My_page.putExtra("loginUser", logInedUser)
            startActivity(intent_to_My_page)
        }

        val main_page_inner_layout_scroll_youtuber = findViewById<LinearLayout>(R.id.main_page_inner_layout_scroll_youtuber)
        var before_selected_icon: CircleImageView? = null
        for (youtuber in youtubers) {

            val main_page_civ_youtuber_icon = CircleImageView(this)
            main_page_civ_youtuber_icon.setImageResource(youtuber.profileIcon)
            val main_page_icon_youtuber_params = LinearLayout.LayoutParams(
                convertDpToPixel(90),
                convertDpToPixel(90)
            )
            main_page_icon_youtuber_params.marginEnd = convertDpToPixel(15)
            main_page_civ_youtuber_icon.layoutParams = main_page_icon_youtuber_params
            main_page_civ_youtuber_icon.borderWidth = convertDpToPixel(0)
            main_page_civ_youtuber_icon.borderColor = ContextCompat.getColor(this, R.color.button)

            val main_page_inner_layout_contents = findViewById<LinearLayout>(R.id.main_page_inner_layout_contents)
            main_page_inner_layout_contents.orientation = LinearLayout.VERTICAL
            val main_page_sv_contents = findViewById<ScrollView>(R.id.main_page_sv_contents)
            main_page_civ_youtuber_icon.setOnClickListener{
                if(before_selected_icon != null) {
                    before_selected_icon!!.borderWidth = convertDpToPixel(0)
                }
                main_page_civ_youtuber_icon.borderWidth = convertDpToPixel(5)
                main_page_inner_layout_contents.removeAllViews()
                main_page_sv_contents.fullScroll(ScrollView.FOCUS_UP)
                Toast.makeText(this, youtuber.name, Toast.LENGTH_SHORT).show()
                val posts = when(before_selected_icon) {
                    main_page_civ_youtuber_icon -> posts
                    else -> youtuberToPosts[youtuber]?: mutableListOf()
                }

                if(before_selected_icon == main_page_civ_youtuber_icon) {
                    before_selected_icon!!.borderWidth = convertDpToPixel(0)
                    before_selected_icon = null
                } else
                    before_selected_icon = main_page_civ_youtuber_icon

                for (post in posts) {
                    main_page_inner_layout_contents.addView(makeMainPageContent(post))
                }

            }
            // add icon
            main_page_inner_layout_scroll_youtuber.addView(main_page_civ_youtuber_icon)
        }

        val main_page_inner_layout_contents =
            findViewById<LinearLayout>(R.id.main_page_inner_layout_contents)
        for (post in posts) {
            main_page_inner_layout_contents.addView(makeMainPageContent(post))
        }
    }

    private fun convertDpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics).toInt()
    }

    private fun makeMainPageContent(post: Post) : LinearLayout {
        val entire_layout = LinearLayout(this)
        entire_layout.orientation = LinearLayout.VERTICAL
        val main_page_layout_post_image = LinearLayout(this)
        main_page_layout_post_image.orientation = LinearLayout.VERTICAL
        val main_page_layout_post_image_params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            convertDpToPixel(300)
        )
        main_page_layout_post_image.layoutParams = main_page_layout_post_image_params

        val image_view_post = ImageView(this)
        val image_view_post_params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        image_view_post.setImageResource(post.imageResource)
        image_view_post.layoutParams = image_view_post_params
        main_page_layout_post_image.addView(image_view_post)

        main_page_layout_post_image.setOnClickListener {
            
            // 디테일 페이지로 post 전송(이미지 클릭)
//            val intent_to_test = Intent(this, TestActivity::class.java)
//            Toast.makeText(this, post.toString(), Toast.LENGTH_SHORT).show()
//            intent_to_test.putExtra("post", post)
//            intent_to_test.putExtra("from", "image")
//            startActivity(intent_to_test)
        }



        val main_page_layout_post_icons = LinearLayout(this)
        main_page_layout_post_icons.orientation = LinearLayout.HORIZONTAL

        val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)

        scaleAnimation.duration = 500
        val boundInterpolator = BounceInterpolator()

        scaleAnimation.interpolator = boundInterpolator

        val ic_like = ToggleButton(this)
        ic_like.setBackgroundResource(R.drawable.main_page_sel_like)
        for (like in post.likes) {
            if(like.checkedUser.id == logInedUser.id) {
                ic_like.isChecked = true
                break
            }
        }

        ic_like.layoutParams = LinearLayout.LayoutParams(
            convertDpToPixel(50),
            convertDpToPixel(50)
        )
        ic_like.textOn = ""
        ic_like.textOff = ""
        ic_like.text = ""

        // like button clicklistner
        ic_like.setOnCheckedChangeListener { compoundButton, isChecked ->
            Log.d("like button", "isChecked = ${isChecked}")
            for ((likeIndex, like) in post.likes.withIndex()) {
                if(like.checkedUser.id == logInedUser.id) {
                    post.likes.removeAt(likeIndex)
                    break
                }
            }

            if(isChecked) // 좋아요 추가
                post.likes.add(Like(logInedUser))

            compoundButton.startAnimation(scaleAnimation)
        }

        val ic_comment = ImageView(this)
        ic_comment.setImageResource(R.drawable.ic_comment)
        ic_comment.layoutParams = LinearLayout.LayoutParams(
            convertDpToPixel(50),
            convertDpToPixel(50)
        )

        ic_comment.setOnClickListener {
            // 디테일 페이지로 post 전송(댓글버튼 클릭)
//            val intent_to_test = Intent(this, TestActivity::class.java)
//            intent_to_test.putExtra("post", post)
//            intent_to_test.putExtra("from", "ic_comment")
//            startActivity(intent_to_test)
        }

        main_page_layout_post_icons.addView(ic_like)
        main_page_layout_post_icons.addView(ic_comment)

        val main_page_layout_post_content = LinearLayout(this)
        main_page_layout_post_content.orientation = LinearLayout.VERTICAL


        main_page_layout_post_content.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val content = TextView(this)
        content.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        content.text = post.content
        content.setTextColor(Color.BLACK)
        main_page_layout_post_content.addView(content)

        val entire_layout_params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        entire_layout_params.bottomMargin = convertDpToPixel(50)
        entire_layout.layoutParams = entire_layout_params
        entire_layout.addView(main_page_layout_post_image)
        entire_layout.addView(main_page_layout_post_icons)
        entire_layout.addView(main_page_layout_post_content)

        return entire_layout
    }
}