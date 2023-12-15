package com.buyi.cartoon.qqapi.bean

import android.os.Parcel
import android.os.Parcelable

class QqUserInfoBean() : Parcelable {

    var loginId:String? = null
    var token:String? = null
    var phoneBindFlag:Boolean? = null

    constructor(parcel: Parcel) : this() {
        loginId = parcel.readString()
        token = parcel.readString()
        phoneBindFlag = parcel.readValue(Int::class.java.classLoader) as? Boolean
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(loginId)
        parcel.writeString(token)
        parcel.writeValue(phoneBindFlag)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "QqUserInfoBean(loginId=$loginId, token=$token, phoneBindFlag=$phoneBindFlag)"
    }

    companion object CREATOR : Parcelable.Creator<QqUserInfoBean> {
        override fun createFromParcel(parcel: Parcel): QqUserInfoBean {
            return QqUserInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<QqUserInfoBean?> {
            return arrayOfNulls(size)
        }
    }



}