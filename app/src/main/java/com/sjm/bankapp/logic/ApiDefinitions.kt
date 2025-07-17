package com.sjm.bankapp.logic

import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.Business
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.logic.models.dto.ChangeEmailRequest
import com.sjm.bankapp.logic.models.dto.ChangeEmailResponse
import com.sjm.bankapp.logic.models.dto.ChangePasswordRequest
import com.sjm.bankapp.logic.models.dto.ChangePasswordResponse
import com.sjm.bankapp.logic.models.dto.ChangePhoneRequest
import com.sjm.bankapp.logic.models.dto.ChangePhoneResponse
import com.sjm.bankapp.logic.models.dto.LoginRequest
import com.sjm.bankapp.logic.models.dto.LoginResponse
import com.sjm.bankapp.logic.models.dto.TransactionRequest
import com.sjm.bankapp.logic.models.dto.TransactionResponse
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

    @PUT("user/changePassword")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ChangePasswordResponse>

    @GET("transactions/getBalance")
    suspend fun getBalance(@Query("accountId") accountId: Long): Long

    @GET("transactions/getTransactionHistory")
    suspend fun getTransactionHistory(@Query("accountId") accountId: Long, @Query("page") page: Int = 0): Response<List<Entry>>

    @GET("transactions/getTransactionDetails")
    suspend fun getTransactionDetails(@Query("opId") operationId: String): Response<Transaction>

    @POST("transactions/makeTransaction")
    suspend fun makeTransaction(@Body transactionRequest: TransactionRequest): Response<TransactionResponse>

    @GET("business")
    suspend fun getBusinesses(): Response<List<Business>>

    @GET("bills/get-bill")
    suspend fun getBill(@Query("businessId") businessId: Long, @Query("serviceId") serviceId: String): Response<Bill>

    @POST("bills/pay-bill")
    suspend fun payBill(@Body bill: Bill): Response<TransactionResponse>
}
