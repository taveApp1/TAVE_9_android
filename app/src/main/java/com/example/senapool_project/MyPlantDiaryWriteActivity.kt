package com.example.senapool_project

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityMyPlantDiaryWriteBinding

class MyPlantDiaryWriteActivity : AppCompatActivity(){

    lateinit var binding: ActivityMyPlantDiaryWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlantDiaryWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}