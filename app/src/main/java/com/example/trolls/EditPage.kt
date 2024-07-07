package com.example.trolls

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView


class EditPage : AppCompatActivity() {

    private lateinit var editpage_iv_profilepic: ImageView
    private lateinit var editpage_tv_myid: TextView
    private lateinit var editpage_et_myname: EditText
    private lateinit var editpage_et_nickname: EditText
    private lateinit var editpage_et_intro: EditText
    var imgList = listOf(
        R.drawable.profile01,
        R.drawable.profile02,
        R.drawable.profile03,
        R.drawable.profile04,
        R.drawable.profile05,
        R.drawable.profile06,
        R.drawable.profile07,
        R.drawable.profile08,
        R.drawable.profile09,
        R.drawable.profile10,
        R.drawable.profile11,
        R.drawable.profile12,
    )

    private fun convertDpToPixel(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            this.resources.displayMetrics
        ).toInt()
    }

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
        var imgInt = intent.getIntExtra("IMG",0)

//        var editpage_lo_profile = findViewById<ConstraintLayout>(R.id.editpage_lo_profile)
        var editpage_lo_profileconstraint = findViewById<ConstraintLayout>(R.id.editpage_lo_profileconstraint)
        var editpage_lo_imgoptions = findViewById<ConstraintLayout>(R.id.editpage_lo_imgoptions)
        var editpage_lo_optionslist = findViewById<GridLayout>(R.id.editpage_lo_optionslist)
        var editpage_lo_profilescroll = findViewById<ScrollView>(R.id.editpage_lo_profilescroll)

        editpage_iv_profilepic = findViewById<ImageView>(R.id.editpage_iv_profilepic)
        editpage_tv_myid = findViewById<TextView>(R.id.editpage_tv_myid)//아이디는 변경 불가
        editpage_et_myname = findViewById<EditText>(R.id.editpage_et_myname)
        editpage_et_nickname = findViewById<EditText>(R.id.editpage_et_nickname)

        editpage_iv_profilepic.setOnClickListener{
            editpage_lo_imgoptions.visibility = View.VISIBLE
        }

        fun proflieSet(imgNum: Int) {
            Glide.with(this)
                .load(imgList.get(imgNum))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(90)))
                .into(editpage_iv_profilepic)
            imgInt = imgNum
            editpage_lo_profilescroll.fullScroll(ScrollView.FOCUS_UP-70)
            editpage_lo_imgoptions.visibility = View.INVISIBLE
        }
        for ((index, img) in imgList.withIndex()) {
            val circleView = CircleImageView(this)
            circleView.setImageResource(img) // drawable id
            circleView.setOnClickListener{ proflieSet(index) }
            val circleView_params = LinearLayout.LayoutParams(
                convertDpToPixel(90), // width
                convertDpToPixel(90) // height
            )
            circleView_params.marginEnd = convertDpToPixel(15)
            circleView.layoutParams = circleView_params
            editpage_lo_optionslist.addView(circleView)
        }

        var editpage_lo_intro = findViewById<ConstraintLayout>(R.id.editpage_lo_intro)
        editpage_et_intro = findViewById<EditText>(R.id.editpage_et_intro)

        if (intent.getStringExtra("TARGET") == "profile") {
            Glide.with(this)
                .load(intent.getIntExtra("IMG",0))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(90)))
                .into(editpage_iv_profilepic)
            editpage_tv_myid.setText(intent.getStringExtra("ID"))
            editpage_et_myname.setText(intent.getStringExtra("NAME"))
            editpage_et_nickname.setText(intent.getStringExtra("NICK"))
            editpage_lo_intro.setVisibility(View.INVISIBLE)
            editpage_lo_profileconstraint.setVisibility(View.VISIBLE)
        } else {
            editpage_et_intro.setText(intent.getStringExtra("INTRO"))
            editpage_lo_intro.setVisibility(View.VISIBLE)
            editpage_lo_profileconstraint.setVisibility(View.INVISIBLE)
        }

        var editpage_iv_save = findViewById<ImageView>(R.id.editpage_iv_save)
        editpage_iv_save.setOnClickListener {

            var saveIntent = Intent(this, MyPageActivity::class.java).apply {
                if (intent.getStringExtra("TARGET") == "profile") {
                    putExtra("ID", editpage_tv_myid.text)
                    putExtra("NAME", editpage_et_myname.text.toString())
                    putExtra("NICK", editpage_et_nickname.text.toString())
                    if (imgInt >= imgList.size) { putExtra("IMG", imgInt) }
                    else { putExtra("IMG", imgList[imgInt]) }
                    putExtra("TARGET", "profile")
                } else {
                    putExtra("ID", editpage_tv_myid.text)
                    putExtra("INTRO", editpage_et_intro.text.toString())
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
