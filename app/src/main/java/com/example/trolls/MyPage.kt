package com.example.trolls

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class MyPage : AppCompatActivity() {
    fun changeShape(img: ImageView) {
        Glide.with(this)//this = MainActivity
            //바꿀 이미지 리소스
            .load(R.drawable.dummy_image02)// 임시 더미 이미지
            //apply = 이미지 옵션
            .apply(RequestOptions.bitmapTransform(RoundedCorners(70)))
            //into = 어디다 그릴지
            .into(img)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /**
         * 프로필 설정 박스 부분
         */
        findViewById<ImageView>(R.id.mypage_iv_profilepic)
        findViewById<TextView>(R.id.mypage_tv_myid)
        findViewById<TextView>(R.id.mypage_tv_nickname)
        findViewById<Button>(R.id.mypage_btn_profilechnge)
        /**
         * 소개말 설정 부분
         */
        findViewById<Button>(R.id.mypage_btn_intro)
        findViewById<TextView>(R.id.mypage_et_intro)

    }
}