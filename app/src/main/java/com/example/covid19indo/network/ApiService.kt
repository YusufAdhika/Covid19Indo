package com.example.covid19indo.network

import android.telecom.Call
import com.example.covid19indo.model.ResponseCountry
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("provinsi")
    fun getAllNegara(): retrofit2.Call<ResponseCountry>
}

interface InfoService{
    @GET
    fun getInfoService(@Url url: String?)
}
object RetrofitBuilder{
    private val okhttp = OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://indonesia-covid-19.mathdro.id/api/")
        .client(okhttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}