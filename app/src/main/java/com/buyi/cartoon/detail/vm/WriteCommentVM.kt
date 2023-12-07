package com.buyi.cartoon.detail.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class WriteCommentVM(application: Application) : AndroidViewModel(application) {


    val publishLd = MutableLiveData<Boolean>()




    fun publish(comment:String){

       publishLd.postValue(true)
    }


}