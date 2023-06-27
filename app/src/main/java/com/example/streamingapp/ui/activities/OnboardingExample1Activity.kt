package com.example.streamingapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivityOnboardingExample1Binding
import com.example.streamingapp.ui.components.adapter.OnboardingViewPagerAdapter
import com.example.streamingapp.utils.Animatoo
import com.example.streamingapp.utils.AppConstants
import com.example.streamingapp.utils.AppPreference
import com.example.streamingapp.utils.appPreference
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class OnboardingExample1Activity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var textSkip: TextView

    private lateinit var binding: ActivityOnboardingExample1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingExample1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launch {
            appPreference(this@OnboardingExample1Activity).writeString(
                AppConstants.PREF_ACCESS_TOKEN,
                "onBoarding is complete"
            )
        }

        mViewPager = binding.viewPager
        mViewPager.adapter = OnboardingViewPagerAdapter(this, this)
        TabLayoutMediator(binding.pageIndicator, mViewPager) { _, _ -> }.attach()
        textSkip = findViewById(R.id.text_skip)
        textSkip.setOnClickListener {
            finish()
            val intent =
                Intent(applicationContext, OnboardingFinishActivity::class.java)
            startActivity(intent)
            Animatoo.animateSlideLeft(this)
        }

        val btnNextStep: Button = findViewById(R.id.btn_next_step)

        btnNextStep.setOnClickListener {
            if (getItem() > mViewPager.childCount) {
                finish()
                val intent =
                    Intent(applicationContext, OnboardingFinishActivity::class.java)
                startActivity(intent)
                Animatoo.animateSlideLeft(this)
            } else {
                mViewPager.setCurrentItem(getItem() + 1, true)
            }
        }

    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }

}
