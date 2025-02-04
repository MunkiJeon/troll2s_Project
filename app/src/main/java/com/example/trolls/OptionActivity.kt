package com.example.trolls

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.fonts.Font
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale


class OptionActivity : AppCompatActivity() {
    private lateinit var languageBox: ImageButton
    private lateinit var themeBox: ImageButton
    private lateinit var fontSizeBox: ImageButton
    private lateinit var fontBox: ImageButton

    private lateinit var languageDetailBox: ConstraintLayout
    private lateinit var themeDetailBox: ConstraintLayout
    private lateinit var fontSizeDetailBox: ConstraintLayout
    private lateinit var fontDetailBox: ConstraintLayout

    private lateinit var languageKoreanBtn: Button
    private lateinit var languageEnglishBtn: Button

    private lateinit var themeLightBtn: Button
    private lateinit var themeDarkBtn: Button

    private lateinit var fontSizeNormalBtn: Button
    private lateinit var fontSizeLargeBtn: Button

    private lateinit var fontABtn: Button
    private lateinit var fontBBtn: Button

    private lateinit var configuration: Configuration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_option)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configuration  = Configuration()

        languageBox = findViewById(R.id.change_language_bt)
        themeBox = findViewById(R.id.change_theme_bt)
        fontSizeBox = findViewById(R.id.change_font_size_bt)
        fontBox = findViewById(R.id.change_font_bt)

        languageDetailBox = findViewById(R.id.change_language_detail)
        languageKoreanBtn = findViewById(R.id.change_language_korean_button)
        languageEnglishBtn = findViewById(R.id.change_language_english_button)
        languageDetailBox.visibility = View.GONE

        themeDetailBox = findViewById(R.id.change_theme_detail)
        themeLightBtn = findViewById(R.id.change_theme_light_bn)
        themeDarkBtn = findViewById(R.id.change_theme_dark_bn)
        themeDetailBox.visibility = View.GONE

        fontSizeDetailBox = findViewById(R.id.change_font_size_detail)
        fontSizeNormalBtn = findViewById(R.id.change_font_size_normal_bn)
        fontSizeLargeBtn= findViewById(R.id.change_font_size_large_bn)
        fontSizeDetailBox.visibility = View.GONE

        fontDetailBox = findViewById(R.id.change_font_detail)
        fontABtn = findViewById(R.id.change_font_a_bn)
        fontBBtn = findViewById(R.id.change_font_b_bn)
        fontDetailBox.visibility = View.GONE

        setBoxClickListener()
        setDetailBoxClickListener()
    }

    private fun setBoxClickListener() {
        languageBox.setOnClickListener {
            if(languageDetailBox.visibility == View.VISIBLE) {
                languageDetailBox.visibility = View.GONE
                languageBox.animate().setDuration(200).rotation(0f)
            } else {
                languageDetailBox.visibility = View.VISIBLE
                languageBox.animate().setDuration(200).rotation(180f)
            }
        }

        themeBox.setOnClickListener {
            if(themeDetailBox.visibility == View.VISIBLE) {
                themeDetailBox.visibility = View.GONE
                themeBox.animate().setDuration(200).rotation(0f)
            } else {
                themeDetailBox.visibility = View.VISIBLE
                themeBox.animate().setDuration(200).rotation(180f)
            }
        }

        fontSizeBox.setOnClickListener {
            if(fontSizeDetailBox.visibility == View.VISIBLE) {
                fontSizeDetailBox.visibility = View.GONE
                fontSizeBox.animate().setDuration(200).rotation(0f)
            } else {
                fontSizeDetailBox.visibility = View.VISIBLE
                fontSizeBox.animate().setDuration(200).rotation(180f)
            }
        }

        fontBox.setOnClickListener {
            if(fontDetailBox.visibility == View.VISIBLE) {
                fontDetailBox.visibility = View.GONE
                fontBox.animate().setDuration(200).rotation(0f)
            } else {
                fontDetailBox.visibility = View.VISIBLE
                fontBox.animate().setDuration(200).rotation(180f)
            }
        }
    }

    private fun setDetailBoxClickListener() {
        languageKoreanBtn.setOnClickListener {
            Log.d("Option", "Click languageKoreanBtn")

            val appLocale = LocaleListCompat.forLanguageTags("ko")
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
        languageEnglishBtn.setOnClickListener {
            Log.d("Option", "Click languageEnglishBtn")

            val appLocale = LocaleListCompat.forLanguageTags("en")
            AppCompatDelegate.setApplicationLocales(appLocale)
        }

        themeLightBtn.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        themeDarkBtn.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        fontSizeNormalBtn.setOnClickListener {
            adjustFontSize(FontSize.Normal)
            recreate()
        }
        fontSizeLargeBtn.setOnClickListener {
            adjustFontSize(FontSize.Large)
            recreate()
        }

        fontABtn.setOnClickListener {

        }
        fontBBtn.setOnClickListener {

        }
    }
    private fun adjustFontSize(fontSize: FontSize) {
        val configuration= this.resources.configuration
        val scale = when(fontSize.name) {
            FontSize.Normal.name -> 1.0f
            FontSize.Large.name -> 1.2f
            else -> throw Exception()
        }

        configuration.fontScale = scale

        //TODO: deprecated 된거 바꾸기
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density

        baseContext.resources.updateConfiguration(configuration, metrics)
    }
}