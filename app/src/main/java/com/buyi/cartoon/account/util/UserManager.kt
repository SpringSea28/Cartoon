package com.buyi.cartoon.account.util

import com.buyi.cartoon.account.bean.UserInfo
import com.buyi.cartoon.main.utils.ConstantApp
import com.tencent.mmkv.MMKV

object UserManager {


    fun getUserId(): Long?{
        return getUserInfo()?.id
    }

    fun getToken(): String?{
        return getUserInfo()?.token
    }


    fun getUserInfo(): UserInfo? {
        val userInfo = MMKV.defaultMMKV().decodeParcelable(
            ConstantApp.KEY_USER_INFO,
            UserInfo::class.java, null
        )
        return userInfo
    }

    fun saveUserInfo(userInfo: UserInfo) {
        MMKV.defaultMMKV().encode(
            ConstantApp.KEY_USER_INFO,
            userInfo
        )
    }

    fun deleteUserInfo(){
        MMKV.defaultMMKV().remove(ConstantApp.KEY_USER_INFO)
    }
}