package com.example.streamingapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.streamingapp.R
import com.example.streamingapp.data.model.adapteritemlist.AwardGallery
import com.example.streamingapp.databinding.FragmentAwardsBinding
import com.example.streamingapp.ui.components.adapter.AwardGalleryListAdapter

class AwardsFragment : Fragment() {
    private lateinit var binding: FragmentAwardsBinding

    private val adapter by AwardGalleryListAdapter.getSuggestionAdapter()

    private val awardList = listOf(
        AwardGallery(
            1,
            "https://images.pexels.com/photos/5240676/pexels-photo-5240676.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            2,
            "https://images.pexels.com/photos/6629521/pexels-photo-6629521.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            3,
            "https://images.pexels.com/photos/5473177/pexels-photo-5473177.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            4,
            "https://images.jdmagicbox.com/comp/chandigarh/f7/0172px172.x172.110206224806.q9f7/catalogue/nuga-best-health-care-centre--sector-10-chandigarh-acupressure-equipment-dealers-8qcyau.jpg",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            5,
            "https://images.pexels.com/photos/5793917/pexels-photo-5793917.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            6,
            "https://images.pexels.com/photos/5793695/pexels-photo-5793695.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            7,
            "https://images.pexels.com/photos/14797755/pexels-photo-14797755.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            8,
            "https://www.nugaindia.in/images/awards/award4-big.jpg",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            9,
            "https://www.nugaindia.in/images/awards/award4-big.jpg",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            10,
            "https://images.pexels.com/photos/6120392/pexels-photo-6120392.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            11,
            "https://www.nugaindia.in/images/awards/award4-big.jpg",
            "This is the Best Award ever for nugaBest"
        ),  AwardGallery(
            12,
            "https://www.nugaindia.in/images/awards/award4-big.jpg",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            13,
            "https://images.pexels.com/photos/7005693/pexels-photo-7005693.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            14,
            "https://www.nugaindia.in/images/TJ1/img1.jpg",
            "This is the Best Award ever for nugaBest"
        ), AwardGallery(
            15,
            "https://images.pexels.com/photos/6532373/pexels-photo-6532373.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
            "This is the Best Award ever for nugaBest"
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_awards, container, false)

        adapter.submitList(awardList)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() = binding.apply {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        awardRv.layoutManager = manager
        awardRv.setHasFixedSize(true)
        awardRv.adapter = adapter
    }

}