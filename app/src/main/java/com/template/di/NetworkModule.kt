package com.template.di

import com.template.repository.net.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule
{
    @JvmStatic
    @get:Singleton
    @get:Provides
    val okHttpClient: OkHttpClient
    get()
    {
        return OkHttpClient.Builder().apply {
            connectTimeout(30L, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        }.build()
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient) : Retrofit
    {
        return Retrofit.Builder().apply {
            baseUrl("https://participaate.xyz/")
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
    }

    @Singleton
    @Provides
    fun provideApiInterface() : ApiService
    {
        val retrofit = getRetrofit(okHttpClient)
        return retrofit.create(ApiService::class.java)
    }
}