package com.buyi.cartoon.detail.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.R
import com.buyi.cartoon.home.bean.HomeRecommendItemBean
import com.buyi.cartoon.http.bean.CartoonDetailBean
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class CartoonDetailVM(application: Application) : AndroidViewModel(application) {

    val cartoonDetailBeanLd = MutableLiveData<CartoonDetailBean>()
    val readingChapterLd = MutableLiveData<Int>()
    val collectLd = MutableLiveData<Boolean>()
    val collectSucLd = MutableLiveData<Boolean>()

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

    fun fetchDetail(){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                test()
            }
    }

    fun setCollect(){
        if(collectLd.value == true){
            collectLd.postValue(false)
        }else{
            collectLd.postValue(true)
            collectSucLd.postValue(true)
        }
    }


    private fun test(){
        val detailBean = CartoonDetailBean()
        detailBean.id = (Math.random()*10).toInt()
        detailBean.imgUrl = testUrl[0]
        detailBean.imgBigUrl = testUrl[1]
        detailBean.title = "我是笨蛋"
        detailBean.score = 8.5f
        detailBean.scoreNumbers = 30
        detailBean.description = "我是傻瓜我是傻瓜我是傻瓜，哈哈哈哈哈哈"
        detailBean.author = "任我行"
        detailBean.status = 1
        detailBean.totalChapter = 30
        detailBean.latestChapter = 8
        detailBean.labels = listOf("北京","喀喀喀")

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
        chapterInfos.add(chapter1)
        chapterInfos.add(chapter2)
        chapterInfos.add(chapter1)

        detailBean.chapterInfos = chapterInfos

        cartoonDetailBeanLd.postValue(detailBean)
        readingChapterLd.postValue(2)
        collectLd.postValue(true)
    }


}