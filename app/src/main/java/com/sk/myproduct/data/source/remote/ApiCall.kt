package com.sk.myproduct.data.source.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit


interface ApiCall {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST
    suspend fun common(
        @Url urlPath: String,
        @Header("Authorization") authHeader: String,
        @Header("Host") host: String,
        @Body data: RequestBody
    ): Response<JsonObject>

    companion object {
        operator fun invoke(): ApiCall {
            return Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://books4lending.com/dev_customerAPI/api/auth/")
                .build()
                .create(ApiCall::class.java)
        }

        private fun getGson(): Gson? {
            return GsonBuilder().setLenient().excludeFieldsWithoutExposeAnnotation().create()
        }

        private fun getClient(): OkHttpClient? {
            try {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                return OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        }

    }

}