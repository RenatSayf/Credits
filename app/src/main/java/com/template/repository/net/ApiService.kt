package com.template.repository.net

import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService
{
    @GET("{url}")
    suspend fun getShowcase(@Path(value = "url", encoded = true) path: String) : Response<String>


}