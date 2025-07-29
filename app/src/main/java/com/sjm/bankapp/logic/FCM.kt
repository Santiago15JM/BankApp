package com.sjm.bankapp.logic

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FCM : FirebaseMessagingService() {
    lateinit var token: String

    init {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            token = task.result
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.data["type"] == "new_txn") {
            val operationId = message.data["txn_id"]
            onNewTransaction(operationId!!)
        } else {
            NotificationHelper.showSimpleNotification(this,"?", "????")
        }
    }

    fun onNewTransaction(operationId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val (amount, senderName) = BankEnd.getTransactionNotificationDetail(operationId)
            NotificationHelper.showSimpleNotification(this@FCM,"Recibiste $amount", "Recibiste $amount de $senderName")
        }
    }

    companion object {
        suspend fun getFCMToken(): String? {
            return FirebaseMessaging.getInstance().token.await()
        }
    }

}