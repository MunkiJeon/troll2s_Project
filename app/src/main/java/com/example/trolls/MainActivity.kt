package com.example.trolls

import Comment
import Dummy
import Like
import Post
import User
import Youtuber
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.util.TypedValue
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageButton
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


//    private val dummy = Dummy()



    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val posts = Dummy.posts
        val youtubers = Dummy.youtubers
        val youtuberToPosts = Dummy.youtuberToPosts

        val logInedUser = intent.getParcelableExtra("USERINFO")?:User()

//        Log.d("logInedUser", logInedUser.id)

        val main_page_btn_my_page = findViewById<Button>(R.id.main_page_btn_my_page)

        main_page_btn_my_page.setOnClickListener{
            // 마이페이지로 loginUser 전송
            val intent_to_My_page = Intent(this, MyPageActivity::class.java)
            intent_to_My_page.putExtra("loginUser", logInedUser)
            startActivity(intent_to_My_page)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
//            main_page_civ_youtuber_icon.borderColor = ContextCompat.getColor(this, R.color.image_view)
//            main_page_civ_youtuber_icon.borderColor = resources.getColor(R.attr.colorImageView)

            val main_page_inner_layout_contents = findViewById<LinearLayout>(R.id.main_page_inner_layout_contents)
            main_page_inner_layout_contents.orientation = LinearLayout.VERTICAL
            val main_page_sv_contents = findViewById<ScrollView>(R.id.main_page_sv_contents)
            main_page_civ_youtuber_icon.setOnClickListener{
                if(before_selected_icon != null) {
                    before_selected_icon!!.borderWidth = convertDpToPixel(0)
                }
                main_page_civ_youtuber_icon.borderWidth = convertDpToPixel(5)
                main_page_inner_layout_contents.removeAllViews()

//                main_page_sv_contents.fullScroll(ScrollView.FOCUS_UP)
                main_page_sv_contents.post{
                    main_page_sv_contents.fullScroll(ScrollView.FOCUS_UP)
                }


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
                    main_page_inner_layout_contents.addView(makeMainPageContent(post, logInedUser))
                }

            }
            // add icon
            main_page_inner_layout_scroll_youtuber.addView(main_page_civ_youtuber_icon)
        }

        val main_page_inner_layout_contents =
            findViewById<LinearLayout>(R.id.main_page_inner_layout_contents)
        for (post in posts) {
            main_page_inner_layout_contents.addView(makeMainPageContent(post, logInedUser))
        }
    }

    private fun convertDpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics).toInt()
    }

    @SuppressLint("ResourceAsColor")
    private fun makeMainPageContent(post: Post, logInedUser: User) : LinearLayout {
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
        val ic_comment = ImageView(this)
//        val ic_comment = ImageButton(this)
        val ic_like = ToggleButton(this)
        val content = TextView(this)

        image_view_post.transitionName = "iv_main_image"
        val image_view_post_params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        image_view_post.setImageResource(post.imageResource)
        image_view_post.layoutParams = image_view_post_params
        main_page_layout_post_image.addView(image_view_post)


        val main_page_layout_post_icons = LinearLayout(this)
        main_page_layout_post_icons.orientation = LinearLayout.HORIZONTAL

        val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)

        scaleAnimation.duration = 500
        val boundInterpolator = BounceInterpolator()

        scaleAnimation.interpolator = boundInterpolator

        ic_like.backgroundTintList = resources.getColorStateList(R.color.button, null)
        ic_like.transitionName = "tb_like"
        ic_like.setBackgroundResource(R.drawable.main_page_sel_like)

//        ic_like.highlightColor = R.color.image_view
//        DrawableCompat.setTint(ic_like.buttonDrawable!!, ContextCompat.getColor(this,R.color.image_view))

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
//        ic_like.setBackgroundColor(R.attr.colorImageView)
//        ic_like.setBackgroundColor(R.color.image_view)

        // click like button
        ic_like.setOnCheckedChangeListener { compoundButton, isChecked ->
//            Log.d("like button", "isChecked = ${isChecked}")
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


        ic_comment.transitionName = "iv_comment"
        ic_comment.setImageResource(R.drawable.ic_comment)
//        ic_comment.background = R.drawable.
        ic_comment.layoutParams = LinearLayout.LayoutParams(
            convertDpToPixel(50),
            convertDpToPixel(50)
        )

//       click comment (to DetailActivity)
        ic_comment.setOnClickListener {
            // 디테일 페이지로 post 전송(댓글버튼 클릭)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(image_view_post, "iv_main_image"),
                Pair.create(ic_like, "tb_like"),
                Pair.create(ic_comment, "iv_comment"),
                Pair.create(content, "tv_content"),
            )

            val intentToDetail = Intent(this, DetailActivity::class.java)
            intentToDetail.putExtra("loginedUser", logInedUser)
            intentToDetail.putExtra("post", post)
            intentToDetail.putExtra("from", "ic_comment")
            startActivity(intentToDetail, options.toBundle())
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        }

        main_page_layout_post_icons.addView(ic_like)
        main_page_layout_post_icons.addView(ic_comment)

        val main_page_layout_post_content = LinearLayout(this)
        main_page_layout_post_content.orientation = LinearLayout.VERTICAL


        main_page_layout_post_content.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )


        content.transitionName = "tv_content"
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

        main_page_layout_post_image.setOnClickListener {

//          디테일 페이지로 post 전송(이미지 클릭)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(image_view_post, "iv_main_image"),
                Pair.create(ic_like, "tb_like"),
                Pair.create(ic_comment, "iv_comment"),
                Pair.create(content, "tv_content"),
            )


            // click image (to DetailActivity)
            val intentToDetail = Intent(this, DetailActivity::class.java)
            intentToDetail.putExtra("post", post)
            intentToDetail.putExtra("from", "image")
            intentToDetail.putExtra("loginedUser", logInedUser)

            startActivity(intentToDetail, options.toBundle())

            // API version less than 33
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

            // API version more than 33
//            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.fade_in, R.anim.fade_out)
        }

        entire_layout.addView(main_page_layout_post_image)
        entire_layout.addView(main_page_layout_post_icons)
        entire_layout.addView(main_page_layout_post_content)

        return entire_layout
    }
}