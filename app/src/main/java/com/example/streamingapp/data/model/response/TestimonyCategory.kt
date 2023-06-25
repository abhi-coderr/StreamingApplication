package com.example.streamingapp.data.model.response

import android.os.Parcelable
import com.example.streamingapp.utils.CheckableDataClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestimonyCategory(

    val id: Int,
    val category: String

) : Parcelable, CheckableDataClass()