package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.senapool_project.databinding.FragmentMyPlantBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantFragment : Fragment() {

    lateinit var binding: FragmentMyPlantBinding
    private lateinit var myplantRVAdapter: MyPlantRVAdapter
    lateinit var token: String
    lateinit var userPK: String
    var check:Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantBinding.inflate(inflater,container,false)

        token = arguments?.getString("token").toString()
        userPK = arguments?.getString("userPK").toString()
        Log.d("MYPLANT/PK 입니다",userPK)

        binding.myPlantPlusIb.setOnClickListener {
            val intent = Intent(activity,MyPlantEnrollActivity::class.java)
            intent.putExtra("userPK",userPK) //데이터 넣기
            intent.putExtra("token",token) //데이터 넣기
            startActivity(intent)
            //startActivity(Intent(activity, MyPlantEnrollActivity::class.java))
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Log.d("MYPLANT","onstart")

        getPlant(userPK)





    }

    private fun initRecyclerView(result: plantDtoList) {
        myplantRVAdapter = MyPlantRVAdapter(requireContext(), result)

        binding.myPlantRv.adapter = myplantRVAdapter
    }



    fun getPlant(userPK:String){
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
                        Log.d("USERIMAGE/SUCCESS",resp.result.userImage.toString())
                        Glide.with(context!!).load(resp.result.userImage).into(binding.myPlantUserImageIv)
                        binding.myPlantUserNameTv.text=resp.result.userId

                        binding.myPlantRv.adapter = myplantRVAdapter
                        binding.myPlantRv.layoutManager = GridLayoutManager(context, 2)
                        check=2000
                        Log.d("MYPLANT/check",check.toString())
                        if (check==2000) {
                            Log.d("MYPLANT/check","실행된다")
                            myplantRVAdapter.setMyItemClickListener(object : MyPlantRVAdapter.MyItemClickListener {
                                override fun onItemClick() {
                                    //myplantdiarylist프래그먼트로 이동!
                                    (context as MainActivity).supportFragmentManager.beginTransaction()
                                        .replace(R.id.main_frm, MyPlantDiaryListFragment())
                                        .commitAllowingStateLoss()
                                }

//            override fun onRemoveSong(plantPK: Int) {
//                TODO("Not yet implemented")
//            }
                            })
                        }

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