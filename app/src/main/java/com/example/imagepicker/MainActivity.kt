package com.example.imagepicker

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var btnChoosePhoto: Button
    private lateinit var previewImage: ImageView
    private lateinit var btnCaptureImage: Button
    private lateinit var imageUri: Uri


    private val photoChooseContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let {
                previewImage.setImageURI(it)
            }
        }

    private val photoCaptureContracts =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            previewImage.setImageURI(null)
            previewImage.setImageURI(imageUri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        btnChoosePhoto = findViewById(R.id.btn_choose_photo)
        previewImage = findViewById(R.id.imageview)
        btnCaptureImage = findViewById(R.id.btn_capture_photo)
        imageUri = createImageUri()!!
        btnChoosePhoto.setOnClickListener {
            photoChooseContract.launch("image/*")
        }

        btnCaptureImage.setOnClickListener {
        photoCaptureContracts.launch(imageUri)
        }


    }

    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            applicationContext,
            "com.example.imagepicker.fileProvider",
            image
        )
    }
}