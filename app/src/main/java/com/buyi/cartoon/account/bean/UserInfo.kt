package com.buyi.cartoon.account.bean

import android.os.Parcel
import android.os.Parcelable

class UserInfo() : Parcelable {

    var token:String? = null
    var avatar:String? = null
    var nickName:String? = null
    var gender:Int? = null
    var status:Int? = null
    var signature:String? = null

    var id:Long? = null
    var loginTime:String? = null
    var phone:String? = null
    var qqOpenid:String? = null
    var wechatOpenid:String? = null
    var wechatUnionid:String? = null


    constructor(parcel: Parcel) : this() {
        token = parcel.readString()
        avatar = parcel.readString()
        nickName = parcel.readString()
        gender = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        signature = parcel.readString()

        id = parcel.readValue(Long::class.java.classLoader) as? Long
        loginTime = parcel.readString()
        phone = parcel.readString()
        qqOpenid = parcel.readString()
        wechatOpenid = parcel.readString()
        wechatUnionid = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeString(avatar)
        parcel.writeString(nickName)
        parcel.writeValue(gender)
        parcel.writeValue(status)
        parcel.writeString(signature)

        parcel.writeValue(id)
        parcel.writeString(loginTime)
        parcel.writeString(phone)
        parcel.writeString(qqOpenid)
        parcel.writeString(wechatOpenid)
        parcel.writeString(wechatUnionid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfo> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }


}