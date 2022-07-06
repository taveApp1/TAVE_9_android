package com.example.senapool_project

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "code") val code:Int
    )

data class VerifySendResponse(
    //@SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String
)

data class VerifyConfirmResponse(
    @SerializedName(value = "code") val code:Int
)

data class LoginResponse(
    @SerializedName(value = "token") val token:String
)