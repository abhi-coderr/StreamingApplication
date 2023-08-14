package com.example.streamingapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivityCategoriesBinding
import com.example.streamingapp.utils.AppConstants
import com.example.streamingapp.utils.appPreference
import kotlinx.coroutines.launch

class UpdatedSplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_categories)

        lifecycleScope.launch {


            val shouldGoToDashboard = appPreference(this@UpdatedSplashActivity).readString(
                AppConstants.PREF_ACCESS_TOKEN
            )
                .isNotBlank()

            if (shouldGoToDashboard) {
                Handler().postDelayed({
                    // Intent is used to switch from one activity to another.
                    val i = Intent(this@UpdatedSplashActivity, CategoryActivity::class.java)
                    startActivity(i) // invoke the SecondActivity.
                    finish() // the current activity will get finished.
                }, 2500)
            } else {
                Handler().postDelayed({
                    // Intent is used to switch from one activity to another.
                    val i =
                        Intent(this@UpdatedSplashActivity, OnboardingExample1Activity::class.java)
                    startActivity(i) // invoke the SecondActivity.
                    finish() // the current activity will get finished.
                }, 2500)
            }

        }

    }
}