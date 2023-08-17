package com.example.streamingapp.ui.components.adapter

import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.TestimonyListItem
import com.example.streamingapp.databinding.RawTestimonyItemBestBinding
import com.example.streamingapp.databinding.RawTestimonyItemBinding
import com.example.streamingapp.databinding.RawTestimonyItemNewBinding
import com.example.streamingapp.databinding.RawVideoListingItemBinding
import com.example.streamingapp.utils.CustomAdapter
import com.example.streamingapp.utils.setSafeOnClickListener

object DiseaseListAdapter {
    fun getDiseaseVideoAdapter() = lazy {
        CustomAdapter<TestimonyListItem, RawVideoListingItemBinding>(
            layoutResource = R.layout.raw_video_listing_item,
            onBind = { binding, item, _ ->
                binding.apply {
                    testimonyData = item
                }
            },
            isSameItems = { oldItem, newItem -> oldItem.id == newItem.id },
            isSameItemContent = { oldItem, newItem -> oldItem == newItem },
            deepCopy = { item -> item.copy() }
        )
    }
}