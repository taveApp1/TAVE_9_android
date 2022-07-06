package com.example.senapool_project

import com.google.gson.annotations.SerializedName

data class VerifySendEmail(
    @SerializedName(value = "email") var email: String? =""
)
