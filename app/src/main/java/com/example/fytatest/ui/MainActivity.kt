package com.example.fytatest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.fytatest.*
import com.example.fytatest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val postURL: String = R.string.URL.toString()
    private var imageData: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val resultText = findViewById<TextView>(R.id.txtResult)


        binding.imBack.setOnClickListener {
            cameraActivity()
        }
    }

    private fun cameraActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun postHttp() {


    }

    private fun getHTTP() {

    }

    private fun pickItemFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GalleryActivity.IMAGE_PICK_CODE)
    }

}