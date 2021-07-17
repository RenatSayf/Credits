package com.zaimutest777.zaim.repository.net

import com.zaimutest777.zaim.models.Phone
import com.zaimutest777.zaim.models.credits.CreditsList
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NetWorkRepository @Inject internal constructor(private var apiService: ApiService) : ApiService
{
    override suspend fun getShowcase(userAgent: String, path: String) : Response<String>
    {
        return apiService.getShowcase(userAgent, path)
    }

    override suspend fun getConfirm(
        userAgent: String,
        path: String?,
        packageId: String,
        userId: String,
        getz: String,
        getr: String
    ): Response<JSONObject>
    {
        return apiService.getConfirm(userAgent, path, packageId, userId, getz, getr)
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