package com.layon.sendpushnotificationfcm.model

//This data class will be received in FirebaseMessagingService::class#onMessageReceived() from RemoteMessage.data object
data class NotificationData(
    //to add new notification create new fields here
    val data: String,
    val showPopUp: Boolean = true
)