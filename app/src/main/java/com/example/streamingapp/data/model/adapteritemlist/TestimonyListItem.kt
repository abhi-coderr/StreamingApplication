package com.example.streamingapp.data.model.adapteritemlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestimonyListItem(

    val id: Int,
    val detail: String? = null,
    val url: String? = null,
    val category: String? = null

) : Parcelable