package com.layon.sendpushnotificationfcm.model

data class PushNotification(
    val notification: Notification,
    val data : NotificationData,
    val to : String //fcm token of who will receive the push notification
)