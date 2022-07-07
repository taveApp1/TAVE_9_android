package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.senapool_project.databinding.FragmentMyPlantBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantFragment : Fragment() {

    lateinit var binding: FragmentMyPlantBinding
    private lateinit var myplantRVAdapter: MyPlantRVAdapter
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantBinding.inflate(inflater,container,false)




        binding.myPlantPlusIb.setOnClickListener {
            startActivity(Intent(activity, MyPlantEnrollActivity::class.java))
        }

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val token = arguments?.getString("token")
        val userPK = arguments?.getString("userPK")
        Log.d("MYPLANT/PK",userPK.toString())
        getPlant("14")
    }

    private fun initRecyclerView(result: plantDtoList) {
        myplantRVAdapter = MyPlantRVAdapter(requireContext(), result)

        binding.myPlantRv.adapter = myplantRVAdapter
    }



    fun getPlant(userPK:String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.MyPlantList(userPK).enqueue(object : Callback<MyPlantListResponse> {

            //응답이 왔을 때 처리하는 부분
            override fun onResponse(call: Call<MyPlantListResponse>, response: Response<MyPlantListResponse>) {
                //response의 body안에 서버 개발자가 만든게 들어있음
                Log.d("MYPlANT/SUCCESS", response.toString())
                val resp: MyPlantListResponse = response.body()!!
                Log.d("MYPlANT/SUCCESS", resp.result.toString())
                when(resp.code){
                    2000->{
                        initRecyclerView(resp.result.plantListDto)
                    }
                    else->{
                        //Toast.makeText(this@MyPlantFragment,resp.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //네트워크 연결자체가 실패했을 때 실행하는 부분
            override fun onFailure(call: Call<MyPlantListResponse>, t: Throwable) {
                Log.d("MYPlANT/FAILURE", t.message.toString())
            }
        })
        //비동기작업이니까 함수가 잘 실행되었는지 확인차 찍어보기
        Log.d("MYPlANT", "HELLO")
    }
}