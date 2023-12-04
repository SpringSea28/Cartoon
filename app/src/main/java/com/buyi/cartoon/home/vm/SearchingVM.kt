package com.buyi.cartoon.home.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.http.datasource.HomeClassifyDs
import com.buyi.cartoon.main.utils.ConstantApp
import com.tencent.mmkv.MMKV
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class SearchingVM(application: Application) : AndroidViewModel(application) {

    private val TAG = SearchingVM::class.java.simpleName

    val classListLD = MutableLiveData<List<ClassifyInfoBean>>()
    val refreshFinishLD = MutableLiveData<Boolean>()
    val historyLabelLd = MutableLiveData<List<String>?>()

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20,3)
    ) {
        HomeClassifyDs()
    }.flow
        .cachedIn(viewModelScope)


    fun fetchCartoonList(type:Int?, status:Int?){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }
    }

    fun fetchLabel(){
        val historyStr = MMKV.defaultMMKV().getString(ConstantApp.KEY_SEARCH_HISTORY, null)
        var split = historyStr?.split(",")
        historyLabelLd.postValue(split)
    }

    fun addLabel(label:String){
        val curLabels = historyLabelLd.value
        val newLabels = ArrayList<String>()
        if (curLabels == null) {
            newLabels.add(label)
        }else{
            for (item in curLabels){
                if(item != label){
                    newLabels.add(item)
                }
            }
            newLabels.add(label)
        }
        val joinToString = newLabels.joinToString(separator = ",")
        MMKV.defaultMMKV().putString(ConstantApp.KEY_SEARCH_HISTORY,joinToString)
        historyLabelLd.postValue(newLabels)
    }

    fun clearLabel(){
        MMKV.defaultMMKV().putString(ConstantApp.KEY_SEARCH_HISTORY,null)
        historyLabelLd.postValue(null)
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




}