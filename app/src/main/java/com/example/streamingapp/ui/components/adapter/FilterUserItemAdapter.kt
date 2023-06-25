package com.example.streamingapp.ui.components.adapter

import android.util.Log
import com.example.streamingapp.R
import com.example.streamingapp.data.model.response.TestimonyCategory
import com.example.streamingapp.databinding.FilterCategoryItemLayoutBinding
import com.example.streamingapp.utils.CustomAdapter
import com.example.streamingapp.utils.setSafeOnClickListener

object FilterUserItemAdapter {

    fun getAdapter(doSomethingAfterCheckListUpdate: (selectedItem: TestimonyCategory) -> Unit) =
        lazy {
            CustomAdapter<TestimonyCategory, FilterCategoryItemLayoutBinding>(
                layoutResource = R.layout.filter_category_item_layout,
                onBind = { binding, item, checkListAction ->
                    binding.apply {
                        categoryName.text = item.category
                        selectCategoryCB.isChecked = item.isChecked
                        Log.i("booleanCheck____>>>>", selectCategoryCB.isChecked.toString())

                        mainRl.setSafeOnClickListener {
                            checkListAction.checkLikeRadioButton(
                                whichItemShouldBeChecked = { item.id == it.id },
                                afterUpdatingList = { selectedItem ->
                                    doSomethingAfterCheckListUpdate(selectedItem)
                                }
                            )
                        }
                    }
                },
                isSameItems = { oldItem, newItem -> oldItem.id == newItem.id },
                isSameItemContent = { oldItem, newItem -> oldItem == newItem },
                deepCopy = { loginResponseData -> loginResponseData.copy() }
            )
        }

}