package com.example.streamingapp.data.model.adapteritemlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AwardGallery(

    val id: Int,
    val image: String,
    val detail: String

) : Parcelable
