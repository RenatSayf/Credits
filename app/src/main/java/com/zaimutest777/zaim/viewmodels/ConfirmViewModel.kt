package com.zaimutest777.zaim.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfirmViewModel : ViewModel()
{
    private var _agreed = MutableLiveData(true)
    val agreed: LiveData<Boolean> = _agreed

    fun acceptTheAgreement(param: Boolean)
    {
        _agreed.value = param
    }

}