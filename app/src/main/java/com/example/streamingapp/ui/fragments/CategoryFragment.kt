package com.example.streamingapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.streamingapp.R
import com.example.streamingapp.data.model.response.TestimonyCategory
import com.example.streamingapp.databinding.FragmentCategoryBinding
import com.example.streamingapp.ui.components.adapter.CategoryListAdapter
import java.util.Locale


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    private val categoryList = listOf(
        TestimonyCategory(1, "Back Pain"),
        TestimonyCategory(2, "Acidity"),
        TestimonyCategory(3, "Allergy"),
        TestimonyCategory(4, "Alzheimer"),
        TestimonyCategory(5, "Ankle"),
        TestimonyCategory(6, "Heart Disease"),
        TestimonyCategory(7, "Knee Pain"),
        TestimonyCategory(9, "Others")
    )

    private val categoryAdapter by CategoryListAdapter.getCategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val query = charSequence.toString()
                if (query.isEmpty()) {
                    categoryAdapter.submitList(categoryList)
                } else {
                    val filteredList = categoryAdapter.currentList.filter {
                        it.category.toLowerCase(Locale.ROOT)
                            .contains(query.toLowerCase(Locale.ROOT))
                    }
                    categoryAdapter.submitList(filteredList)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        categoryAdapter.submitList(categoryList)
        binding.recyclerView.adapter = categoryAdapter
    }


}