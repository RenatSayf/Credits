package com.zaimutest777.zaim

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyInitialActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_activity)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(getString(R.string.one_signal_app_id))

        val metricaConfig = YandexMetricaConfig.newConfigBuilder("1bfbd449-bf14-4417-af97-0f6e980cbbf9").build()
        YandexMetrica.activate(this, metricaConfig)
        YandexMetrica.enableActivityAutoTracking(application)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavView?.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener
        {
            override fun onNavigationItemSelected(item: MenuItem): Boolean
            {
                when(item.itemId)
                {
                    R.id.menu_loans ->
                    {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.loansListFragment)
                    }
                    R.id.menu_cards ->
                    {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.cardsListFragment)
                    }
                    R.id.menu_credits ->
                    {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.creditsListFragment)
                    }
                }
                return true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean
    {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}