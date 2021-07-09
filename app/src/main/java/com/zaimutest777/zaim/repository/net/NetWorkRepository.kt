package com.zaimutest777.zaim.repository.net

import com.google.gson.JsonObject
import com.zaimutest777.zaim.models.Phone
import com.zaimutest777.zaim.models.credits.CreditsList
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NetWorkRepository @Inject internal constructor(private var apiService: ApiService) : ApiService
{
    override suspend fun getShowcase(path: String) : Response<String>
    {
        return apiService.getShowcase(path)
    }

    override suspend fun getConfirm(
        path: String,
        packageId: String,
        userId: String,
        getz: String,
        getr: String
    ): Response<JSONObject>
    {
        return apiService.getConfirm(path, packageId, userId, getz)
    }

    override suspend fun sendPhoneNumber(
        path: String,
        data: Phone
    ): Response<Unit>
    {
        return apiService.sendPhoneNumber(path, data)
    }

    override suspend fun getCreditsList(path: String): Response<CreditsList>
    {
        return apiService.getCreditsList(path)
    }

}