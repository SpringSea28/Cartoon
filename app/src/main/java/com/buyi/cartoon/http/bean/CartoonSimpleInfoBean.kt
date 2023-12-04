package com.buyi.cartoon.http.bean

import android.os.Parcel
import android.os.Parcelable

class CartoonSimpleInfoBean() : Parcelable {
    var id:Int? = null
    var imgUrl:String? = null
    var title:String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        imgUrl = parcel.readString()
        title = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(imgUrl)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartoonSimpleInfoBean> {
        override fun createFromParcel(parcel: Parcel): CartoonSimpleInfoBean {
            return CartoonSimpleInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<CartoonSimpleInfoBean?> {
            return arrayOfNulls(size)
        }
    }


}
