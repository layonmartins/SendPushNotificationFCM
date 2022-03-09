package com.layon.sendpushnotificationfcm.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.layon.sendpushnotificationfcm.utils.NotificationUtil

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("layon.f", "message: $remoteMessage")
        Log.d("layon.f", "message.from: ${remoteMessage.from}")
        Log.d("layon.f", "message.data: ${remoteMessage.data}")
        Log.d(
            "layon.f",
            "message.notification: ${remoteMessage.notification?.title} ${remoteMessage.notification?.body}"
        )

        remoteMessage.notification?.let { notification ->
            NotificationUtil.createNotification(
                context = this,
                title = notification.title.toString(),
                body = notification.body.toString(),
                data = remoteMessage.data)
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d("layon.f", "onNewToken : $newToken")
    }
}