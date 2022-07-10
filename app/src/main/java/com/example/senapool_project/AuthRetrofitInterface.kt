package com.example.senapool_project

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {

    @Multipart
    @POST("/user/signup")
    fun signUp(@Query("email") email:String,
               @Query("password") password:String,
               @Query("userId") userId:String,
               @Part userImage: MultipartBody.Part): Call<AuthResponse>

    @POST("/mailConfirm")
    fun verifyEmailSend(@Body email: VerifySendEmail): Call<VerifySendResponse>

    @POST("/verifyCode")
    fun verifyConfirm(@Body code: VerifyCodeConfirm): Call<VerifyConfirmResponse>

    @POST("/user/login")
    fun login(@Body login:Login): Call<LoginResponse>

    /*@PATCH("/user/temPassword")
    Call <
    fun passwordEmailSend(@Body code: VerifySendEmail) : Call<VerifySendResponse>

    @PATCH("/user/temPassword")
    fun passwordEmailSend
    @PATCH("/posts/1")
    (@Field("title") String title);*/
}