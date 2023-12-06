package com.buyi.cartoon.http.bean

import android.os.Parcel
import android.os.Parcelable

class CommentBean() : Parcelable {
    var id:Int? = null
    var imgUrl:String? = null
    var nickname:String? = null
    var comment:String? = null
    var date:String? = null
    var likeNum:Int? = null
    var like:Int? = null
    var commentReplyList:List<CommentReply>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        imgUrl = parcel.readString()
        nickname = parcel.readString()
        comment = parcel.readString()
        date = parcel.readString()
        likeNum = parcel.readValue(Int::class.java.classLoader) as? Int
        like = parcel.readValue(Int::class.java.classLoader) as? Int
        commentReplyList = parcel.createTypedArrayList(CommentReply)
    }


    class CommentReply():Parcelable{
        var id:Int? = null
        var nickname:String? = null
        var reply:String? = null

        constructor(parcel: Parcel) : this() {
            id = parcel.readValue(Int::class.java.classLoader) as? Int
            nickname = parcel.readString()
            reply = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(nickname)
            parcel.writeString(reply)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CommentReply> {
            override fun createFromParcel(parcel: Parcel): CommentReply {
                return CommentReply(parcel)
            }

            override fun newArray(size: Int): Array<CommentReply?> {
                return arrayOfNulls(size)
            }
        }


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(imgUrl)
        parcel.writeString(nickname)
        parcel.writeString(comment)
        parcel.writeString(date)
        parcel.writeValue(likeNum)
        parcel.writeValue(like)
        parcel.writeTypedList(commentReplyList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CommentBean> {
        override fun createFromParcel(parcel: Parcel): CommentBean {
            return CommentBean(parcel)
        }

        override fun newArray(size: Int): Array<CommentBean?> {
            return arrayOfNulls(size)
        }
    }


}
