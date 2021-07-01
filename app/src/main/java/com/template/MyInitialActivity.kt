package com.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyInitialActivity : AppCompatActivity()
{
    private val ONESIGNAL_APP_ID = "3559894f-bb80-4cbd-b945-546a6f2edc20"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_activity)
    }
}