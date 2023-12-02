package com.buyi.cartoon.http.respority

import com.buyi.cartoon.http.DataApi
import com.buyi.cartoon.http.RetrofitService
import com.buyi.cartoon.http.bean.DemoReqData

class DataRespority {

    private var netWork = RetrofitService.createService(
        DataApi::class.java
    )

    /**
     * 查询护理数据
     */
    suspend fun loadData(
        pageId: Int
    ): DemoReqData {
        return netWork.getData(pageId)
    }
}