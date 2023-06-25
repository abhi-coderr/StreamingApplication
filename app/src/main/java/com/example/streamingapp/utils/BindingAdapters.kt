package com.example.streamingapp.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.streamingapp.R
import java.text.DateFormat
import java.util.*


@BindingAdapter("tint")
fun ImageView.setImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}

@BindingAdapter("loadImageUsingGlide")
fun loadImage(view: ImageView, imageUrl: String?) {

    Glide.with(view.context)
        .load(imageUrl)
        .error(R.color.theme_color)
        .timeout(60000)
        .placeholder(R.color.theme_color)
        .diskCacheStrategy(DiskCacheStrategy.ALL)//Adding caching for images
        .into(view)

}
