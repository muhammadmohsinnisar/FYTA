package com.example.fytatest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.fytatest.databinding.ActivityGalleryBinding

class GalleryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    private var imageData: ByteArray? = null


    companion object {
        val IMAGE_PICK_CODE = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = findViewById(R.id.im_plant)
        val imPlant = binding.imPlant

        binding.btnGallery.setOnClickListener {
            pickItemFromGallery()

        }

        binding.btnSearch.setOnClickListener {
            postImagetoAPI()

        }
    }

    private fun postImagetoAPI() {
        val intent = Intent(this, MainActivity::class.java)

    }

    private fun pickItemFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView.setImageURI(data?.data)
        //createImageData(data?.data)
       //val uri = Uri.fromFile()

    }


    private fun createImageData(data: Uri?) {
       // val inputStream = contentResolver.openInputStream(uri)
       // inputStream?.buffered()?.use {
        //    imageData = it.readBytes()
        }
    }



//}