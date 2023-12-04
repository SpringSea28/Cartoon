package com.buyi.cartoon.home.bean

import android.os.Parcel
import android.os.Parcelable
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean

class HomeRecommendItemBean() : Parcelable {
    var id:Int? = null
    var title:String? = null
    var cartoonsList:List<CartoonSimpleInfoBean>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        title = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeRecommendItemBean> {
        override fun createFromParcel(parcel: Parcel): HomeRecommendItemBean {
            return HomeRecommendItemBean(parcel)
        }

        override fun newArray(size: Int): Array<HomeRecommendItemBean?> {
            return arrayOfNulls(size)
        }
    }


}