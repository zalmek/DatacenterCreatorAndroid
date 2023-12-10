package com.example.datacentercreator.network

import com.example.datacentercreator.model.Component
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DatacenterCreatorApi {

    @GET("api/components")
    suspend fun getAllComponents(@Query("filterText") filterText: String?): List<Component>

    @GET("api/components/{id}")
    suspend fun getComponent(@Path("id") id: String): Component

    @POST("component")
    suspend fun postComponent(@Body component: Component): List<Component>

    companion object RetrofitBuilder {
        private const val BASE_URL = "https://127.0.0.1:8000/"

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: DatacenterCreatorApi = getRetrofit().create()
    }

}