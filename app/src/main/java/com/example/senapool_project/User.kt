package com.example.senapool_project

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class User(
    @SerializedName(value = "email") var email: String? ="",
    @SerializedName(value = "password") var password: String? ="",
    @SerializedName(value = "userId") var userId: String? ="",
    @SerializedName(value = "userImage") var userImage: MultipartBody.Part
)

data class VerifySendEmail(
    @SerializedName(value = "email") var email: String? =""
)

data class VerifyCodeConfirm(
    @SerializedName(value="code") var code: String?=""
)

data class Login(
    @SerializedName(value = "userId") var userId:String?="",
    @SerializedName(value = "password") var password: String?=""
)

data class Result(
    @SerializedName("tokenDto") var tokenDto: TokenDto,
    @SerializedName("user") var LoginUser: LoginUser
)

data class TokenDto(
    @SerializedName(value = "token") var token: String?=""
)

data class LoginUser(
    @SerializedName("userPK") var userPK: String?="",
    @SerializedName("userId") var userId: String?="",
    @SerializedName("password") var password: String?="",
    @SerializedName("email") var email: String?="",
    @SerializedName("userImageName") var userImageName: String?="",
    @SerializedName("activated") var activated: Boolean,
    @SerializedName("myPlantList") var myPlantList: ArrayList<MyPlant>,
    @SerializedName("authorities") var authorities: ArrayList<MyPlant> //별 필요없음

)

data class MyPlantListResult(
    @SerializedName("userPK") var userPK: String?="",
    @SerializedName("userId") var userId: String?="",
    @SerializedName("userImage") var userImage: String?="",
    @SerializedName("plantListDto") var plantListDto: plantDtoList
)

data class plantDtoList(
    @SerializedName("plantDtoList") var plantDtoList: ArrayList<MyPlant>
)

