package com.buyi.cartoon.http.bean

import android.os.Parcel
import android.os.Parcelable

class CartoonDetailBean() : Parcelable {
    var id:Int? = null
    var imgUrl:String? = null
    var imgBigUrl:String? = null
    var title:String? = null
    var labels:List<String>? = null
    var score:Float? = null
    var scoreNumbers:Int? = null
    var description:String? = null
    var author:String? = null
    var status:Int? = null
    var totalChapter:Int? = null
    var latestChapter:Int? = null
    var chapterInfos:List<ChapterInfo>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        imgUrl = parcel.readString()
        imgBigUrl = parcel.readString()
        title = parcel.readString()
        labels = parcel.createStringArrayList()
        score = parcel.readValue(Float::class.java.classLoader) as? Float
        scoreNumbers = parcel.readValue(Int::class.java.classLoader) as? Int
        description = parcel.readString()
        author = parcel.readString()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        totalChapter = parcel.readValue(Int::class.java.classLoader) as? Int
        latestChapter = parcel.readValue(Int::class.java.classLoader) as? Int
        chapterInfos = parcel.createTypedArrayList(ChapterInfo)
    }


    class ChapterInfo():Parcelable{
        var id:Int? = null
        var imgUrl:String? = null
        var name:String? = null
        var time:String? = null

        constructor(parcel: Parcel) : this() {
            id = parcel.readValue(Int::class.java.classLoader) as? Int
            imgUrl = parcel.readString()
            name = parcel.readString()
            time = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(imgUrl)
            parcel.writeString(name)
            parcel.writeString(time)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ChapterInfo> {
            override fun createFromParcel(parcel: Parcel): ChapterInfo {
                return ChapterInfo(parcel)
            }

            override fun newArray(size: Int): Array<ChapterInfo?> {
                return arrayOfNulls(size)
            }
        }


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(imgUrl)
        parcel.writeString(imgBigUrl)
        parcel.writeString(title)
        parcel.writeStringList(labels)
        parcel.writeValue(score)
        parcel.writeValue(scoreNumbers)
        parcel.writeString(description)
        parcel.writeString(author)
        parcel.writeValue(status)
        parcel.writeValue(totalChapter)
        parcel.writeValue(latestChapter)
        parcel.writeTypedList(chapterInfos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartoonDetailBean> {
        override fun createFromParcel(parcel: Parcel): CartoonDetailBean {
            return CartoonDetailBean(parcel)
        }

        override fun newArray(size: Int): Array<CartoonDetailBean?> {
            return arrayOfNulls(size)
        }
    }


}
