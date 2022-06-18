package com.example.senapool_project

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityDiaryFeedDetailBinding

class DiaryFeedDetailActivity : AppCompatActivity(){

    lateinit var binding: ActivityDiaryFeedDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.diaryFeedDetailContentTv.movementMethod = ScrollingMovementMethod.getInstance()

    }
}