package com.buyi.cartoon.http

import com.buyi.cartoon.http.bean.DemoReqData
import retrofit2.http.GET
import retrofit2.http.Path

interface DataApi {
    /**
     * 获取数据
     */
    @GET("wenda/list/{pageId}/json")
    suspend fun getData(@Path("pageId") pageId:Int): DemoReqData
}