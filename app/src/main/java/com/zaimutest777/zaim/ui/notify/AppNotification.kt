package com.zaimutest777.zaim.ui.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.zaimutest777.zaim.R

class AppNotification constructor(private val context: Context) : Notification()
{
    companion object
    {
        const val NOTIFICATION_ID : Int = 25698744
        const val CHANEL_ID : String = "${NOTIFICATION_ID}.service_notification"
    }

    private lateinit var notification: Notification

    init
    {

    }

    fun create(title: String, contentText: String) : Notification
    {
        notification = NotificationCompat.Builder(context, CHANEL_ID).apply {
            setOngoing(true)
            setSmallIcon(R.drawable.ic_os_notification_fallback_white_24dp)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_MAX
            setChannelId(CHANEL_ID)
            setDefaults(DEFAULT_SOUND)
            setContentTitle(title)
            setContentText(contentText)


        }.build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name = context.getString(R.string.app_name)
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        return notification
    }

    fun show()
    {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }


}