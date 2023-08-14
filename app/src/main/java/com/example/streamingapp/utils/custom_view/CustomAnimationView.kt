package com.example.streamingapp.utils.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.airbnb.lottie.LottieDrawable
import com.example.streamingapp.R
import com.example.streamingapp.databinding.CustomAnimationLayoutBinding

class CustomAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    /**
     * Private Binding Variable.
     */
    private var _binding: CustomAnimationLayoutBinding? = null


    init {
        var loopInfinite = false
        var startWithFadeIn = false
        var imageAssetsFolder = ""
        var animationJson = ""

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomAnimationView,
                defStyleAttr,
                0
            )

            loopInfinite =
                typedArray.getBoolean(R.styleable.CustomAnimationView_loopInfinite, false)
            imageAssetsFolder =
                typedArray.getString(R.styleable.CustomAnimationView_imageAssetsFolder).orEmpty()
            animationJson =
                typedArray.getString(R.styleable.CustomAnimationView_animationJson).orEmpty()
            startWithFadeIn =
                typedArray.getBoolean(R.styleable.CustomAnimationView_startWithFadeIn, false)


            typedArray.recycle()
        }


        /**
         * Creating inflater from the context
         */
        val inflater = LayoutInflater.from(context)

        /**
         * Inflating the layout and creating data binding for setting up the layout.
         */
        _binding = CustomAnimationLayoutBinding.inflate(inflater, this, true)

        _binding?.customAnimationView?.apply {
            this.imageAssetsFolder = imageAssetsFolder
            setAnimation(animationJson)
            repeatCount = if (loopInfinite) LottieDrawable.INFINITE else 0

            if (startWithFadeIn) {
                alpha = 0f
                playAnimation()
                animate().alpha(1f).duration = 1000
            } else {
                playAnimation()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        /**
         * Freeing up the binding variable.
         */
        _binding = null
    }
//
//    private val lottieAnimationView: LottieAnimationView
//
//    init {
//        LayoutInflater.from(context).inflate(R.layout.custom_animation_layout, this, true)
//        lottieAnimationView = findViewById(R.id.customAnimationView)
//        attrs?.let {
//            val typedArray = context.obtainStyledAttributes(
//                attrs,
//                R.styleable.CustomAnimationView,
//                defStyleAttr,
//                0
//            )
//
//            val loopInfinite =
//                typedArray.getBoolean(R.styleable.CustomAnimationView_loopInfinite, false)
//            val shouldStartWithFadeIn =
//                typedArray.getBoolean(R.styleable.CustomAnimationView_startWithFadeIn, false)
//
//            typedArray.recycle()
//
//            // Set loop count
//            lottieAnimationView.repeatCount = if (loopInfinite) LottieDrawable.INFINITE else 0
//
//            // If startWithFadeIn is true, trigger custom behavior
//            if (shouldStartWithFadeIn) {
//                startWithFadeIn()
//            }
//        }
//    }
//
//    private fun startWithFadeIn() {
//        alpha = 0f
//        lottieAnimationView.playAnimation()
//        animate().alpha(1f).duration = 1000
//    }
}