package com.template.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.template.R

class RConfigViewModel(app: Application) : AndroidViewModel(app)
{
    private val _remoteConfig = MutableLiveData<FirebaseRemoteConfig?>(null).apply {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 //TODO перед релизом изменить на 3600
        }
        val frc = Firebase.remoteConfig
        frc.setConfigSettingsAsync(configSettings)
        frc.fetchAndActivate()
            .addOnCompleteListener { t ->
                if (t.isSuccessful)
                {
                    frc.setDefaultsAsync(R.xml.remote_config_defaults)
                    this.value = frc
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                //_isOnLine.value = NetHelper.isOnline(app)
            }
    }
    var remoteConfig: LiveData<FirebaseRemoteConfig?> = _remoteConfig

    private var _isOnLine = MutableLiveData(true).apply {
        //value = NetHelper.isOnline(app)
    }
    //var isOnLine: LiveData<Boolean> = _isOnLine


}