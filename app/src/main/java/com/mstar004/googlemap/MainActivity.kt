package com.mstar004.googlemap


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
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

            object : CountDownTimer(15000, 1000){
                @SuppressLint("ResourceAsColor", "SetTextI18n")
                override fun onTick(p0: Long) {

                    binding.orderTimer.text =
                        (p0 / 1000).toString().replace(".", "")
                    when((p0/1000).toInt()){

                        10->{
                            binding.orderTimer.setTextColor(Color.parseColor("#EECE21"))
                        }
                        5-> {
                            binding.orderTimer.setTextColor(Color.parseColor("#FA0606"))
                            binding.btnMake.setBackgroundResource(R.drawable.shape_red)
                        }
                    }
                }

                override fun onFinish() {
                    binding.btnMake.isEnabled = false
                    binding.orderTimer.visibility = View.GONE
                    binding.btnMake.setBackgroundResource(R.drawable.shape1)
                }

            }.start()

            val vibrator: Vibrator =
                this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(200)
            }

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
