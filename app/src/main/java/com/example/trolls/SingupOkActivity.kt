package com.example.trolls

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SingupOkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_singupok)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var sininup_wellcome_tv = findViewById<TextView>(R.id.sininup_wellcome_tv)
        var sininup_add_btn = findViewById<Button>(R.id.sininup_add_btn)
        var nick_ok_tv = findViewById<TextView>(R.id.nick_ok_tv)

        //저장된 메일 변동되도록 설정
       val USERMALL = intent.getStringExtra("USEREMALL").toString()
        nick_ok_tv.text = USERMALL

//sininup_add_btn.setOnClickListner {
        //누르면 재선님 메인화면으로 갈 수 있게
//        val it_main = Intent(
//            this, //메인화면엑티비티//::class.java)
//                    startActivity (it_singin)

    }
}