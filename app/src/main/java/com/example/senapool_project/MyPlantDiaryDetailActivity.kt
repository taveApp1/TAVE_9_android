package com.example.senapool_project

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.ActivityMyPlantDiaryDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantDiaryDetailActivity : AppCompatActivity(){

    lateinit var binding: ActivityMyPlantDiaryDetailBinding
    lateinit var plantPK:String
    lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlantDiaryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plantPK = intent.getStringExtra("plantPK").toString()
        token = intent.getStringExtra("token").toString()
        Log.d("DIARYDETIAL/create",plantPK)

        binding.myPlantDiaryDetailContentTv.movementMethod = ScrollingMovementMethod.getInstance()

        binding.myPlantDiaryDetailArrowIv.setOnClickListener { finish() }

    }

    override fun onStart() {
        super.onStart()
        Log.d("DIARYDETIAL/start",plantPK)
        getDiary(plantPK)

    }

    fun getDiary(plantPK:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.MyPlantDiaryDetail("Bearer "+token,plantPK).enqueue(object :
            Callback<MyPlantDiaryDetailResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<MyPlantDiaryDetailResponse>, response: Response<MyPlantDiaryDetailResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("DIARYDETAIL/SUCCESS", response.toString())
                val resp: MyPlantDiaryDetailResponse = response.body()!!
                Log.d("DIARYDETAIL/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        Glide.with(this@MyPlantDiaryDetailActivity).load(resp.result.plantDiaryInfoDto.image).into(binding.myPlantDiaryDetailImgIv)
                        binding.myPlantDiaryDetailTitleTv.text=resp.result.plantDiaryInfoDto.title
                        binding.myPlantDiaryDetailContentTv.text=resp.result.plantDiaryInfoDto.content
                        binding.myPlantDiaryDetailDateTv.text=resp.result.plantDiaryInfoDto.createdAt
                        binding.myPlantDiaryDetailHeartCountTv.text=resp.result.plantDiaryInfoDto.likesCount.toString()
                        //공개여부 수정하기


                    }


                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantDiaryDetailResponse>, t: Throwable) {
                Log.d("DIARYDETAIL/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("DIARYDETAIL", "HELLO")

    }
}