package com.example.senapool_project

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "Code") val Code:Int
    )

data class VerifySendResponse(
    @SerializedName(value = "Code") val Code:Int
)

data class VerifyConfirmResponse(
    @SerializedName(value = "Code") val Code:Int
)
