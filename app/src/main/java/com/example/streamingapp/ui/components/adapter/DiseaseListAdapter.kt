package com.example.streamingapp.ui.components.adapter

import android.app.Activity
import android.media.MediaMetadataRetriever
import androidx.lifecycle.lifecycleScope
import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.TestimonyListItem
import com.example.streamingapp.databinding.RawVideoListingItemBinding
import com.example.streamingapp.utils.CustomAdapter
import com.example.streamingapp.utils.setSafeOnClickListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeUnit

object DiseaseListAdapter {
    @OptIn(DelicateCoroutinesApi::class)
    fun getDiseaseVideoAdapter(
        activity: Activity,
        onWatch: (item: TestimonyListItem) -> Unit = {}
    ) = lazy {
        CustomAdapter<TestimonyListItem, RawVideoListingItemBinding>(
            layoutResource = R.layout.raw_video_listing_item,
            onBind = { binding, item, _ ->
                binding.apply {
                    testimonyData = item
                    root.setSafeOnClickListener {
                        onWatch.invoke(item)
                    }
                }
            },
            isSameItems = { oldItem, newItem -> oldItem.id == newItem.id },
            isSameItemContent = { oldItem, newItem -> oldItem == newItem },
            deepCopy = { item -> item.copy() }
        )
    }
}