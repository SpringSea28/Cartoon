package com.buyi.cartoon.my.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class MyVm(application: Application) : AndroidViewModel(application) {

    private val TAG = MyVm::class.java.simpleName



    override fun onCleared() {
        Log.i(TAG,"onCleared")
        super.onCleared()
    }



}