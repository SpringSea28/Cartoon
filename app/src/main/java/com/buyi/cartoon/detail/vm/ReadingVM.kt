package com.buyi.cartoon.detail.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.http.bean.DemoReqData
import com.buyi.cartoon.http.datasource.HomeClassifyDs
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit


class ReadingVM(application: Application) : AndroidViewModel(application) {

    val classListLD = MutableLiveData<List<ClassifyInfoBean>>()

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20,3)
    ) {
        HomeClassifyDs()
    }.flow
        .cachedIn(viewModelScope)

    fun fetchCartoon(startPos:Int): Flow<PagingData<DemoReqData.DataBean.DatasBean>> {
        var flow = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20,3)
        ) {
            HomeClassifyDs(startPos)
        }.flow
        return flow
    }


}