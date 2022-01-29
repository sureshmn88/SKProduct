package com.sk.myproduct.data.source.remote

import com.sk.myproduct.data.Result
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call.invoke()
            var errorBody = ""
            if (response.errorBody() != null)
                errorBody = response.errorBody()!!.string()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            } else {
                return Result.Error(response.code().toString(), errorBody)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error("SafeApiRequest ", e.message!!)
        }
    }
}

class ApiException(message: String) : IOException(message)