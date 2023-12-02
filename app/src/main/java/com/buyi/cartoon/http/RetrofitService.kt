package com.buyi.cartoon.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {


    private var retrofit: Retrofit

    private const val BASEAPI = "https://www.wanandroid.com/";


    init {
        val builder = OkHttpClient()
            .newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
        val interceptor = CLoggingInterceptor()
        interceptor.level =
            CLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        retrofit = Retrofit.Builder()
            .baseUrl(BASEAPI)
            .client(builder.build())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    /**
     * 创建service对象
     */
    fun <T> createService(mClass: Class<T>): T {
        return retrofit.create(mClass) as T
    }


}