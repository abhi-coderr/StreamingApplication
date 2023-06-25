package com.example.streamingapp.ui.components.dialoges

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.streamingapp.R
import com.example.streamingapp.databinding.CategoryBottomSheetLayoutBinding
import com.example.streamingapp.utils.AppDialogUtils

object ShowCategoryBottomSheet {
    fun <T : ViewHolder> getDialog(appDialogUtils: AppDialogUtils?, adapter: Adapter<T>) =
        appDialogUtils?.getCustomDialog<CategoryBottomSheetLayoutBinding>(
            R.layout.category_bottom_sheet_layout,
            isCancelable = true,
            layoutGravity = Gravity.BOTTOM
        ) { binding ->
            binding.apply {
                categoryListRv.adapter = adapter
            }.root
        }

}