package com.example.senapool_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.senapool_project.databinding.DialogMyPlantWateringBinding

class DialogMyPlantWateringFragment: DialogFragment(){

    lateinit var binding: DialogMyPlantWateringBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_my_plant_watering, container, false)
    }

    //팝업하다 망한 것~
}