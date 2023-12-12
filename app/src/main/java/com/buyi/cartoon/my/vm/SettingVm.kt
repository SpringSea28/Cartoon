package com.buyi.cartoon.my.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SettingVm(application: Application) : AndroidViewModel(application) {

    private val TAG = SettingVm::class.java.simpleName

    val loginOutResultLd = MutableLiveData<Boolean>()
    val accountCancelResultLd = MutableLiveData<Boolean>()

    override fun onCleared() {
        Log.i(TAG,"onCleared")
        super.onCleared()
    }


    fun loginOut(){
        loginOutResultLd.postValue(true)
    }

    fun accountCancel(){
        accountCancelResultLd.postValue(true)
    }

}