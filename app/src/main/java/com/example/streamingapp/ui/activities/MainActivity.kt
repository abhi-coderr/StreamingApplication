package com.example.streamingapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpBinding()
    }

    private fun setUpBinding() = binding.apply {

        filterBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "Filter BottomSheet", Toast.LENGTH_SHORT).show()
        }

        awardBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AwardActivity::class.java))
            Toast.makeText(this@MainActivity, "Navigate to award screen", Toast.LENGTH_SHORT).show()
        }

    }

}