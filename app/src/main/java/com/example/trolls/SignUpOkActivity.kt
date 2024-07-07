package com.example.trolls

import User
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpOkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_ok)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        findViewById<TextView>(R.id.email_text).text = intent.getStringExtra("USEREMALL")!!
        val user = intent.getParcelableExtra("USERINFO")?: User()
        findViewById<TextView>(R.id.nick_ok_tv).text = user.id

        findViewById<Button>(R.id.sininup_add_btn).setOnClickListener {
            finish()
        }
    }
}