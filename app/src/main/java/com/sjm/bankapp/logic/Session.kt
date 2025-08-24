package com.sjm.bankapp.logic

import android.content.Context
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.room.Room
import com.sjm.bankapp.logic.dto.auth.SessionDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

object Session {
    private lateinit var db: AppDatabase
    var userId: Long = 0
        private set
    var accountId: Long = 0
        private set
    var authToken: String = ""
        private set
    var userName: String = ""
        private set
    var userEmail: String = ""
        private set
    var userPhone: String = ""
        private set

    var timeoutJob: Job? = null
    var sessionExpired by mutableStateOf(false)

    fun instantiateDB(context: Context) {
        if (!this::db.isInitialized) {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "App.db").build()
        }
    }

    fun getSavedAccountsDao() = db.savedAccountsDao()

//    suspend fun loadDetails(context: Context) {
//        userEmail = Preferences.getUserEmail(context)!!
//        pass?
//    }

    suspend fun saveSessionDetails(context: Context, details: SessionDetails) {
        userId = details.uid
        accountId = details.aid
        authToken = details.token
        userName = details.name
        userEmail = details.email
        userPhone = details.phone

        Preferences.saveEmail(context, userEmail)
        //TODO save password in (encrypted?) preferences
    }

    private fun getTokenExpiration(): Long? {
        val parts = authToken.split(".")
        if (parts.size != 3) return null

        val payloadJson = try {
            val payload = Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_WRAP)
            JSONObject(String(payload, Charsets.UTF_8))
        } catch (_: Exception) {
            return null
        }

        return payloadJson.optLong("exp", 0L) * 1000 // Millis
    }

    fun startTimeoutJob() {
        timeoutJob = CoroutineScope(Dispatchers.IO).launch {
            val delay = getTokenExpiration()?.minus(System.currentTimeMillis())
            if (delay != null) {
                delay(delay)
                sessionExpired = true
            }
        }
    }

    fun stopTimeoutJob() {
        timeoutJob?.cancel()
        sessionExpired = false
    }
}
