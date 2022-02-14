package com.mstar004.googlemap


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.mstar004.googlemap.databinding.ActivityMapsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMake.setOnClickListener {
            val json = binding.editText.text.toString().trim()
            val qrgEncoder = QRGEncoder(json, null, QRGContents.Type.TEXT, 500)
            qrgEncoder.colorBlack = Color.GREEN
            qrgEncoder.colorWhite = Color.WHITE
            findViewById<AppCompatImageView>(R.id.imgQrCode).setImageBitmap(qrgEncoder.bitmap)
            findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.cons_img).visibility =
                View.VISIBLE
            findViewById<AppCompatTextView>(R.id.txt).text = binding.editText.text.toString().trim()
        }
    }
}