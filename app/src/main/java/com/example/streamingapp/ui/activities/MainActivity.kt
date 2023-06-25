package com.example.streamingapp.ui.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.streamingapp.R
import com.example.streamingapp.data.model.response.TestimonyCategory
import com.example.streamingapp.databinding.ActivityMainBinding
import com.example.streamingapp.ui.components.adapter.FilterUserItemAdapter
import com.example.streamingapp.ui.components.dialoges.ShowCategoryBottomSheet
import com.example.streamingapp.utils.AppDialogUtils
import com.example.streamingapp.utils.AppProgressUtils
import com.example.streamingapp.utils.showIfPossible

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var userFilterDialog: AlertDialog? = null

    private val appDialogUtil = AppDialogUtils(this)

    private val adapter by FilterUserItemAdapter.getAdapter { selectedItem ->
        userFilterDialog?.dismiss()
        Toast.makeText(this, selectedItem.category, Toast.LENGTH_SHORT).show()
        AppProgressUtils(this).showProgressOrHideIt(true)
    }

    val categoryList = listOf<TestimonyCategory>(
        TestimonyCategory(1, "Back Pain"),
        TestimonyCategory(2, "Neck Pain"),
        TestimonyCategory(3, "Thai Pain"),
        TestimonyCategory(4, "Nee Pain"),
        TestimonyCategory(5, "Throat Pain"),
        TestimonyCategory(6, "Thai Pain"),
        TestimonyCategory(7, "Neck Pain"),
        TestimonyCategory(9, "Back Pain"),
        TestimonyCategory(10, "Throat Pain")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter.submitList(categoryList)
        setUpBinding()
    }

    private fun setUpBinding() = binding.apply {

        filterBtn.setOnClickListener {
            setBottomSheet()
        }

        awardBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AwardActivity::class.java))
        }

    }

    private fun setBottomSheet() {

        if (userFilterDialog == null) {
            userFilterDialog = ShowCategoryBottomSheet.getDialog(
                appDialogUtils = appDialogUtil,
                adapter = adapter
            )
        }
        userFilterDialog?.showIfPossible(this)

    }


}