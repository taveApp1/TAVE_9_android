package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.senapool_project.databinding.ActivitySettingMainBinding

class SettingFragment : Fragment() {

    lateinit var binding: ActivitySettingMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySettingMainBinding.inflate(inflater,container,false)

        /*회원정보 수정 버튼 클릭*/
        binding.editMemInfoBtn.setOnClickListener {
            startActivity(Intent(activity, SettingEditMemberInfo::class.java))
        }

        /*로그아웃 버튼 클릭*/
        binding.logoutBtn.setOnClickListener {
            startActivity(Intent(activity, LoginMainActivity::class.java))
        }

        /*회원탈퇴 버튼 클릭  ->  수정 필요*/
        binding.quitBtn.setOnClickListener {
            startActivity(Intent(activity, QuitActivity::class.java))
        }


        return binding.root
    }
}
