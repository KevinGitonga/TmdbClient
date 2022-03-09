package com.a1g0.tmdbclient.data.api

import retrofit2.Response
import timber.log.Timber.Forest.e

abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call : suspend() -> Response<T>): T {

        e("Api Request")
        val response = call.invoke()

        if (response.isSuccessful && response.body() != null) {
            e(response.body().toString())
            return response.body()!!
        }
        else {
            e(response.code().toString())
            e(response.message())
            throw Exception(response.code().toString())
        }

    }

}