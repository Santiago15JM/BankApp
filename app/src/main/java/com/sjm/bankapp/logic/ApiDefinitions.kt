package com.sjm.bankapp.logic

import com.sjm.bankapp.logic.models.dao.ChangeEmailRequest
import com.sjm.bankapp.logic.models.dao.ChangeEmailResponse
import com.sjm.bankapp.logic.models.dao.ChangePhoneRequest
import com.sjm.bankapp.logic.models.dao.ChangePhoneResponse
import com.sjm.bankapp.logic.models.dao.LoginRequest
import com.sjm.bankapp.logic.models.dao.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface BankEndApi {
    @POST("auth/authenticate")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @PUT("user/changeEmail")
    suspend fun changeEmail(@Body changeEmailRequest: ChangeEmailRequest): Response<ChangeEmailResponse>

    @PUT("user/changePhone")
    suspend fun changePhone(@Body changePhoneRequest: ChangePhoneRequest): Response<ChangePhoneResponse>
}

