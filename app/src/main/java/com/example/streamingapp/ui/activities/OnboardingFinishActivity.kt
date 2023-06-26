package com.example.streamingapp.ui.activities


import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.streamingapp.databinding.ActivityOnboardingFinishBinding

class OnboardingFinishActivity : AppCompatActivity() {
    private lateinit var btnStart: LinearLayout

    private lateinit var binding: ActivityOnboardingFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingFinishBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        btnStart = binding.layoutStart
        btnStart.setOnClickListener {
            startActivity(Intent(this@OnboardingFinishActivity, MainActivity::class.java))
            finishAffinity()
        }
    }
}
