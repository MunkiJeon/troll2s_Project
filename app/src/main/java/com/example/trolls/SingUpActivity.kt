package com.example.trolls

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SingUpActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_singup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var singup_usernickname_tv = findViewById<TextView>(R.id.singup_usernickname_tv)
        var editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)
        var sinup_password_input_tv = findViewById<EditText>(R.id.sinup_password_input_tv)
        val singup_all_checkedbox_btn = findViewById<CheckBox>(R.id.singup_all_checkedbox_btn)
        val singup_finish_btn = findViewById<Button>(R.id.singup_finish_btn)
        var singup_nickid_tv = findViewById<EditText>(R.id.singup_nickid_tv)

        singup_finish_btn.setOnClickListener {
            if (singup_nickid_tv.text.isEmpty() || sinup_password_input_tv.text.isEmpty() || editTextTextEmailAddress.text.isEmpty()
                || sinup_password_input_tv.text.length !in 6..24) {
                Toast.makeText(this, "정확한 정보가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            } else if (singup_all_checkedbox_btn.isChecked.not()) {
                Toast.makeText(this, "체크박스를 클릭해주세요", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                val it_singup = Intent(this, SingupOkActivity::class.java)
                it_singup.putExtra("USEREMALL",editTextTextEmailAddress.text.toString())
                startActivity(it_singup)
            }




            if (sinup_password_input_tv.text.length in 16..24)
                Toast.makeText(this, "사용가능한 비밀번호 입니다", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(this, "비밀번호 설정을 확인해주세요(6~24자 이내)", Toast.LENGTH_SHORT).show()
            }


        }

    }
}
