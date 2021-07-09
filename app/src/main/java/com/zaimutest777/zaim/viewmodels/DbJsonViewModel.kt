package com.zaimutest777.zaim.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaimutest777.zaim.models.credits.CreditsList
import com.zaimutest777.zaim.repository.net.NetWorkRepository
import com.zaimutest777.zaim.utils.RxBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DbJsonViewModel @Inject internal constructor(private val netRepository: NetWorkRepository): ViewModel()
{
    private var cardsLink : String? = ""
    private var creditsList: CreditsList? = null

    init
    {
        val frc = RxBus.getConfig().value
        cardsLink = frc?.getString("cards_link")
        cardsLink?.let {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    try
                    {
                        val response = netRepository.getCreditsList(it)
                        if (response.isSuccessful)
                        {
                            creditsList = response.body()
                            creditsList
                        }
                    }
                    catch (e: Exception)
                    {
                        e.printStackTrace()
                    }
                }
            }
        }

    }
}