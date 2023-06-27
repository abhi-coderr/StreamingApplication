package com.example.streamingapp.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivitySplashScreenBinding
import com.example.streamingapp.utils.AppConstants
import com.example.streamingapp.utils.AppPreference
import com.example.streamingapp.utils.appPreference
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        lifecycleScope.launch {


            val shouldGoToDashboard = appPreference(this@SplashScreenActivity).readString(AppConstants.PREF_ACCESS_TOKEN)
                    .isNotBlank()

            if (shouldGoToDashboard) {
                Handler().postDelayed({
                    // Intent is used to switch from one activity to another.
                    val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(i) // invoke the SecondActivity.
                    finish() // the current activity will get finished.
                }, 3000)
            } else {
                Handler().postDelayed({
                    // Intent is used to switch from one activity to another.
                    val i =
                        Intent(this@SplashScreenActivity, OnboardingExample1Activity::class.java)
                    startActivity(i) // invoke the SecondActivity.
                    finish() // the current activity will get finished.
                }, 3000)
            }

        }


    }
}