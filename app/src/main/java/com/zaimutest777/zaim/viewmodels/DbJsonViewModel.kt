package com.zaimutest777.zaim.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaimutest777.zaim.models.credits.*
import com.zaimutest777.zaim.repository.net.NetWorkRepository
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DbJsonViewModel @Inject internal constructor(private val netRepository: NetWorkRepository): ViewModel()
{
    private var cardsLink : String? = null
    private var creditsList: CreditsList? = null

    private var _loans = MutableLiveData<List<Loan>?>()
    val loans: LiveData<List<Loan>?> = _loans

    private var _creditCards = MutableLiveData<List<Creditcard>?>()
    val creditCards: LiveData<List<Creditcard>?> = _creditCards

    private var _debitCards = MutableLiveData<List<Debitcard>?>()
    val debitCards: LiveData<List<Debitcard>?> = _debitCards

    private var _rasrochka = MutableLiveData<List<Rasrochka>?>()
    val rasrochka: LiveData<List<Rasrochka>?> = _rasrochka

    private var _credits = MutableLiveData<List<Credit>?>()
    val credits: LiveData<List<Credit>?> = _credits

    private var _netState: MutableLiveData<NetworkState> = MutableLiveData(NetworkState.Completed(0))
    val netState: LiveData<NetworkState> = _netState

    fun setNetState(state: NetworkState)
    {
        _netState.value = state
    }

    init
    {
        val frc = RxBus.getConfig().value
        cardsLink = frc?.getString("cards_link")
        cardsLink?.let {
            _netState.value = NetworkState.Loading(0)
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    try
                    {
                        val response = netRepository.getCreditsList(it)
                        if (response.isSuccessful)
                        {
                            creditsList = response.body()
                            _loans.value = creditsList?.loans
                            _creditCards.value = creditsList?.creditcards
                            _debitCards.value = creditsList?.debitcards
                            _rasrochka.value = creditsList?.rasrochka
                            _credits.value = creditsList?.credits
                        }
                        _netState.value = NetworkState.Completed(response.code())
                    }
                    catch (e: Exception)
                    {
                        e.printStackTrace()
                        _netState.value = e.message?.let { m -> NetworkState.Error(m) }
                    }
                }
            }
        }

    }
}