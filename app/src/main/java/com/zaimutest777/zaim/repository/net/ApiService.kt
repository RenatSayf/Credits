package com.zaimutest777.zaim.repository.net

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService
{
    @GET("{url}")
    suspend fun getShowcase(@Path(value = "url", encoded = true) path: String) : Response<String>

    @GET("{url}")
    @Headers("User-Agent: utm_source=google-play&utm_medium=organic")
    suspend fun getConfirm(
        @Path(value = "url", encoded = true) path: String,
        @Query("packageid", encoded = true) packageId: String,
        @Query("usserid", encoded = true) userId: String,
        @Query("getz", encoded = true) getz: String,
        @Query("getr", encoded = true) getr: String = "utm_source=google-play&utm_medium=organic"
    ) : Response<JSONObject>


}