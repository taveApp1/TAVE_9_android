package com.example.senapool_project

import com.google.gson.annotations.SerializedName

data class VerifyCodeConfirm(
    @SerializedName(value="code") var code: String?=""
)
