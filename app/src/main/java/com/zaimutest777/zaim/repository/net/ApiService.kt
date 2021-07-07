package com.zaimutest777.zaim.repository.net

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService
{
    @GET("{url}")
    suspend fun getShowcase(@Path(value = "url", encoded = true) path: String) : Response<String>

    @GET("{url}")
    @Headers("User-Agent: getr=utm_source=google-play&utm_medium=organic")
    suspend fun getConfirm(
        @Path(value = "url", encoded = true) path: String,
        @Query("packageid", encoded = true) packageId: String,
        @Query("usserid", encoded = true) userId: String,
        @Query("getz", encoded = true) getz: String,
        @Query("getr", encoded = true) getr: String = "getr=utm_source=google-play&utm_medium=organic"
    ) : Response<JSONObject>


    @POST("{url}")
    @Headers("Content-Type: application/json;charset=UTF-8",
                    "Accept: */*")
    suspend fun sendPhoneNumber(
        @Path(value = "url", encoded = true)
        path: String,
        @Body
        data: String ) : Response<Unit>


}