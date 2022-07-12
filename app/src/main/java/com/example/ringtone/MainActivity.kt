package com.example.ringtone

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ringtone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: AirplaneModeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MyService::class.java)
        binding.btnStart.setOnClickListener {
            intent.putExtra(Instance.PUT_EXTRA_KEY, "")
            this.startService(intent)
        }

        binding.btnStop.setOnClickListener {
            this.stopService(intent)
        }

        receiver = AirplaneModeChangeReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }

        binding.btnSendCustomBroadcast.setOnClickListener {
            val intent1 = Intent("com.example.ringtone_SEND_RECEIVER")
            intent1.putExtra(
                "com.example.ringtone_EXTRA_DATA",
                "Send data use Custom Broadcast Receiver"
            )
            sendBroadcast(intent1)
        }
    }

    override fun onStop() {
        super.onStop()
        this.unregisterReceiver(receiver)
    }
}