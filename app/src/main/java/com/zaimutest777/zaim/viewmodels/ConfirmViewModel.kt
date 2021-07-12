package com.zaimutest777.zaim.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConfirmViewModel @Inject internal constructor(app: Application) : AndroidViewModel(app)
{
    @SuppressLint("HardwareIds")
    val androidId: String = Settings.Secure.getString(app.applicationContext.contentResolver, Settings.Secure.ANDROID_ID)

    private var _agreed = MutableLiveData(true)
    val agreed: LiveData<Boolean> = _agreed

    fun acceptTheAgreement(param: Boolean)
    {
        _agreed.value = param
    }



}