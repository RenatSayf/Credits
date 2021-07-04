package com.zaimutest777.zaim.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaimutest777.zaim.repository.net.NetWorkRepository
import com.zaimutest777.zaim.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class StartViewModel @Inject internal constructor(private var netRepository: NetWorkRepository): ViewModel()
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
                    _netState.value = NetworkState.Loading(showcase.code())
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
}