package com.example.senapool_project

import com.google.gson.annotations.SerializedName
import java.io.File

data class User(
    @SerializedName(value = "email") var email: String? ="",
    @SerializedName(value = "password") var password: String? ="",
    @SerializedName(value = "userId") var userId: String? ="",
    @SerializedName(value = "userImage") var userImage: String? =""
)
