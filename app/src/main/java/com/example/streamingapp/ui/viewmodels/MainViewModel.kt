package com.example.streamingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.streamingapp.data.model.response.Testimony
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {

    val isProgressLoad = MutableLiveData<Boolean>()

    var testimonyResponse = MutableLiveData<MutableList<Testimony>>()

    fun fetchTestimoniesByCategoryFromViewModel(targetCategory: String) {

        isProgressLoad.value = false

        val database: DatabaseReference = FirebaseDatabase.getInstance().reference

        val query: Query = database.child("Video").orderByChild("testimonyCategory").equalTo(targetCategory)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (testimonySnapshot in dataSnapshot.children) {
                    val testimony = testimonySnapshot.getValue(Testimony::class.java)
                    if (testimony != null) {
                        testimonyResponse.value?.add(testimony)
                    }
                }

                Log.i("list-------->", testimonyResponse.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
                isProgressLoad.value = false
            }
        })

        isProgressLoad.value = false
    }


}