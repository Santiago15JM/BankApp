package com.sjm.bankapp.logic

import com.sjm.bankapp.logic.models.dao.ChangeEmailRequest
import com.sjm.bankapp.logic.models.dao.ChangeEmailResponse
import com.sjm.bankapp.logic.models.dao.ChangePasswordRequest
import com.sjm.bankapp.logic.models.dao.ChangePasswordResponse
import com.sjm.bankapp.logic.models.dao.ChangePhoneRequest
import com.sjm.bankapp.logic.models.dao.ChangePhoneResponse
import com.sjm.bankapp.logic.models.dao.LoginRequest
import com.sjm.bankapp.logic.models.dao.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BankEndApi {
    @POST("auth/authenticate")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @PUT("user/changeEmail")
    suspend fun changeEmail(@Body changeEmailRequest: ChangeEmailRequest): Response<ChangeEmailResponse>

    @PUT("user/changePhone")
    suspend fun changePhone(@Body changePhoneRequest: ChangePhoneRequest): Response<ChangePhoneResponse>

    @GET("transactions/getBalance")
    suspend fun getBalance(@Query("accountId") accountId: Long): Long

    @PUT("user/changePassword")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>
}
