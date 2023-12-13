package com.buyi.cartoon.account.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.account.util.UserManager


class EditNickNameVM(application: Application) : AndroidViewModel(application) {


    val publishLd = MutableLiveData<Boolean>()

    var nickName:String? = null


    fun publish(nickName:String){
        this.nickName = nickName
        val userInfo = UserManager.getUserInfo()
        userInfo?.nickName = nickName
        userInfo?.let { UserManager.saveUserInfo(it) }
        publishLd.postValue(true)
    }


}