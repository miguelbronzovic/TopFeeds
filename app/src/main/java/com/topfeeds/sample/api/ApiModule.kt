/*
 * Created by Miguel Bronzovic
 *
 * Module class to obtain Retrofit's dependencies
 */
package com.topfeeds.sample.api

import com.topfeeds.sample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val OKHTTP_CONNECTION_TIMEOUT = (30 * 1000).toLong()
private const val OKHTTP_READ_TIMEOUT = (30 * 1000).toLong()

class ApiModule {

    companion object {
        val okHttpClient: OkHttpClient
            get() {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

                return OkHttpClient.Builder()
                        .connectTimeout(OKHTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                        .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .addInterceptor(loggingInterceptor)
                        .build()
            }
    }
}
