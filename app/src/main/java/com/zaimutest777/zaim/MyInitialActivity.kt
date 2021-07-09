package com.zaimutest777.zaim

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyInitialActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_activity)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavView?.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener
        {
            override fun onNavigationItemSelected(item: MenuItem): Boolean
            {
                when(item.itemId)
                {
                    R.id.menu_loans ->
                    {

                    }
                    R.id.menu_cards ->
                    {

                    }
                    R.id.menu_credits ->
                    {

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