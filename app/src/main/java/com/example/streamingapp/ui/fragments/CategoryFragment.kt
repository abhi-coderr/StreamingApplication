package com.example.streamingapp.ui.fragments

import android.content.Intent
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
import com.example.streamingapp.ui.activities.DiseaseVideoActivity
import com.example.streamingapp.ui.activities.VideoPlayerActivity
import com.example.streamingapp.ui.components.adapter.CategoryListAdapter
import com.example.streamingapp.utils.AppConstants.CATEGORY_FRAGMENT_CATEGORY_NAME
import java.util.Locale


class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    private val categoryList = listOf(
        TestimonyCategory(
            1,
            "Back Pain",
            "https://images.pexels.com/photos/7298685/pexels-photo-7298685.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            2,
            "Acidity",
            "https://images.pexels.com/photos/14569658/pexels-photo-14569658.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            3,
            "Allergy",
            "https://images.pexels.com/photos/9882204/pexels-photo-9882204.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            4,
            "Alzheimer",
            "https://images.pexels.com/photos/7551606/pexels-photo-7551606.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            5,
            "Ankle",
            "https://images.pexels.com/photos/7298407/pexels-photo-7298407.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            6,
            "Heart Disease",
            "https://images.pexels.com/photos/4386467/pexels-photo-4386467.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            7,
            "Knee Pain",
            "https://images.pexels.com/photos/7339492/pexels-photo-7339492.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        ),
        TestimonyCategory(
            9,
            "First Category",
            "https://images.pexels.com/photos/3873188/pexels-photo-3873188.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        )
    )

    private val categoryAdapter by CategoryListAdapter.getCategoryAdapter { category ->
        sendCategory(category)
    }

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
//                    val filteredList = categoryAdapter.currentList.filter {
//                        it.category.toLowerCase(Locale.ROOT)
//                            .contains(query.toLowerCase(Locale.ROOT))
//                    }
                    val  filteredList = categoryAdapter.currentList.filter { it.category.contains(query, ignoreCase = true) }
                    categoryAdapter.submitList(filteredList)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        categoryAdapter.submitList(categoryList)
        binding.recyclerView.adapter = categoryAdapter
    }

    private fun sendCategory(category: String) {
        val intent = Intent(requireContext(), DiseaseVideoActivity::class.java)
        intent.putExtra(CATEGORY_FRAGMENT_CATEGORY_NAME, category)
        startActivity(intent)
    }

}