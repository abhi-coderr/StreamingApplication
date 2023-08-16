package com.example.streamingapp.ui.components.adapter

import com.example.streamingapp.R
import com.example.streamingapp.data.model.response.TestimonyCategory
import com.example.streamingapp.databinding.RawCategoryItemBinding
import com.example.streamingapp.utils.CustomAdapter


object CategoryListAdapter {
    fun getCategoryAdapter() = lazy {
        CustomAdapter<TestimonyCategory, RawCategoryItemBinding>(
            layoutResource = R.layout.raw_category_item,
            onBind = { binding, item, _ ->
                binding.apply {
                    categories = item
                }
            },
            isSameItems = { oldItem, newItem -> oldItem.id == newItem.id },
            isSameItemContent = { oldItem, newItem -> oldItem == newItem },
            deepCopy = { item -> item.copy() }
        )
    }


}