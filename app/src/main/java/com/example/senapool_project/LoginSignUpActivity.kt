package com.example.senapool_project

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityLoginSingnupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class LoginSignUpActivity :AppCompatActivity(){

    lateinit var binding: ActivityLoginSingnupBinding
    lateinit var profileImageBase64: String
    var verifyEmailSend: Int = 0
    var verifyConfirm: Int = 0
    lateinit var file: File

    /*회원가입 완료 누르면 다시 백하기*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginSingnupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 앨범 버튼 클릭 리스너 구현
        binding.settingMyPlantPictureSelectBtn.setOnClickListener {
            // 갤러리에서 사진 선택해서 가져오기
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"     // 모든 이미지
            startActivityForResult(intent,102)
        }

        //본인인증
        binding.emailVerifyBtn.setOnClickListener {
            verifyEmailSend()
        }

        //인증확인
        binding.emailVerifyOkBtn.setOnClickListener {
            verifyEmailConfirm()
        }

        /*회원가입 완료 누르면 다시 백하기*/
        binding.signupDoned.setOnClickListener{
            signUp()
        }



        /*중복확인 검사도 해야함 */
        /*이거 데이터 저장해야하지 않나?*/

    }

    //이미지를 위한 것
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == 102 && resultCode == Activity.RESULT_OK){
            val currentImageURL = intent?.data
            Log.d("IMAGE/SUCCESS",currentImageURL.toString())

            file=File(currentImageURL.toString())

            // 이미지 뷰에 선택한 이미지 출력
            binding.settingEditImageIv.setImageURI(currentImageURL)
            try {
                //이미지 선택 후 처리
            }catch (e: Exception){
                e.printStackTrace()
            }
        } else{
            Log.d("IMAGE/FAIL", "something wrong")
        }
    }




    private fun getUser(): User{
        val email : String = binding.signupEmail.text.toString()
        val password: String = binding.signupPassword.text.toString()
        val userId: String = binding.signupId.text.toString()
        val userImage: File = file

        return User(email, password, userId,userImage)
    }

    private fun getVerifySendEmail(): VerifySendEmail{
        val email : String = binding.signupEmail.text.toString()

        return VerifySendEmail(email)
    }

    private fun getVerifyCodeConfirm(): VerifyCodeConfirm{
        val code : String = binding.verifyEmailTv.text.toString()

        return VerifyCodeConfirm(code)
    }


    private fun signUp() { //API연결
        if (binding.signupId.text.toString().isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }
        if (binding.signupEmail.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }
        if (verifyConfirm == 0) {
            Toast.makeText(this, "인증확인 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }
        if (verifyEmailSend == 0) {
            Toast.makeText(this, "본인인증 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        if (binding.signupPassword.text.toString() != binding.signupPasswordDoublecheck.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(getUser()).enqueue(object : Callback<AuthResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                val resp: AuthResponse = response.body()!!
                Log.d("SIGNUP/SUCCESS", resp.code.toString())
                when(resp.code){
                    200->finish()
                    else->{
                        //아이디 중복확인 text 해줘야함.
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("SIGNUP", "HELLO")

    }


    //본인인증 --> 완료
    private fun verifyEmailSend(){
        if (binding.signupEmail.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        val authService2 = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService2.verifyEmailSend(getVerifySendEmail()).enqueue(object : Callback<VerifySendResponse>{

            override fun onResponse(call: Call<VerifySendResponse>,response: Response<VerifySendResponse>) {
                Log.d("VERIFY_SEND/SUCCESS", response.toString())
                Log.d("VERIFY_SEND/SUCCESS", response.code().toString()+' '+response.message().toString())
                val resp: VerifySendResponse = response.body()!!
                Log.d("VERIFY_SEND/SUCCESS", resp.message)
                when (response.code()) {
                    200 -> {
                        //finish()
                        binding.emailSendTv.visibility= View.VISIBLE
                        binding.emailSendTv.text=resp.message
                        verifyEmailSend=1
                    }
                    else -> {
                        binding.emailSendTv.visibility= View.VISIBLE
                        binding.emailSendTv.text=resp.message

                    }
                }
            }

            override fun onFailure(call: Call<VerifySendResponse>, t: Throwable) {
                Log.d("VERIFY_SEND/FAILURE", t.message.toString())
            }
        })
        Log.d("VERIFY_SEND", "HELLO")
    }


    //인증확인
    private fun verifyEmailConfirm(){
        if (binding.verifyEmailTv.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일로 전송된 인증코드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        val authService3 = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService3.verifyConfirm(getVerifyCodeConfirm()).enqueue(object : Callback<VerifyConfirmResponse>{

            override fun onResponse(call: Call<VerifyConfirmResponse>,response: Response<VerifyConfirmResponse>) {
                Log.d("VERIFY_CONFIRM/SUCCESS", response.toString())
                //val resp: VerifyConfirmResponse = response.body()!!
                //Log.d("VERIFY_CONFIRM/SUCCESS", resp.code.toString())
                when (response.code()) {
                    200 -> {
                        binding.verfiyEmailConfirmTv.visibility=View.VISIBLE
                        binding.verfiyEmailConfirmTv.text="인증성공했습니다."
                        verifyConfirm=1
                    }
                    else -> {
                        //인증틀렸을 때
                        binding.verfiyEmailConfirmTv.visibility=View.VISIBLE
                        binding.verfiyEmailConfirmTv.text="인증실패"
                    }
                }
            }

            override fun onFailure(call: Call<VerifyConfirmResponse>, t: Throwable) {
                Log.d("VERIFY_CONFIRM/FAILURE", t.message.toString())
            }
        })
    }


}