package com.example.senapool_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.senapool_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, MyPlantFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.page_my_plant -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frm , MyPlantFragment()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.page_diary_feed -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frm, DiaryFeedFragment()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.page_setting -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_frm, SettingFragment()).commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}