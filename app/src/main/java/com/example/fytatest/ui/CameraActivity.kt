package com.example.fytatest.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fytatest.Constants
import com.example.fytatest.R
import com.example.fytatest.databinding.ActivityCameraBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity(){

    private lateinit var outputDirectory: File
    private var imageCapture: ImageCapture? = null
    private lateinit var  binding: ActivityCameraBinding
    private lateinit var cameraExecutor: ExecutorService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionsGranted()){
            Toast.makeText(this, "Camera Allowed", Toast.LENGTH_SHORT).show()
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this,
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSION
            )
        }

        binding.btnClick.setOnClickListener {
            captureImage()
        }

        binding.btnCamgallery.setOnClickListener{
            val intent = Intent(this, SelectAndSendActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getOutputDirectory(): File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let { mFile->File(mFile,resources.getString(
            R.string.app_name
        )).apply {
            mkdirs()
        }}
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else filesDir
        }


    private fun captureImage(){
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(Constants.FILE_NAME_FORMAT, Locale.getDefault()).format(System.currentTimeMillis()) + ".jpg")

        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOption,ContextCompat.getMainExecutor(this),
        object :ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
               val savedUri = Uri.fromFile(photoFile)
                val msg = "Photo Saved!"
                Toast.makeText(this@CameraActivity, "Picture saved in ${savedUri}", Toast.LENGTH_SHORT).show()

            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(Constants.TAG, "onError: ${exception.message}", exception)
            }
        })
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.viewFinder.surfaceProvider
                    )
                }
            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            }catch (e: Exception){
                Log.d(Constants.TAG, "startCamera Failed!", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if(requestCode == Constants.REQUEST_CODE_PERMISSION){
            if(allPermissionsGranted()){
                startCamera()
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
                finish()
            }


        }
    }

    private fun allPermissionsGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
