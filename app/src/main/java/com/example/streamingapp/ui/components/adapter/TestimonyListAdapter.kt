package com.example.streamingapp.ui.components.adapter

import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.TestimonyListItem
import com.example.streamingapp.databinding.RawTestimonyItemBestBinding
import com.example.streamingapp.databinding.RawTestimonyItemBinding
import com.example.streamingapp.databinding.RawTestimonyItemNewBinding
import com.example.streamingapp.utils.CustomAdapter
import com.example.streamingapp.utils.setSafeOnClickListener

object TestimonyListAdapter {
    fun getTestimonyAdapter(onWatch: (item: TestimonyListItem) -> Unit = {}) = lazy {
        CustomAdapter<TestimonyListItem, RawTestimonyItemBestBinding>(
            layoutResource = R.layout.raw_testimony_item_best,
            onBind = { binding, item, _ ->
                binding.apply {
                    testimonyData = item

                    watchVideoBtn.setSafeOnClickListener {
                        onWatch.invoke(item)
                    }

//


//                    shareBtn.setSafeOnClickListener {
//                        onShareListener.invoke(item)
//                    }

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