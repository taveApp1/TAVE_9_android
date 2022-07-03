package com.example.senapool_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.senapool_project.databinding.ActivityLoginBinding

class LoginMainActivity :AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*로그인 버튼 누르면 그 때 MainActivity.kt로 이동*/
        binding.loginButton.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        /*회원가입 버튼 누르면 회원가입으로 이동*/
        binding.signupBtn.setOnClickListener{
            val intent=Intent(this,LoginSignUpActivity::class.java)
            startActivity(intent)
        }

        /*비밀번호찾기 버튼 누르면 이메일 비밀번호 찾기 페이지로 이동*/

        binding.findPWbtn.setOnClickListener{
            val intent=Intent(this,LoginPWActivity::class.java)
            startActivity(intent)
        }
        /*구글 버튼 누르면 api 회원가입&로그인 가능하도록*/
        /*네이버 버튼 누르면 api 회원가입&로그인 가능하도록*/
        /*카카오 버튼 누르면 api 회원가입&로그인 가능하도록*/
    }

}