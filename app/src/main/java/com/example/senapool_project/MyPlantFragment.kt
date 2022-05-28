package com.example.senapool_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.senapool_project.databinding.FragmentMyPlantBinding

class MyPlantFragment : Fragment() {

    lateinit var binding: FragmentMyPlantBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantBinding.inflate(inflater,container,false)

        return binding.root
    }
}