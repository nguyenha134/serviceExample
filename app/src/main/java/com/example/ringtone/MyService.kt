package com.example.ringtone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MyService : Service() {
    private val RINGTONE_ID = "ForegroundService Kotlin"

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, MyService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, MyService::class.java)
            context.stopService(stopIntent)
        }
    }

    private var player: Ringtone? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRingTone()
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, RINGTONE_ID)
            .setContentTitle("RingTone is playing...")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                RINGTONE_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private fun startRingTone() {
        val onRingTone = RingtoneManager.getActualDefaultRingtoneUri(
            applicationContext, RingtoneManager.TYPE_RINGTONE
        )
        player = RingtoneManager.getRingtone(applicationContext, onRingTone);
        player?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()

    }
}