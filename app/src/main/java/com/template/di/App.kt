package com.template.di

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MultiDexApplication()
{
    override fun attachBaseContext(base: Context?)
    {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}