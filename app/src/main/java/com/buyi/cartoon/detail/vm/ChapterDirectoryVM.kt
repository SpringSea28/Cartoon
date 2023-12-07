package com.buyi.cartoon.detail.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.http.bean.CartoonDetailBean
import com.buyi.cartoon.http.bean.CommentBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class ChapterDirectoryVM(application: Application) : AndroidViewModel(application) {

    val chapterListLd = MutableLiveData<List<CartoonDetailBean.ChapterInfo>>()
    val statusLd = MutableLiveData<Int>()
    val dateLd = MutableLiveData<String>()
    val latestChapterLd = MutableLiveData<Int>()



    val testUrl = arrayOf(
        "https://img1.baidu.com/it/u=225041176,855892897&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422",
        "https://img2.baidu.com/it/u=1665640482,1824915280&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img0.baidu.com/it/u=3145022701,3943645695&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889",
        "https://img2.baidu.com/it/u=710071810,2487788150&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800",
        "https://img2.baidu.com/it/u=2526519423,798155762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img2.baidu.com/it/u=3574827082,3267311681&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800",
        "https://img0.baidu.com/it/u=3279376560,3507705273&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img2.baidu.com/it/u=2242228201,4108939845&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"
        )

    fun fetchChapter(){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                test()
            }
    }




    private fun test(){

        val chapterInfos = ArrayList<CartoonDetailBean.ChapterInfo>()
        val chapter1 = CartoonDetailBean.ChapterInfo()
        chapter1.id = 1
        chapter1.name = "自由飞翔"
        chapter1.imgUrl = testUrl[3]
        chapter1.time = "2023-10-5"
        val chapter2 = CartoonDetailBean.ChapterInfo()
        chapter2.id = 2
        chapter2.name = "我了歌曲"
        chapter2.imgUrl = testUrl[4]
        chapter2.time = "2023-12-5"

        val chapter3 = CartoonDetailBean.ChapterInfo()
        chapter3.id = 3
        chapter3.name = "我了歌曲"
        chapter3.imgUrl = testUrl[4]
        chapter3.time = "2023-12-5"

        val chapter4 = CartoonDetailBean.ChapterInfo()
        chapter4.id = 4
        chapter4.name = "我了歌曲"
        chapter4.imgUrl = testUrl[4]
        chapter4.time = "2023-12-5"

        chapterInfos.add(chapter1)
        chapterInfos.add(chapter2)
        chapterInfos.add(chapter3)
        chapterInfos.add(chapter4)


        chapterListLd.postValue(chapterInfos)
        statusLd.postValue(1)
        dateLd.postValue("2023-12-07")
        latestChapterLd.postValue(4)
    }


}