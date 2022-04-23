package com.example.fytatest.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.fytatest.R
import com.example.fytatest.databinding.ActivitySplashBinding


public const val SPLASH_TIME = 3000L


class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finish()
            },
            SPLASH_TIME
        )

        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_anim)

        binding.splashPlant.animation = fadeInAnim


    }
}