package com.example.senapool_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.senapool_project.databinding.ActivityLoginEmailBinding

class LoginPWActivity :AppCompatActivity(){

    lateinit var binding: ActivityLoginEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*이메일 버튼을 누르면 임시비밀번호 발송되고 로그인 화면으로 이동*/
        binding.emailBtn.setOnClickListener{
            val intent=Intent(this,LoginMainActivity::class.java)
            startActivity(intent)
        }
    }
}