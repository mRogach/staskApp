package com.example.stackapp.data.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by
 * Mykhailo on 12/12/2019.
 */

abstract class BaseRestClient {

    fun getOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/2.2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
    }

}