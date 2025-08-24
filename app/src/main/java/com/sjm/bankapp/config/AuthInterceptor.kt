package com.sjm.bankapp.config

import com.sjm.bankapp.logic.Session
import okhttp3.Interceptor
import okhttp3.Request

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest: Request = chain.request()

        if (isPublicRequest(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        val newRequest: Request = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${Session.authToken}")
            .build()

        return chain.proceed(newRequest)
    }

    private fun isPublicRequest(request: Request): Boolean {
        val url = request.url.toString()
        return url.endsWith("auth/authenticate") || url.endsWith("auth/register")
    }
}