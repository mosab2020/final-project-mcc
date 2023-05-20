package com.example.palliativecare.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palliativecare.R
import com.example.palliativecare.adapter.TopicAdapter
import com.example.palliativecare.databinding.FragmentHomeBinding
import com.example.palliativecare.model.Topic
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val db = Firebase.firestore
        val data = ArrayList<Topic>()

        db.collection("topics")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    data.add(
                        Topic(
                            document.data["imageURL"].toString(),
                            document.data["title"].toString(),
                            document.data["description"].toString()
                        )
                    )
                }
                val llm = LinearLayoutManager(requireActivity())
                llm.setOrientation(LinearLayoutManager.VERTICAL)
                binding.homeRecyclerView.setLayoutManager(llm)
                binding.homeRecyclerView.setAdapter(TopicAdapter(requireActivity(),data))
                Log.d("Added Successfully", "Added Successfully")
            }
            .addOnFailureListener {
                Log.d("Added Failure", "Added Failure")

            }

        /*
         LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter( adapter );*/
//        val llm = LinearLayoutManager(requireActivity())
//        llm.setOrientation(LinearLayoutManager.VERTICAL)
//        binding.homeRecyclerView.setLayoutManager(llm)
//        binding.homeRecyclerView.setAdapter(TopicAdapter(requireActivity(),data))
//        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
//        val topicsAdapter = TopicAdapter(requireActivity(), data)
//        binding.homeRecyclerView.adapter = topicsAdapter


        return binding.root
    }

}