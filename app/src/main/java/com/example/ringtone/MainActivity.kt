package com.example.ringtone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ringtone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, MyService::class.java)
        val message: String = String()
        binding.btnStart.setOnClickListener {
            MyService.startService(this, message)
        }

        binding.btnStop.setOnClickListener {
          MyService.stopService(this)
        }
    }


}