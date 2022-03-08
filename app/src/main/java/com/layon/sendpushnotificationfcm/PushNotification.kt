package com.layon.sendpushnotificationfcm

data class PushNotification(
    val data : NotificationData,
    val to : String
)