package com.example.streamingapp.ui.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.TestimonyListItem
import com.example.streamingapp.data.model.response.Testimony
import com.example.streamingapp.data.model.response.TestimonyCategory
import com.example.streamingapp.databinding.ActivityMainBinding
import com.example.streamingapp.ui.components.adapter.FilterUserItemAdapter
import com.example.streamingapp.ui.components.adapter.TestimonyListAdapter
import com.example.streamingapp.ui.components.dialoges.ShowCategoryBottomSheet
import com.example.streamingapp.utils.AppDialogUtils
import com.example.streamingapp.utils.AppProgressUtils
import com.example.streamingapp.utils.VideoFetcher
import com.example.streamingapp.utils.showIfPossible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var userFilterDialog: AlertDialog? = null

    val videoFetcher = VideoFetcher()

    private val appDialogUtil = AppDialogUtils(this)

    private val adapter by FilterUserItemAdapter.getAdapter { selectedItem ->
        userFilterDialog?.dismiss()

//        setData()

        lifecycleScope.launch(Dispatchers.IO) {
            fetchTestimoniesByCategory("First Category")
        }
//        Toast.makeText(this, selectedItem.category, Toast.LENGTH_SHORT).show()
//        AppProgressUtils(this).showProgressOrHideIt(true)
    }

    private val adapterTestimony by TestimonyListAdapter.getTestimonyAdapter()

    private val categoryList = listOf<TestimonyCategory>(
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

        testimonyRv.adapter = adapterTestimony

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


    private fun fetchVideosByCategory(targetCategory: String) {
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val query: Query =
            database.child("Videos").orderByChild("testimonyCategory").equalTo(targetCategory)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (videoSnapshot in dataSnapshot.children) {
                    val video = videoSnapshot.getValue(Testimony::class.java)
                    if (video != null) {
                        val urlDetail = video.testimonyUrl
                        val category = video.testimonyCategory
                        val detail = video.testimonyName

                        if (urlDetail != null) {
                            Log.i("TestimonyData------->>", urlDetail)
                        }
                        if (category != null) {
                            Log.i("TestimonyData------->>", category)
                        }
                        if (detail != null) {
                            Log.i("TestimonyData------->>", detail)
                        }

                        println("Title: $detail")
                        println("Category: $category")
                        println("URL Detail: $urlDetail")
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }

    private suspend fun fetchTestimoniesByCategory(targetCategory: String) {
//        AppProgressUtils(this).showProgressOrHideIt(true)
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val query: Query =
            database.child("Video").orderByChild("testimonyCategory").equalTo(targetCategory)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val testimonies: MutableList<Testimony> = mutableListOf()

                for (testimonySnapshot in dataSnapshot.children) {
                    val testimony = testimonySnapshot.getValue(Testimony::class.java)
                    if (testimony != null) {
                        testimonies.add(testimony)
                    }
//
//
//                    val responseList = listOf(
//                        TestimonyListItem(
//                            ++uniqueId,
//                            testimony?.testimonyName,
//                            testimony?.testimonyUrl,
//                            testimony?.testimonyCategory
//                        )
//                    )
//
//                    adapterTestimony.submitList(responseList)
//
//                    if (testimony != null) {
//                        val testimonyCategory = testimony.testimonyCategory
//                        val testimonyName = testimony.testimonyName
//                        val testimonyUrl = testimony.testimonyUrl
//
//                        if (testimonyCategory != null) {
//                            Log.i("TestimonyData------->>", testimonyCategory)
//                        }
//                        if (testimonyName != null) {
//                            Log.i("TestimonyData------->>", testimonyName)
//                        }
//                        if (testimonyUrl != null) {
//                            Log.i("TestimonyData------->>", testimonyUrl)
//                        }
//
//                        println("Testimony Category: $testimonyCategory")
//                        println("Testimony Name: $testimonyName")
//                        println("Testimony URL: $testimonyUrl")
                }

                Log.i("list-------->", testimonies.toString())

                var uniqueId = 0

                adapterTestimony.submitList(
                    testimonies.map { testimony ->
                        TestimonyListItem(
                            ++uniqueId,
                            testimony.testimonyName,
                            testimony.testimonyUrl,
                            testimony.testimonyCategory
                        )
                    }
                )

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }

    fun setData() {
        videoFetcher.fetchVideosByCategory("First Category") { videoUri, exception ->
            if (exception != null) {
                // Handle the error
                println("An error occurred: ${exception.message}")
            } else {
                // Process the video URLs

                if (!videoUri.isNullOrEmpty()) {

                    var uniqueValue = 0

                    for (url in videoUri) {
                        println(url) // or do something else with the URLs
                        Log.i("testimony___data--->", url.toString())
                    }
                } else {
                    // No videos found for the specified category
                    println("No videos found for the specified category.")
                }
            }
        }
    }


}