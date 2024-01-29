package com.sjm.bankapp.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.sjm.bankapp.logic.BankEndApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime


object RetrofitHelper {
    private const val baseUrl = "http://10.0.2.2:8080/api/"

    fun getInstance(): BankEndApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BankEndApi::class.java)
    }
}

private val gson = GsonBuilder().registerTypeAdapter(
    LocalDateTime::class.java,
    JsonDeserializer { json, type, jsonDeserializationContext -> LocalDateTime.parse(json.asJsonPrimitive.asString) })
    .create()
