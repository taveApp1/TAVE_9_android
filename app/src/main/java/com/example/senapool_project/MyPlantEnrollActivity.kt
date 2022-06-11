package com.example.senapool_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityMyPlantEnrollBinding

class MyPlantEnrollActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyPlantEnrollBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlantEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}