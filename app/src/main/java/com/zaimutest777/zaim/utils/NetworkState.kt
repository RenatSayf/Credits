package com.zaimutest777.zaim.utils

sealed class NetworkState
{
    data class Loading(val code: Int) : NetworkState()
    data class Completed(val code: Int) : NetworkState()
    data class Error(val message: String) : NetworkState()
}
