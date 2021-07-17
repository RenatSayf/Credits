package com.zaimutest777.zaim.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.onesignal.OneSignal
import com.zaimutest777.zaim.models.Phone
import com.zaimutest777.zaim.repository.net.NetWorkRepository
import com.zaimutest777.zaim.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class StartViewModel @Inject internal constructor(private var netRepository: NetWorkRepository, app: Application): AndroidViewModel(app)
{
    private var _netState: MutableLiveData<NetworkState> = MutableLiveData(NetworkState.Completed(0))
    val netState: LiveData<NetworkState> = _netState

    fun setNetState(state: NetworkState)
    {
        _netState.value = state
    }

    private var _confirm: MutableLiveData<NetworkState> = MutableLiveData(NetworkState.Completed(0))
    val confirm: LiveData<NetworkState> = _confirm

    var confirmCode = MutableLiveData("")

    var telNumberIsValid = MutableLiveData(false)

    fun commit(userAgent: String, checkLink: String?, packageId: String, userId: String, getz: String, getr: String)
    {
        _confirm.value = NetworkState.Loading(0)
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try
                {
                    if (!checkLink.isNullOrEmpty())
                    {
                        val response = netRepository.getConfirm(userAgent, checkLink, packageId, userId, getz, getr)
                        _confirm.value = NetworkState.Completed(response.code())
                        //_confirm.value = NetworkState.Completed(403) //TODO сервер код 403 (перед релизом закоментировать)
                    } else
                    {
                        delay(3000)
                        _confirm.value = NetworkState.Completed(403)
                    }
                }
                catch (e: IllegalArgumentException)
                {
                    delay(3000)
                    _confirm.value = NetworkState.Completed(403)
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                    _confirm.value = e.message?.let { NetworkState.Error(it) }
                }
            }
        }
    }

    private var _phoneIsSent = MutableLiveData<NetworkState>()
    val phoneIsSent: LiveData<NetworkState> = _phoneIsSent

    fun sendPhoneNumber(path: String?, data: Phone)
    {
        _phoneIsSent.value = NetworkState.Loading(0)
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try
                {
                    if (!path.isNullOrEmpty())
                    {
                        val response = netRepository.sendPhoneNumber(path, data)
                        val code = response.code()
                        if (code == 201)
                        {
                            OneSignal.setSMSNumber(data.data.number, object : OneSignal.OSSMSUpdateHandler
                            {
                                override fun onSuccess(result: JSONObject?)
                                {
                                    _phoneIsSent.postValue(NetworkState.Completed(response.code()))
                                }

                                override fun onFailure(error: OneSignal.OSSMSUpdateError?)
                                {
                                    if (error != null)
                                    {
                                        _phoneIsSent.postValue(NetworkState.Error(error.message))
                                    }
                                }
                            })
                        }
                        else if (code == 403 || code == 1020)
                        {
                            _phoneIsSent.postValue(NetworkState.Completed(403))
                        }
                    } else
                    {
                        delay(5000)
                        _phoneIsSent.postValue(NetworkState.Completed(403))
                    }
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                    _phoneIsSent.value = e.message?.let { NetworkState.Error(it) }
                }
            }
        }



    }
}