package com.example.senapool_project

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.ActivityMyPlantDiaryWriteBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class MyPlantDiaryWriteActivity : AppCompatActivity(){

    lateinit var binding: ActivityMyPlantDiaryWriteBinding
    var file: File = File("drawable/diary_image_example.jpg")
    var publish:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlantDiaryWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPK = intent.getStringExtra("userPK")
        val plantPK = intent.getStringExtra("plantPK")
        val token = intent.getStringExtra("token")

        binding.myPlantDiaryWriteSaveBtn.setOnClickListener {
            enrollDiary(userPK!!,plantPK!!,token!!)
        }
        binding.myPlantDiaryWriteImgIv.setOnClickListener {
            // 갤러리에서 사진 선택해서 가져오기
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"     // 모든 이미지
            startActivityForResult(intent,102)
        }
        binding.myPlantDiaryWriteArrowIv.setOnClickListener { finish() }


        binding.myPlantDiaryWriteModifyBtn.setOnClickListener {
            if (!publish) {
                binding.myPlantDiaryWriteModifyBtn.text="공개"
            } else {
                binding.myPlantDiaryWriteModifyBtn.text="비공개"
            }
            publish = !publish
        }

    }

    //이미지를 위한 것
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == 102 && resultCode == Activity.RESULT_OK){
            val currentImageURL = intent?.data
            Log.d("IMAGE/SUCCESS",currentImageURL.toString())
            file= File(createCopyAndReturnRealPath(currentImageURL!!))
            Log.d("IMAGE/SUCCESS",createCopyAndReturnRealPath(currentImageURL!!).toString())
            // 이미지 뷰에 선택한 이미지 출력
            binding.myPlantDiaryWriteImgIv.setImageURI(currentImageURL)
            try {
                //이미지 선택 후 처리
            }catch (e: Exception){
                e.printStackTrace()
            }
        } else{
            Log.d("IMAGE/FAIL", "something wrong")
        }
    }

    fun createCopyAndReturnRealPath(uri: Uri) :String? {
        val context = applicationContext
        val contentResolver = context.contentResolver ?: return null

        // Create file path inside app's data dir
        val filePath = (context.applicationInfo.dataDir + File.separator
                + System.currentTimeMillis())
        val file = File(filePath)
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()


        }
        return file.getAbsolutePath()
    }

    private fun enrollDiary(userPK:String, plantPK:String, token:String) {
        if (binding.myPlantDiaryWriteTitleEt.text.toString().isEmpty()) {
            Toast.makeText(this, "일기 제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }
        if (binding.myPlantDiaryWriteDateEt.text.toString().isEmpty()) {
            Toast.makeText(this, "날짜를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }
        if (binding.myPlantDiaryWriteContentTv.text.toString().isEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }


        val requestFile = RequestBody.create("image".toMediaTypeOrNull(), file)

        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        Log.d("FILE",token)

        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.DiaryEnroll("Bearer "+token,userPK,plantPK,binding.myPlantDiaryWriteContentTv.text.toString(),
            binding.myPlantDiaryWriteDateEt.text.toString(),
            publish,
            binding.myPlantDiaryWriteTitleEt.text.toString(),
            body).enqueue(object : Callback<MyPlantEnrollResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<MyPlantEnrollResponse>, response: Response<MyPlantEnrollResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                val resp: MyPlantEnrollResponse = response.body()!!
                Log.d("ENROLL/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        Log.d("ENROLL/SUCCESS", resp.message)
                        finish()
                    }
                    else->{
                        Toast.makeText(this@MyPlantDiaryWriteActivity,resp.message, Toast.LENGTH_SHORT).show()
                        Log.d("ENROLL/FAIL",resp.code.toString())

                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantEnrollResponse>, t: Throwable) {
                Log.d("ENROLL/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("ENROLL", "HELLO")
    }



}