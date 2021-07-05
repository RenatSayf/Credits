package com.zaimutest777.zaim.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import androidx.lifecycle.*
import com.zaimutest777.zaim.repository.net.NetWorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ConfirmViewModel @Inject internal constructor(app: Application, private val netRepository: NetWorkRepository) : AndroidViewModel(app)
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