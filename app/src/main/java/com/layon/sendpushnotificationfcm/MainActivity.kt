package com.layon.sendpushnotificationfcm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.layon.sendpushnotificationfcm.Constants.Companion.CONTENT_TYPE
import com.layon.sendpushnotificationfcm.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "layon.f"
const val TOPIC = "sendpushnotificationfcmtopic"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var serverKey : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //get the actual FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            binding.textViewMyFCMToken.text = "$token"
            Log.d("layon.f", "FcmToken: $token")
        })

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        binding.button.setOnClickListener {
            val title = binding.editTextTextPersonNameTitle.text.toString()
            val message = binding.editTextTextPersonNameMessage.text.toString()
            val recipientToken = binding.editTextTextPersonSendToken.text.toString()
            serverKey = binding.editTextTextPersonServerKey.text.toString()

            if(title.isNotEmpty() && message.isNotEmpty()) {

                if(serverKey.isNullOrBlank()) {
                    showToast("You was supposed to input the server_key")
                }
                if(recipientToken.isBlank()){
                    showToast("You was supposed to input the fcm token recipient")
                }

                PushNotification(
                    NotificationData(title, message),
                    recipientToken
                ).also {
                    sendNotification(it)
                    showToast("Trying send notification")
                }
            }
        }

        binding.copyImageView.setOnClickListener {
            copy(binding.textViewMyFCMToken.text.toString())
            showToast("fcm token copied")
        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(
                    notification = notification,
                    serverKey = "key=$serverKey",
                    contentType = CONTENT_TYPE
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Response: ${Gson().toJson(response)}")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }

    private fun copy(text: String){
        val clipboard: ClipboardManager =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("copy", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun showToast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}