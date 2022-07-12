package com.example.senapool_project

import android.content.DialogInterface
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.senapool_project.databinding.ActivityMyPlantDiaryWriteBinding
import com.example.senapool_project.databinding.ActivityQuitCheckBinding

class QuitActivity:AppCompatActivity() {

    lateinit var binding: ActivityQuitCheckBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuitCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.quitBtnDoublecheck.setOnClickListener{
            val intent = Intent(this, LoginMainActivity::class.java)
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar)
            dlg.setMessage("회원탈퇴를 하시면 \n그동안의 기록들이 모두 삭제되며,\n" +
                    "복구가 불가능합니다. \n정말로 탈퇴 하시겠습나까?") // 메시지
            dlg.setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this,"회원탈퇴되었습니다.",Toast.LENGTH_SHORT).show()
                startActivity(intent)
                dlg.show()
            })
            dlg.setNegativeButton("아니요",DialogInterface.OnClickListener{dialog, which ->
            Toast.makeText(this, "계속하여 세나풀을 즐겨보세요", Toast.LENGTH_SHORT).show()
            })
            dlg.show()

        }
    }

}

