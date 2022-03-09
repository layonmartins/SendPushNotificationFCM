package com.layon.sendpushnotificationfcm.model

//This data class will be received in FirebaseMessagingService::class#onMessageReceived() from RemoteMessage.notification object
data class Notification(
    val title: String,
    val body: String
)