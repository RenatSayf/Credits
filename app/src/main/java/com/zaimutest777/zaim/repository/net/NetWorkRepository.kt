package com.zaimutest777.zaim.repository.net

import retrofit2.Response
import javax.inject.Inject

class NetWorkRepository @Inject internal constructor(var apiService: ApiService)
{
    suspend fun getShowcase(link: String) : Response<String>
    {
        return apiService.getShowcase(link)
    }
}