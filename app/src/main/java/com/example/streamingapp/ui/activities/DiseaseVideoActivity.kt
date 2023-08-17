package com.example.streamingapp.ui.activities

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.TestimonyListItem
import com.example.streamingapp.data.model.response.Testimony
import com.example.streamingapp.databinding.ActivityDiseaseVideoBinding
import com.example.streamingapp.ui.components.adapter.DiseaseListAdapter
import com.example.streamingapp.utils.AppConstants.CATEGORY_FRAGMENT_CATEGORY_NAME
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class DiseaseVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseVideoBinding

    private var dialogProgress: Dialog? = null

    private val testimonies: MutableList<Testimony> = mutableListOf()

    private val diseaseAdapter by DiseaseListAdapter.getDiseaseVideoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disease_video)

        val categoryName = intent.getStringExtra(CATEGORY_FRAGMENT_CATEGORY_NAME)

        categoryName?.let { name ->
            setUpBinding(name)
            fetchTestimoniesByCategory(name)
        }
    }

    private fun setUpBinding(name: String) = binding.apply {
        categoryName.text = name
        diseaseRv.adapter = diseaseAdapter
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

                diseaseAdapter.submitList(
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