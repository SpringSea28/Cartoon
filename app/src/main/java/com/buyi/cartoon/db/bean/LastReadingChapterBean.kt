package com.buyi.cartoon.db.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_reading_chapter")
class LastReadingChapterBean() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var rowid: Long? = null
    @ColumnInfo(name = "id_server")
    var id:Int? = null
    @ColumnInfo(name = "cartoon_name")
    var name:String? = null
    @ColumnInfo(name = "last_reading_chapter")
    var lastReadingChapter:Int? = null
    @ColumnInfo(name = "last_reading_chapter_title")
    var lastReadingChapterTitle:String? = null


    @ColumnInfo(name = "user_id")
    var userId:Long? = null

    constructor(parcel: Parcel) : this() {
        rowid = parcel.readValue(Long::class.java.classLoader) as? Long
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        lastReadingChapter = parcel.readValue(Int::class.java.classLoader) as? Int
        lastReadingChapterTitle = parcel.readString()
        userId = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(rowid)
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(lastReadingChapter)
        parcel.writeString(lastReadingChapterTitle)
        parcel.writeValue(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "LastReadingChapterBean(rowid=$rowid, id=$id, name=$name, lastReadingChapter=$lastReadingChapter, lastReadingChapterTitle=$lastReadingChapterTitle, userId=$userId)"
    }

    companion object CREATOR : Parcelable.Creator<LastReadingChapterBean> {
        override fun createFromParcel(parcel: Parcel): LastReadingChapterBean {
            return LastReadingChapterBean(parcel)
        }

        override fun newArray(size: Int): Array<LastReadingChapterBean?> {
            return arrayOfNulls(size)
        }
    }



}
