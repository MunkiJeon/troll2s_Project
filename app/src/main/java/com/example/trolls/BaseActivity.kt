package com.example.trolls

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontSize(this)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun adjustFontSize(context: Context): Context {
        val settings = getSharedPreferences("preference", MODE_PRIVATE)
        val fontSizePref = settings.getString("font_size", FontSize.Normal.name)

        val configuration= this.resources.configuration
        val scale = when(fontSizePref) {
            FontSize.Normal.name -> 1.0f
            FontSize.Large.name -> 1.2f
            else -> throw Exception()
        }


        configuration.fontScale = scale

        //TODO: deprecated 된거 바꾸기
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density

        baseContext.resources.updateConfiguration(configuration, metrics);

        return context.createConfigurationContext(configuration)
    }
}