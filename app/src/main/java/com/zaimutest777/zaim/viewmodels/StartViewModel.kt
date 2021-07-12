package com.zaimutest777.zaim.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.onesignal.OneSignal
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.models.Phone
import com.zaimutest777.zaim.repository.net.NetWorkRepository
import com.zaimutest777.zaim.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class StartViewModel @Inject internal constructor(private var netRepository: NetWorkRepository, private val app: Application): AndroidViewModel(app)
{
    private var _checkLink: MutableLiveData<Response<String>> = MutableLiveData()
    val checkLink: LiveData<Response<String>> = _checkLink

    fun nextPath(link: String)
    {
        _netState.value = NetworkState.Loading(0)
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try
                {
                    val showcase = netRepository.getShowcase(link)
                    _checkLink.value = showcase
                    _netState.value = NetworkState.Completed(showcase.code())
                } catch (e: Exception)
                {
                    e.printStackTrace()
                    _netState.value = e.message?.let { NetworkState.Error(it) }
                }
            }
        }
    }

    private var _netState: MutableLiveData<NetworkState> = MutableLiveData(NetworkState.Completed(0))
    val netState: LiveData<NetworkState> = _netState

    fun setNetState(state: NetworkState)
    {
        _netState.value = state
    }

    private var _confirm = MutableLiveData<Response<JSONObject>>()
    val confirm: LiveData<Response<JSONObject>> = _confirm

    var confirmCode = MutableLiveData("")

    var telNumberIsValid = MutableLiveData(false)

    fun getConfirm(userAgent: String, path: String, packageId: String, userId: String, getz: String, getr: String)
    {
        _netState.value = NetworkState.Loading(0)
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try
                {
                    val response = netRepository.getConfirm(userAgent, path, packageId, userId, getz, getr)
                    _confirm.value = response
                    _netState.value = NetworkState.Completed(response.code())
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                    _netState.value = e.message?.let { NetworkState.Error(it) }
                }
            }
        }
    }

    private var _phoneIsSent = MutableLiveData<NetworkState>()
    val phoneIsSent: LiveData<NetworkState> = _phoneIsSent

    fun sendPhoneNumber(path: String, data: Phone)
    {
        _phoneIsSent.value = NetworkState.Loading(0)
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try
                {
                    val response = netRepository.sendPhoneNumber(path, data)
                    _phoneIsSent.value = NetworkState.Completed(response.code())
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                    _phoneIsSent.value = e.message?.let { NetworkState.Error(it) }
                }
            }
        }

        OneSignal.setSMSNumber(data.data.number, object : OneSignal.OSSMSUpdateHandler
        {
            override fun onSuccess(result: JSONObject?)
            {
                result
                return
            }

            override fun onFailure(error: OneSignal.OSSMSUpdateError?)
            {
                error
                return
            }

        })
    }
}