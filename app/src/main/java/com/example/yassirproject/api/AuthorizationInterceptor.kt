package com.example.yassirproject.api

import android.util.Log
import com.example.yassirproject.BuildConfig
import com.example.yassirproject.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestUrl = original.url()
            .newBuilder()
            .addQueryParameter(
                Constants.API_KEY,
                BuildConfig.API_KEY
            ).build()

        val request = original.newBuilder().url(requestUrl).build()

        return chain.proceed(request)
    }
}