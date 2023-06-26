package com.example.streamingapp.ui.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.TestimonyListItem
import com.example.streamingapp.data.model.response.Testimony
import com.example.streamingapp.data.model.response.TestimonyCategory
import com.example.streamingapp.databinding.ActivityMainBinding
import com.example.streamingapp.ui.components.adapter.FilterUserItemAdapter
import com.example.streamingapp.ui.components.adapter.TestimonyListAdapter
import com.example.streamingapp.ui.components.dialoges.ShowCategoryBottomSheet
import com.example.streamingapp.ui.viewmodels.MainViewModel
import com.example.streamingapp.utils.AppDialogUtils
import com.example.streamingapp.utils.AppProgressUtils
import com.example.streamingapp.utils.setSafeOnClickListener
import com.example.streamingapp.utils.showIfPossible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var userFilterDialog: AlertDialog? = null

    private lateinit var mainViewModel: MainViewModel

    var isLoader: Boolean = true

    private val testimonies: MutableList<Testimony> = mutableListOf()

    private val appProgressUtils = AppProgressUtils(this)

    private val appDialogUtil = AppDialogUtils(this)

    private var dialogProgress: Dialog? = null

    private val adapter by FilterUserItemAdapter.getAdapter { selectedItem ->
        userFilterDialog?.dismiss()
        testimonies.clear()
        fetchTestimoniesByCategory(selectedItem.category)
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
//        setUpViewModel()
        setUpBinding()

        binding.customToolBar.setSafeOnClickListener {
            startActivity(Intent(this@MainActivity, VideoPlayerActivity::class.java))
        }

        fetchAllData()

        adapter.submitList(categoryList)

    }

    private fun setUpViewModel() = binding.apply {

        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]

        mainViewModel.isProgressLoad.observe(this@MainActivity, Observer { isLoad ->
            AppProgressUtils(this@MainActivity).showProgressOrHideIt(isLoad)
        })

        mainViewModel.testimonyResponse.observe(this@MainActivity, Observer { response ->
            var uniqueId = 0
            if (response.isEmpty()) {
                isListEmpty(true)
            } else {
                adapterTestimony.submitList(response.map {
                    TestimonyListItem(
                        ++uniqueId,
                        it.testimonyName,
                        it.testimonyUrl,
                        it.testimonyCategory
                    )
                })
            }
        })

    }

    private fun setUpBinding() = binding.apply {
        testimonyRv.adapter = adapterTestimony
        filterBtn.setSafeOnClickListener {
            setBottomSheet()
        }
        awardBtn.setSafeOnClickListener {
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

    private fun fetchTestimoniesByCategory(targetCategory: String) {

        showProgressOrHideIt(true)

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

                showProgressOrHideIt(false)

                if (testimonies.isEmpty()) {
                    isListEmpty(true)
                } else {
                    isListEmpty(false)
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
                showProgressOrHideIt(false)
            }
        })

        showProgressOrHideIt(false)

        if (testimonies.isEmpty()) {
            isListEmpty(true)
        } else {
            isListEmpty(false)
        }

    }

    private fun fetchAllData() {

        showProgressOrHideIt(true)

        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val query: Query = database.child("Video")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (testimonySnapshot in dataSnapshot.children) {
                    val testimony = testimonySnapshot.getValue(Testimony::class.java)
                    if (testimony != null) {
                        testimonies.add(testimony)
                    }
                }

                showProgressOrHideIt(false)

                if (testimonies.isEmpty()) {
                    isListEmpty(true)
                } else {
                    isListEmpty(false)
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
                showProgressOrHideIt(false)
            }
        })

        showProgressOrHideIt(false)

        if (testimonies.isEmpty()) {
            isListEmpty(true)
        } else {
            isListEmpty(false)
        }

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

    fun isListEmpty(isEmpty: Boolean) = binding.apply {
        noDataAnim.apply {
            /**
             * Setting up the animation type.
             */
            setAnimation("not_found.json")
            /**
             * Setting up the repeat count.
             */
            repeatCount = LottieDrawable.INFINITE
            /**
             * Playing the progress animation.
             */
            playAnimation()
            this.isVisible = isEmpty
        }
    }

    fun showProgressOrHideIt(showProgress: Boolean) {
        if (showProgress) {
            if (!this.isFinishing && !this.isDestroyed && (dialogProgress == null || !dialogProgress!!.isShowing)) {
                /**
                 * Initializing the dialog.
                 */
                dialogProgress = Dialog(this)
                /**
                 * Setting up the dialog.
                 */
                dialogProgress?.apply {
                    /**
                     * Requesting widow.
                     */
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    /**
                     * Making progress not cancelable.
                     */
                    setCancelable(false)
                    /**
                     * Setting up the dialog layout.
                     */
                    setContentView(R.layout.raw_progress_layout)
                    /**
                     * Setting up the transparent background.
                     */
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    /**
                     * Setting up the lottie animation and showing it.
                     */
                    val mLottieAnimationView: LottieAnimationView = findViewById(
                        R.id.raw_progress_layout_animationview
                    )
                    mLottieAnimationView.apply {
                        /**
                         * Getting image from the assets folder.
                         */
                        imageAssetsFolder = "images/"
                        /**
                         * Setting up the animation type.
                         */
                        setAnimation("loader.json")
                        /**
                         * Setting up the repeat count.
                         */
                        repeatCount = LottieDrawable.INFINITE
                        /**
                         * Playing the progress animation.
                         */
                        playAnimation()
                    }
                    /**
                     * Showing the dialog.
                     */
                    if (!isShowing) show()
                }
            }
        } else {
            /**
             * Hiding the progress.
             */
            dialogProgress?.apply {
                if (isShowing) {
                    dismiss()
                }
            }

            /**
             * Resetting the dialog.
             */
            dialogProgress = null
        }
    }

}