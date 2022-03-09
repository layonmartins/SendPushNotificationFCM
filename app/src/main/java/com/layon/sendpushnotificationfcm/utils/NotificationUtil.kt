package com.layon.sendpushnotificationfcm.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.layon.sendpushnotificationfcm.MainActivity
import com.layon.sendpushnotificationfcm.R
import kotlin.random.Random

class NotificationUtil {

    companion object {

        fun createNotification(context: Context, title: String, body: String, data: Map<String, String>? = null) {

            val intent = Intent(context, MainActivity::class.java)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()
            Log.d("layon.f", "createNotification: title $title body: $body notificationID: $notificationID")

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val channel = if (data?.get("showPopUp") == "true") {
                context.getString(R.string.high_notification_channel_id)
            } else {
                context.getString(R.string.default_notification_channel_id)
            }
            val notification =
                NotificationCompat.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_baseline_add_reaction_24)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build()

            notificationManager.notify(notificationID, notification)
        }

        fun createChannels(context: Context) {
            createNotificationHighPriorityChannel(context)
            createNotificationDefaultPriorityChannel(context)
        }

        private fun createNotificationHighPriorityChannel(context: Context) {
            val channelName = context.getString(R.string.high_notification_channel_name)
            val channelId = context.getString(R.string.high_notification_channel_id)
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description =
                    "This channel the notification makes a sound and appears as a heads-up notification"
                enableLights(true)
                lightColor = Color.GREEN
            }
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        private fun createNotificationDefaultPriorityChannel(context: Context) {
            val channelName = context.getString(R.string.default_notification_channel_name)
            val channelId = context.getString(R.string.default_notification_channel_id)
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description =
                    "This channel the notification does not makes a sound and does not appears as a heads-up notification"
                enableLights(false)
                lightColor = Color.GREEN
            }
            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}