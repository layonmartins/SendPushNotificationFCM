package com.layon.sendpushnotificationfcm.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.layon.sendpushnotificationfcm.MainActivity
import com.layon.sendpushnotificationfcm.R
import kotlin.random.Random

private const val CHANNEL_ID = "sendpushnotificationfcm_channel"

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("layon.f", "message: $remoteMessage")
        Log.d("layon.f", "message.from: ${remoteMessage.from}")
        Log.d("layon.f", "message.data: ${remoteMessage.data}")
        Log.d("layon.f", "message.notification: ${remoteMessage.notification?.title} ${remoteMessage.notification?.body}")

        remoteMessage.notification?.let { notification ->
            createNotification(notification.title.toString(), notification.body.toString())
        }
    }

    fun createNotification(title: String, message: String) {
        Log.d("layon.f", "createNotification: $title $message")
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        createNotificationChannel(notificationManager)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_baseline_add_reaction_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }



    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d("layon.f", "onNewToken : $newToken")
    }
}