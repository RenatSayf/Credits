package com.zaimutest777.zaim.ui.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_SOUND
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R

object AppNotification
{
    const val NOTIFICATION_ID : Int = 25698744
    private const val CHANNEL_ID : String = "${NOTIFICATION_ID}.channel"

    fun create(context: Context, title: String, contentText: String) : Notification
    {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_os_notification_fallback_white_24dp)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_HIGH
            setChannelId(CHANNEL_ID)
            setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
            setContentTitle(title)
            setContentText(contentText)

            val intent = Intent(context, MyInitialActivity::class.java)
            val activity = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            setContentIntent(activity)

        }.build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name = context.getString(R.string.app_name)
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
        return notification
    }





}