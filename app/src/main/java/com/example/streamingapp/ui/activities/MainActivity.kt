package com.example.streamingapp.ui.activities

import android.app.AlertDialog
import android.content.Context
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

    val testimonies: MutableList<Testimony> = mutableListOf()

    val videoFetcher = VideoFetcher()

    private val appDialogUtil = AppDialogUtils(this)

    private val adapter by FilterUserItemAdapter.getAdapter { selectedItem ->
        userFilterDialog?.dismiss()
        testimonies.clear()
        lifecycleScope.launch(Dispatchers.IO) {
            fetchTestimoniesByCategory(selectedItem.category)
        }
    }

    private val adapterTestimony by TestimonyListAdapter.getTestimonyAdapter(onShareListener = {
        shareVideo(this@MainActivity, it.url.orEmpty(), it.detail.orEmpty())
    })

    private val categoryList = listOf<TestimonyCategory>(
        TestimonyCategory(1, "First Category"),
        TestimonyCategory(2, "Second Category"),
        TestimonyCategory(3, "Third Category"),
        TestimonyCategory(4, "Fourth Category"),
        TestimonyCategory(5, "Fifth Category"),
        TestimonyCategory(6, "Sixth Category"),
        TestimonyCategory(7, "Seventh Category"),
        TestimonyCategory(9, "UP to The 30")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adapter.submitList(categoryList)
        setUpBinding()
        fetchAllData()
    }

    private fun setUpBinding() = binding.apply {
        testimonyRv.adapter = adapterTestimony
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

    private suspend fun fetchTestimoniesByCategory(targetCategory: String) {

        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val query: Query =
            database.child("Video").orderByChild("testimonyCategory").equalTo(targetCategory)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (testimonySnapshot in dataSnapshot.children) {
                    val testimony = testimonySnapshot.getValue(Testimony::class.java)
                    if (testimony != null) {
                        testimonies.add(testimony)
                    }
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

    private fun fetchAllData() {
        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val query: Query =
            database.child("Video")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (testimonySnapshot in dataSnapshot.children) {
                    val testimony = testimonySnapshot.getValue(Testimony::class.java)
                    if (testimony != null) {
                        testimonies.add(testimony)
                    }
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

    private fun shareVideo(context: Context, videoUrl: String, videoTitle: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out this amazing experience testimony: $videoTitle\n\n$videoUrl"
        )
        context.startActivity(Intent.createChooser(shareIntent, "Share Video"))
    }

}