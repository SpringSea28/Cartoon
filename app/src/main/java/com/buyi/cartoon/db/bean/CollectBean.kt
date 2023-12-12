package com.buyi.cartoon.db.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collect")
class CollectBean() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var rowid: Long? = null
    @ColumnInfo(name = "id_server")
    var id:Int? = null
    @ColumnInfo(name = "img_url")
    var imgUrl:String? = null
    @ColumnInfo(name = "cartoon_name")
    var name:String? = null
    @ColumnInfo(name = "last_reading_chapter")
    var lastReadingChapter:Int? = null
    @ColumnInfo(name = "last_reading_chapter_title")
    var lastReadingChapterTitle:String? = null
    @ColumnInfo(name = "last_update_chapter")
    var lastUpdateChapter:Int? = null
    @ColumnInfo(name = "status")
    var status:Int? = null

    @ColumnInfo(name = "user_id")
    var userId:Long? = null

    constructor(parcel: Parcel) : this() {
        rowid = parcel.readValue(Long::class.java.classLoader) as? Long
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        imgUrl = parcel.readString()
        name = parcel.readString()
        lastReadingChapter = parcel.readValue(Int::class.java.classLoader) as? Int
        lastReadingChapterTitle = parcel.readString()
        lastUpdateChapter = parcel.readValue(Int::class.java.classLoader) as? Int
        status = parcel.readValue(Int::class.java.classLoader) as? Int
        userId = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(rowid)
        parcel.writeValue(id)
        parcel.writeString(imgUrl)
        parcel.writeString(name)
        parcel.writeValue(lastReadingChapter)
        parcel.writeString(lastReadingChapterTitle)
        parcel.writeValue(lastUpdateChapter)
        parcel.writeValue(status)
        parcel.writeValue(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "CollectBean(rowid=$rowid, id=$id, imgUrl=$imgUrl, name=$name, lastReadingChapter=$lastReadingChapter, lastReadingChapterTitle=$lastReadingChapterTitle, lastUpdateChapter=$lastUpdateChapter, status=$status, userId=$userId)"
    }

    companion object CREATOR : Parcelable.Creator<CollectBean> {
        override fun createFromParcel(parcel: Parcel): CollectBean {
            return CollectBean(parcel)
        }

        override fun newArray(size: Int): Array<CollectBean?> {
            return arrayOfNulls(size)
        }
    }



}
