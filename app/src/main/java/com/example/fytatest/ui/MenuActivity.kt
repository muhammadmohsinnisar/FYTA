package com.example.fytatest.ui

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fytatest.R
import com.example.fytatest.databinding.ActivityMenuBinding




class MenuActivity: AppCompatActivity() {


    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
       // val splashScreen = installSplashScreen()


        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)

        }

        binding.btnGallery.setOnClickListener {
            val intent = Intent(this, SelectAndSendActivity::class.java)
            startActivity(intent)

        }

        binding.imHappyPlant.setOnClickListener {
            Toast.makeText(this,"You cant touch me :D", Toast.LENGTH_SHORT).show()

        }

        val fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        binding.imHappyPlant.animation = fade_out
        binding.btnGallery.animation = fade_out
        binding.btnCamera.animation = fade_out

    }



}