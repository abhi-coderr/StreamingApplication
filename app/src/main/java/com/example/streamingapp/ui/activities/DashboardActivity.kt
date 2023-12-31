package com.example.streamingapp.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.streamingapp.R
import com.example.streamingapp.databinding.ActivityDashboardBinding
import com.example.streamingapp.ui.components.adapter.TabPagerAdapter
import com.example.streamingapp.ui.fragments.AwardsFragment
import com.example.streamingapp.ui.fragments.CategoryFragment
import com.example.streamingapp.utils.AppDialogUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val appDialogUtil = AppDialogUtils(this)

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        window.statusBarColor = R.color.white
        setUpViewPager()

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        appDialogUtil.getExitDialog().show()
    }

    private fun setUpViewPager() = binding.apply {
        val fragmentList = arrayListOf(
            CategoryFragment(),
            AwardsFragment()
        )
        viewPager.adapter = TabPagerAdapter(this@DashboardActivity, fragmentList)
        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Category"
                1 -> tab.text = "Gallery"
            }
        }.attach()
    }

}