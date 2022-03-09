package com.layon.sendpushnotificationfcm.repository


import com.layon.sendpushnotificationfcm.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NotificationAPI {

    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification,
        @Header("Authorization") serverKey: String,
        @Header("Content-Type") contentType: String,
    ) : Response<ResponseBody>
}