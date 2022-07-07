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

data class Token(
    @SerializedName(value = "token") var token:String?=""
)
