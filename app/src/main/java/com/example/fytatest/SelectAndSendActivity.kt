package com.example.fytatest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.fytatest.databinding.ActivityMenuBinding
import com.example.fytatest.databinding.ActivityPictureselectBinding
import com.example.fytatest.databinding.ActivitySplashBinding
import com.example.fytatest.service.APIService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.IOException



class SelectAndSendActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictureselectBinding
    private lateinit var imageView: ImageView
    private lateinit var imageButton: Button
    private lateinit var sendButton: Button
    private var imageData: ByteArray? = null
    private val postURL: String = "https://example.identification.service?api-key=2b10LPPlQzCC2m7EXa2lAV380"

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureselectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = findViewById(R.id.imageView)

        imageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            launchGallery()
        }
        sendButton = findViewById(R.id.sendButton)
        sendButton.setOnClickListener {

            uploadImage()
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

   /* private fun uploadImage() {
        imageData?: return
        val request = object : VolleyFileUploadRequest(
            Method.POST,
            postURL,
            Response.Listener {
                println("response is: $it")
            },
            Response.ErrorListener {
                println("error is: $it")
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                var params = HashMap<String, FileDataPart>()
                params["imageFile"] = FileDataPart("image", imageData!!, "jpeg")
                return params
            }
        }
        Volley.newRequestQueue(this).add(request)
    }*/

    private suspend fun uploadImage(uri: Uri){
        lifecycleScope.launch {
            val stream = contentResolver.openInputStream(uri) ?: return@launch
            val request = UploadStream("image/*", stream, onUploadProgress = {
                Log.d("MyActivity", "On upload progress $it")
                //viewBinding.progressView.progress = it // Some ProgressBar
            })
            val filePart = MultipartBody.Part.createFormData(
                "file",
                "test.jpg",
                request
            )
            try {
                val service: APIService
                service.uploadFile(filePart)
            }
            catch(e: Exception) { // if something happens to the network
             //   Toast.makemakeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                return@launch
            }
            Log.d("MyActivity", "On finish upload file")
       }
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {
            imageData = it.readBytes()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                imageView.setImageURI(uri)
                createImageData(uri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}