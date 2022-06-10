package com.example.senapool_project

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.senapool_project.databinding.FragmentMyPlantDiaryListBinding

class MyPlantDiaryListFragment : Fragment() {

    private lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentMyPlantDiaryListBinding
    private var diaryDatas = ArrayList<Diary>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantDiaryListBinding.inflate(inflater,container,false)

        diaryDatas.apply {
            add(Diary("2022.05.10","일기 제목 1","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("2022.05.11","일기 제목 2","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("2022.05.12","일기 제목 3","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("2022.05.13","일기 제목 4","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("2022.05.14","일기 제목 5","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
            add(Diary("2022.05.15","일기 제목 6","내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.내용입니다.",R.drawable.diary_image_example))
        }

        val diaryRVAdapter = DiaryRVAdapter(diaryDatas)
        binding.myPlantDiaryListRv.adapter=diaryRVAdapter
        binding.myPlantDiaryListRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

//        diaryRVAdapter.setMyItemClickListener(object: DiaryRVAdapter.MyItemClickListener{
//            override fun onItemClick() {
//                (context as MainActivity).supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_frm)
//            }
//        })
        return binding.root
    }
    



}