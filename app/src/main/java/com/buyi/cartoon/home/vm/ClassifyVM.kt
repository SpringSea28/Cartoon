package com.buyi.cartoon.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.http.datasource.HomeClassifyDs
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class ClassifyVM(application: Application) : AndroidViewModel(application) {

    val classListLD = MutableLiveData<List<ClassifyInfoBean>>()
    val refreshFinishLD = MutableLiveData<Boolean>()

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20,3)
    ) {
        HomeClassifyDs()
    }.flow
        .cachedIn(viewModelScope)


    fun fetchClassList(){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                test()
            }
    }

    fun fetchCartoonList(type:Int?, status:Int?){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }
    }

    private fun test(){

        val classList = ArrayList<ClassifyInfoBean>()
        for (i in 0 until 20){
            val classifyInfoBean = ClassifyInfoBean()
            classifyInfoBean.id = i
            classifyInfoBean.classification = "测试分类$i"
            classList.add(classifyInfoBean)
        }
        classListLD.postValue(classList)


//        network = !network
        refreshFinishLD.postValue(network)
    }
    var network = true


    companion object{
        const val TYPE_WHOLE = 1
        const val TYPE_UNIVERSAL = 2
        const val TYPE_BOY = 3
        const val TYPE_GIRL = 4

        const val STATUS_WHOLE = 1
        const val STATUS_SERIALIZATION = 2
        const val STATUS_OVER = 3
    }

}