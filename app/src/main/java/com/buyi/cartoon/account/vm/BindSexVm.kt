package com.buyi.cartoon.account.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.account.util.UserConstant
import com.buyi.cartoon.account.util.UserManager

class BindSexVm(application: Application) : AndroidViewModel(application) {

    private val TAG = BindSexVm::class.java.simpleName

    val bindSexResultLd = MutableLiveData<Boolean>()

    var sex:Int = UserConstant.SEX_UNKNOWN


    override fun onCleared() {
        Log.i(TAG,"onCleared")
        super.onCleared()
    }

    fun bindSex(sex:Int){
        this.sex = sex
        val userInfo = UserManager.getUserInfo()
        userInfo?.gender = sex
        userInfo?.let {
            UserManager.saveUserInfo(userInfo)
        }
        bindSexResultLd.postValue(true)
    }


}