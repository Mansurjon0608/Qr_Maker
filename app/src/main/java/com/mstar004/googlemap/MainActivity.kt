package com.mstar004.googlemap


import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.provider.Settings
import android.view.View
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mstar004.googlemap.databinding.ActivityMapsBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
    private val CHANNEL_ID = "simple_notification"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMake.setOnClickListener {

            object : CountDownTimer(15000, 1000) {
                @SuppressLint("ResourceAsColor", "SetTextI18n")
                override fun onTick(p0: Long) {
                    binding.orderTimer.text =
                        (p0 / 1000).toString().replace(".", "")
                    when ((p0 / 1000).toInt()) {

                        10 -> {
                            binding.orderTimer.setTextColor(Color.parseColor("#EECE21"))
                        }
                        5 -> {
                            binding.orderTimer.setTextColor(Color.parseColor("#FA0606"))
                            binding.btnMake.setBackgroundResource(R.drawable.shape_red)
                        }
                    }
                }

                override fun onFinish() {
                    binding.orderTimer.visibility = View.GONE
                    binding.btnMake.setBackgroundResource(R.drawable.shape1)

                    notification()
                }

            }.start()

            val vibrator: Vibrator =
                this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(100)
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

    private fun notification() {

        val resultIntent = Intent(this, MainActivity2::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }


        val builder = NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
        builder.setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_timer_24)
            .setContentTitle("QR maker")
            .setContentText("QR image is done")
            .setCategory(NotificationCompat.EXTRA_TEXT)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setContentIntent(resultPendingIntent)
            .setVibrate(longArrayOf(0, 1000, 1000, 1000))


        val notifyManagerCompat = NotificationManagerCompat.from(this@MainActivity)
        notifyManagerCompat.notify(NOTIFICATION_ID, builder.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val name: CharSequence = "Simple"
            val description = "Include all the simple notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }


    }


}
