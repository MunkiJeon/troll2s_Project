package com.example.trolls

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream


class EditPage : AppCompatActivity() {

    private lateinit var editpage_iv_profilepic: ImageView
    private lateinit var editpage_tv_myid: TextView
    private lateinit var editpage_et_myname: EditText
    private lateinit var editpage_et_nickname: EditText
    private lateinit var editpage_et_intro: EditText
    var imgList = listOf(
        R.drawable.dummy_image01,
        R.drawable.dummy_image02,
        R.drawable.dummy_image03,
        R.drawable.dummy_image04,
        R.drawable.dummy_image05,
        R.drawable.dummy_image06,
        R.drawable.dummy_image07
    )
    var imgInt = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var editpage_lo_profile = findViewById<ConstraintLayout>(R.id.editpage_lo_profile)
        editpage_iv_profilepic = findViewById<ImageView>(R.id.editpage_iv_profilepic)
        editpage_tv_myid = findViewById<TextView>(R.id.editpage_tv_myid)//아이디는 변경 불가
        editpage_et_myname = findViewById<EditText>(R.id.editpage_et_myname)
        editpage_et_nickname = findViewById<EditText>(R.id.editpage_et_nickname)

        var edit_iv_img1 = findViewById<ImageView>(R.id.edit_iv_img1).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(0))
                imgInt = 0
            }
        }
        var edit_iv_img2 = findViewById<ImageView>(R.id.edit_iv_img2).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(1))
                imgInt = 1
            }
        }
        var edit_iv_img3 = findViewById<ImageView>(R.id.edit_iv_img3).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(2))
                imgInt = 2
            }
        }
        var edit_iv_img4 = findViewById<ImageView>(R.id.edit_iv_img4).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(3))
                imgInt = 3
            }
        }
        var edit_iv_img5 = findViewById<ImageView>(R.id.edit_iv_img5).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(4))
                imgInt = 4
            }
        }
        var edit_iv_img6 = findViewById<ImageView>(R.id.edit_iv_img6).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(5))
                imgInt = 5
            }
        }
        var edit_iv_img7 = findViewById<ImageView>(R.id.edit_iv_img7).apply {
            setOnClickListener {
                editpage_iv_profilepic.setImageResource(imgList.get(6))
                imgInt = 5
            }
        }

        var editpage_lo_intro = findViewById<ConstraintLayout>(R.id.editpage_lo_intro)
        editpage_et_intro = findViewById<EditText>(R.id.editpage_et_intro)

        if (intent.getStringExtra("TARGET") == "profile") {
            editpage_iv_profilepic.setImageResource(intent.getIntExtra("IMG", 0))
            editpage_tv_myid.setText(intent.getStringExtra("ID"))
            editpage_et_myname.setText(intent.getStringExtra("NAME"))
            editpage_et_nickname.setText(intent.getStringExtra("NICK"))
            editpage_lo_intro.setVisibility(View.INVISIBLE)
            editpage_lo_profile.setVisibility(View.VISIBLE)
        } else {
            editpage_et_intro.setText(intent.getStringExtra("INTRO"))
            editpage_lo_intro.setVisibility(View.VISIBLE)
            editpage_lo_profile.setVisibility(View.INVISIBLE)
        }

        var editpage_iv_save = findViewById<ImageView>(R.id.editpage_iv_save)
        editpage_iv_save.setOnClickListener {

            var saveIntent = Intent(this, MyPage::class.java).apply {
                if (intent.getStringExtra("TARGET") == "profile") {
                    putExtra("ID", editpage_tv_myid.text)
                    putExtra("NAME", editpage_et_myname.text)
                    putExtra("NICK", editpage_et_nickname.text)
                    putExtra("IMG", imgList[imgInt])
                    putExtra("TARGET", "profile")
                } else {
                    putExtra("ID", editpage_tv_myid.text)
                    putExtra("INTRO", editpage_et_intro.text)
                    putExtra("TARGET", "intro")
                }
            }
            setResult(Activity.RESULT_OK, saveIntent)
            finish()
        }
        var editpage_iv_cancel = findViewById<ImageView>(R.id.editpage_iv_cancel)
        editpage_iv_cancel.setOnClickListener { finish() }
    }
}