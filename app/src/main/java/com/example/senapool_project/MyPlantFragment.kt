package com.example.senapool_project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.senapool_project.databinding.FragmentMyPlantBinding

class MyPlantFragment : Fragment() {

    lateinit var binding: FragmentMyPlantBinding
    private var plantDatas = ArrayList<MyPlant>() //현재 서버가 없으니 이를 대체할 MyPlant 데이터를 만들었다.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPlantBinding.inflate(inflater,container,false)

        val token = arguments?.getString("token")
        val userPK = arguments?.getString("userPK")


        //데이터 리스트 생성 더미 데이터
        //서버가 없기에 일단 더미 데이터 만들어놓음.
        plantDatas.apply {
//            add(MyPlant("식물1",R.drawable.my_plant_image_example))
//            add(MyPlant("식물2",R.drawable.my_plant_image_example))
//            add(MyPlant("식물3",R.drawable.my_plant_image_example))
//            add(MyPlant("식물4",R.drawable.my_plant_image_example))
//            add(MyPlant("식물5",R.drawable.my_plant_image_example))
//            add(MyPlant("식물6",R.drawable.my_plant_image_example))
//            add(MyPlant("식물7",R.drawable.my_plant_image_example))
//            add(MyPlant("식물8",R.drawable.my_plant_image_example))
//            add(MyPlant("식물9",R.drawable.my_plant_image_example))
        }

        val myplantRVAdapter = MyPlantRVAdapter(plantDatas)
        binding.myPlantRv.adapter = myplantRVAdapter
        binding.myPlantRv.layoutManager = GridLayoutManager(context, 2)

        myplantRVAdapter.setMyItemClickListener(object: MyPlantRVAdapter.MyItemClickListener{
            override fun onItemClick() {
                //myplantdiarylist프래그먼트로 이동!
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, MyPlantDiaryListFragment())
                    .commitAllowingStateLoss()
            }
        })

        binding.myPlantPlusIb.setOnClickListener {
            startActivity(Intent(activity, MyPlantEnrollActivity::class.java))
        }

        return binding.root
    }


}