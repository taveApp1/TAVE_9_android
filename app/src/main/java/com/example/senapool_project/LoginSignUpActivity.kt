package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityLoginSingnupBinding

class LoginSignUpActivity :AppCompatActivity(){

    lateinit var binding: ActivityLoginSingnupBinding

    /*회원가입 완료 누르면 다시 백하기*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginSingnupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*회원가입 완료 누르면 다시 백하기*/
        binding.signupDoned.setOnClickListener{
            val intent=Intent(this,LoginMainActivity::class.java)
            startActivity(intent)
        }

        /*중복확인 검사도 해야함 */
        /*이거 데이터 저장해야하지 않나?*/


    }
}