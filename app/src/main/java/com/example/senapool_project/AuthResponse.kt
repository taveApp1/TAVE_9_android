package com.example.senapool_project

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
    )

data class VerifySendResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)

data class VerifyConfirmResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)

data class LoginResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: Result
)
data class Result(
    @SerializedName("token") var token: String?="",
    @SerializedName("userPk") var userPk: String?="",
    @SerializedName("userId") var userId: String?="",
    @SerializedName("email") var email: String?="",
    @SerializedName("userImageUrl") var userImageUrl: String?="",
)

data class MyPlantListResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: MyPlantListResult
)

data class MyPlantEnrollResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)