package com.example.trolls

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val appLocale = LocaleListCompat.forLanguageTags("ko")
        AppCompatDelegate.setApplicationLocales(appLocale)

        var singin_password_putin_et = findViewById<EditText>(R.id.singin_password_putin_et)
        val singin_btn = findViewById<Button>(R.id.singin_btn)
        val singin_join_btn = findViewById<Button>(R.id.singin_join_btn)
        var singin_idinput_et = findViewById<EditText>(R.id.singin_idinput_et)

        singin_btn.setOnClickListener {
            if (singin_idinput_et.text.isEmpty() || singin_password_putin_et.text.isEmpty()||singin_idinput_et.text.length !in 6..24)
                Toast.makeText(this, "확인 후 로그인 바랍니다", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                val it_singin = Intent(
                    this, MainActivity::class.java)
                it_singin.putExtra("USERINFO",singin_idinput_et.text.toString())
                startActivity (it_singin)
                finish()
            }


        }


//        val it_singin = Intent(
//            this, //메인화면엑티비티//::class.java)
//            it_singin.putExtra("USERINFO", it_singin.text.toString())
//                    startActivity (it_singin)

        singin_join_btn.setOnClickListener {
            val it_singin = Intent(this, SignUpActivity::class.java)
            startActivity(it_singin)
        }


    }
}