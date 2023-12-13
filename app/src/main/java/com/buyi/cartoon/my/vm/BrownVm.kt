package com.buyi.cartoon.my.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.db.bean.CollectBean
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.db.bean.BrownBean
import com.buyi.cartoon.main.eventbus.MsgEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BrownVm(application: Application) : AndroidViewModel(application) {

    private val TAG = BrownVm::class.java.simpleName

    var offset = 0
    val brownListLd = MutableLiveData<List<BrownBean>>()


    override fun onCleared() {
        Log.i(TAG,"onCleared")
        super.onCleared()
    }

    fun updateBrownReading(cartoonId:Int,readingChapter:Int):Int?{
        var i = 0
        var brownList = brownListLd.value
        if (brownList != null) {
            for (item in brownList){
                if(item.id == cartoonId){
                    item.lastReadingChapter = readingChapter
                    return i++
                }
            }
        }
        return null
    }

    fun fetchBrown(){
        val data = DbManager.loadAllBrown(offset, 20)
        brownListLd.postValue(data)

    }


}