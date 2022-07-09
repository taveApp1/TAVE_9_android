package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.FragmentMyPlantDiaryListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPlantDiaryListFragment : Fragment() {

    private lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentMyPlantDiaryListBinding
    lateinit var diaryRVAdapter: DiaryRVAdapter

    lateinit var token: String
    lateinit var userPK: String
    lateinit var plantPK: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantDiaryListBinding.inflate(inflater,container,false)

        token = arguments?.getString("token").toString()
        userPK = arguments?.getString("userPK").toString()
        plantPK = arguments?.getString("plantPK").toString()
        Log.d("DIARYLIST",token+' '+userPK+' '+plantPK)



        binding.myPlantDiaryListNewDiaryWriteTv.setOnClickListener {
            val intent = Intent(activity,MyPlantDiaryWriteActivity::class.java)
            intent.putExtra("userPK",userPK) //데이터 넣기
            intent.putExtra("plantPK",plantPK) //데이터 넣기
            intent.putExtra("token",token) //데이터 넣기
            startActivity(intent)

        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        getDiary(userPK)

    }

    private fun initRecyclerView(result: diaryPrevListDto) {
        diaryRVAdapter = DiaryRVAdapter(requireContext(), result)

        binding.myPlantDiaryListRv.adapter = diaryRVAdapter
    }

    fun getDiary(userPK:String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.MyPlantDiaryList("Bearer "+token,userPK,plantPK).enqueue(object : Callback<MyPlantDiaryListResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<MyPlantDiaryListResponse>, response: Response<MyPlantDiaryListResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("DIARYLIST/SUCCESS", response.toString())
                val resp: MyPlantDiaryListResponse = response.body()!!
                Log.d("DIARYLIST/SUCCESS", resp.toString())
                when(resp.code){
                    2000->{
                        initRecyclerView(resp.result.diaryPrevListDto)
                        Glide.with(context!!).load(resp.result.plantInfoDto.plantImage).into(binding.myPlantDiaryListPlantIv)
                        binding.myPlantDiaryListPlantNameTv.text=resp.result.plantInfoDto.plantName
                        binding.myPlantDiaryListPlantTypeTv.text=resp.result.plantInfoDto.plantType
                        binding.myPlantDiaryListHowLongDateTv.text= "D+"+resp.result.plantInfoDto.period.toString()
                        binding.myPlantDiaryListWateringBtn.text="\""+resp.result.plantInfoDto.waterPeriod.toString()+"일에 한번\n물을 줘야해요\""

                        binding.myPlantDiaryListRv.adapter = diaryRVAdapter
                        binding.myPlantDiaryListRv.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        Log.d("MYPLANT/check","실행된다")
                        diaryRVAdapter.setMyItemClickListener(object : DiaryRVAdapter.MyItemClickListener {
                                override fun onItemClick(plantPK: String?) {
                                    diaryRVAdapter.setMyItemClickListener(object: DiaryRVAdapter.MyItemClickListener{
                                    override fun onItemClick(diaryPK:String?) {
                                        val intent = Intent(activity,MyPlantDiaryDetailActivity::class.java)
                                        intent.putExtra("plantPK",plantPK) //데이터 넣기
                                        intent.putExtra("token",token) //데이터 넣기
                                        startActivity(intent)
                                    }
                                })




//            override fun onRemoveSong(plantPK: Int) {
//                TODO("Not yet implemented")
//            }
                            }
                        })

                        }


                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantDiaryListResponse>, t: Throwable) {
                Log.d("DIARYLIST/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("DIARYLIST", "HELLO")

    }


}