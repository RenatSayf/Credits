package com.zaimutest777.zaim.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.zaimutest777.zaim.models.credits.Product
import io.reactivex.subjects.BehaviorSubject

object RxBus
{
    private val rConfig: BehaviorSubject<FirebaseRemoteConfig> = BehaviorSubject.create()

    fun sendConfig(config: FirebaseRemoteConfig)
    {
        rConfig.onNext(config)
    }
    fun getConfig() = rConfig

    private val productSubject: BehaviorSubject<Product> = BehaviorSubject.create()
    fun passProduct(product: Product)
    {
        productSubject.onNext(product)
    }
    fun getProduct() = productSubject
}