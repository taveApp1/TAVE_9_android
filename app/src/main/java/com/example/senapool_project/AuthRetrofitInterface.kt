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

    @GET("/myplant-list/{userPK}")
    fun MyPlantList(@Path("userPK") userPK: String): Call<MyPlantListResponse>

    @Multipart
    @POST("/myplant-list/{userPK}")
    fun MyPlantEnroll(@Header("Authorization") token:String,
                      @Path("userPK") userPK: String,
                      @Query("lastWater") lastWater:String,
                      @Query("plantName") plantName:String,
                      @Query("plantType") plantType:String,
                      @Query("startDay") startDay:String,
                      @Query("waterPeriod") waterPeriod:String,
                      @Part file: MultipartBody.Part): Call<MyPlantEnrollResponse>
}