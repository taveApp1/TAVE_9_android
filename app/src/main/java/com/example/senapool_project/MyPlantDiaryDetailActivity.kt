package com.example.senapool_project

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityMyPlantDiaryDetailBinding

class MyPlantDiaryDetailActivity : AppCompatActivity(){

    lateinit var binding: ActivityMyPlantDiaryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlantDiaryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myPlantDiaryDetailContentTv.movementMethod = ScrollingMovementMethod.getInstance()

    }
}