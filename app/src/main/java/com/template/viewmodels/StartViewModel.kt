package com.template.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template.repository.net.NetWorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class StartViewModel @Inject internal constructor(private var netRepository: NetWorkRepository): ViewModel()
{


    fun nextPath(link: String)
    {
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                try
                {
                    val showcase = netRepository.getShowcase(link)
                    if (showcase.isSuccessful)
                    {
                        println("****************** isSuccessful ${showcase.code()} *************************")
                    }
                    else
                    {
                        println("****************** notSuccessful ${showcase.code()} *************************")
                    }
                } catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }

        }
    }
}