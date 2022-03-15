package com.layon.sendpushnotificationfcm.service

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.layon.sendpushnotificationfcm.ResultActivity
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

        remoteMessage.data?.let {
            Log.d("layon.f", "message.data.getdata: ${remoteMessage.data.get("data")}")
            if (it.get("data").toString() == "open result") {
                Log.d("layon.f", "it.contains(\"data\").toString() ${it.contains("data").toString()}")
                val intent = Intent(this, ResultActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }


        remoteMessage.notification?.let { notification ->
            NotificationUtil.createNotification(
                context = this,
                title = notification.title.toString(),
                body = notification.body.toString(),
                data = remoteMessage.data
            )
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Log.d("layon.f", "onNewToken : $newToken")
    }
}