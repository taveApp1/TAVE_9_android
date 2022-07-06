package com.example.senapool_project

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import com.example.senapool_project.databinding.ActivityLoginSingnupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class LoginSignUpActivity :AppCompatActivity(){

    lateinit var binding: ActivityLoginSingnupBinding
    lateinit var profileImageBase64: String
    var verifyEmailSend: Int = 0
    var verifyConfirm: Int = 0

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
            // Base64 인코딩부분
            val ins: InputStream? = currentImageURL?.let {
                applicationContext.contentResolver.openInputStream(
                    it
                )
            }
            val img: Bitmap = BitmapFactory.decodeStream(ins)
            ins?.close()
            val resized = Bitmap.createScaledBitmap(img, 256, 256, true)
            val byteArrayOutputStream = ByteArrayOutputStream()
            resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val outStream = ByteArrayOutputStream()
            val res: Resources = resources
            profileImageBase64 = Base64.encodeToString(byteArray, NO_WRAP)
            // 여기까지 인코딩 끝

            // 이미지 뷰에 선택한 이미지 출력
            binding.settingEditImageIv.setImageURI(currentImageURL)
            try {
                //이미지 선택 후 처리
            }catch (e: Exception){
                e.printStackTrace()
            }
        } else{
            Log.d("ActivityResult", "something wrong")
        }
    }



    // 갤러리에서 사진 선택 후 실행
    @RequiresApi(Build.VERSION_CODES.N)
    val getFromAlbumResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data // 선택한 이미지의 주소
            // 이미지 파일 읽어와서 설정하기
            if (uri != null) {
                // 사진 가져오기
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                // 사진의 회전 정보 가져오기
                val orientation = getOrientationOfImage(uri).toFloat()
                // 이미지 회전하기
                val newBitmap = getRotatedBitmap(bitmap, orientation)
                // 회전된 이미지로 imaView 설정
                binding.settingEditImageIv.setImageBitmap(newBitmap)

            }
            else binding.settingEditImageIv.setImageResource(R.drawable.user)
        }
    }

    // 이미지 회전 정보 가져오기
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getOrientationOfImage(uri: Uri): Int {
        // uri -> inputStream
        val inputStream = contentResolver.openInputStream(uri)
        val exif: ExifInterface? = try {
            ExifInterface(inputStream!!)
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }
        inputStream.close()

        // 회전된 각도 알아내기
        val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        if (orientation != -1) {
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                ExifInterface.ORIENTATION_ROTATE_270 -> return 270
            }
        }
        return 0
    }

    // 이미지 회전하기
    @Throws(Exception::class)
    private fun getRotatedBitmap(bitmap: Bitmap?, degrees: Float): Bitmap? {
        if (bitmap == null) return null
        if (degrees == 0F) return bitmap
        val m = Matrix()
        m.setRotate(degrees, bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
    }





    private fun getUser(): User{
        val email : String = binding.signupEmail.text.toString()
        val password: String = binding.signupPassword.text.toString()
        val userId: String = binding.signupId.text.toString()
        val userImage: String = profileImageBase64

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
        if (binding.signupId.text.toString().isNotEmpty()) {
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