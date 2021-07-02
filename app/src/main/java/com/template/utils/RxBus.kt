package com.template.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.reactivex.subjects.BehaviorSubject

object RxBus
{
    private val rConfig: BehaviorSubject<FirebaseRemoteConfig> = BehaviorSubject.create()

    fun sendConfig(config: FirebaseRemoteConfig)
    {
        rConfig.onNext(config)
    }
    fun getConfig() = rConfig
}