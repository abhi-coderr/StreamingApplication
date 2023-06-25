package com.example.streamingapp.ui.components.adapter

import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.AwardGallery
import com.example.streamingapp.databinding.RawAwardGalleryItemLayoutBinding
import com.example.streamingapp.utils.CustomAdapter

object AwardGalleryListAdapter {
    fun getSuggestionAdapter() = lazy {
        CustomAdapter<AwardGallery, RawAwardGalleryItemLayoutBinding>(
            layoutResource = R.layout.raw_award_gallery_item_layout,
            onBind = { binding, item, _ ->
                binding.apply {

                    awardData = item

//                    root.context.downloadImageFromUrl(item.image,
//                        onSuccess = { bitmap ->
//                            imageView.setImageBitmap(bitmap)
//                        },
//                        onError = {
//                            imageView.setImageResource(R.drawable.ic_docent)
//                        }
//                    )
                }
            },
            isSameItems = { oldItem, newItem -> oldItem.id == newItem.id },
            isSameItemContent = { oldItem, newItem -> oldItem == newItem },
            deepCopy = { item -> item.copy() }
        )
    }
}