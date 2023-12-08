package com.buyi.cartoon.collect.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.db.CollectBean
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.http.datasource.HomeClassifyDs
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.disposables.DisposableContainer
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class CollectVm(application: Application) : AndroidViewModel(application) {


    var offset = 0
    var collectLoadResultLd = MutableLiveData<Boolean>()
    var noMoreDataLd = MutableLiveData<Boolean>()
    var collectList = ArrayList<CollectBean>()

    fun fetchCollect(){
        val single = DbManager.loadAllCollect(offset, 10, -1)

        single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Consumer<List<CollectBean>>{
                override fun accept(t: List<CollectBean>) {
                    offset += t.size
                    collectList.addAll(t)
                    if(t.isEmpty()){
                        noMoreDataLd.postValue(true)
                    }
                    collectLoadResultLd.postValue(true)
                }
            },object:Consumer<Throwable>{
                override fun accept(t: Throwable) {
                    collectLoadResultLd.postValue(false)
                }
            })

    }

    fun refresh(){
        offset = 0
        collectList.clear()
        fetchCollect()
    }

}