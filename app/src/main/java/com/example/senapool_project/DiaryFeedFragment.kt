package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senapool_project.databinding.FragmentDiaryFeedBinding

class DiaryFeedFragment : Fragment() {

    lateinit var binding: FragmentDiaryFeedBinding
    private var diaryDatas = ArrayList<Diary>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiaryFeedBinding.inflate(inflater,container,false)

        diaryDatas.apply {
            add(Diary("회원1","2022.05.10","일기 제목 1","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("회원2","2022.05.11","일기 제목 2","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("회원3","2022.05.12","일기 제목 3","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("회원4","2022.05.13","일기 제목 4","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("회원5","2022.05.14","일기 제목 5","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("회원6","2022.05.15","일기 제목 6","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
        }

        val feedRVAdapter = FeedRVAdapter(diaryDatas)
        binding.diaryFeedListRv.adapter=feedRVAdapter
        binding.diaryFeedListRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        feedRVAdapter.setMyItemClickListener(object: FeedRVAdapter.MyItemClickListener{
            override fun onItemClick() {
                startActivity(Intent(activity,DiaryFeedDetailActivity::class.java))
            }
        })

        return binding.root
    }
}