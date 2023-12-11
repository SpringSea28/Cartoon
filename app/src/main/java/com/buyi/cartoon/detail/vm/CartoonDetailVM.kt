package com.buyi.cartoon.detail.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.R
import com.buyi.cartoon.db.CollectBean
import com.buyi.cartoon.db.DbManager
import com.buyi.cartoon.home.bean.HomeRecommendItemBean
import com.buyi.cartoon.http.bean.CartoonDetailBean
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.http.bean.CommentBean
import com.buyi.cartoon.main.eventbus.MsgEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class CartoonDetailVM(application: Application) : AndroidViewModel(application) {

    private val TAG = CartoonDetailVM::class.java.simpleName

    val cartoonDetailBeanLd = MutableLiveData<CartoonDetailBean>()
    val readingChapterLd = MutableLiveData<Int>(1)
    val collectLd = MutableLiveData<Boolean>()
    val collectSucLd = MutableLiveData<Boolean>()
    val commentListLd = MutableLiveData<List<CommentBean>>()
    val commentList = ArrayList<CommentBean>()

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

    fun fetchComment(){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                testComment()
            }

    }

    fun fetchCollect(simpleInfoBean: CartoonSimpleInfoBean?){
        testCollect(simpleInfoBean)
    }

    fun fetchReadingChapter(simpleInfoBean: CartoonSimpleInfoBean?){
        testReadingChapter(simpleInfoBean)
    }

    fun setCollect(simpleInfoBean: CartoonSimpleInfoBean?){
        if(simpleInfoBean?.id == null){
            Log.e(TAG,"setCollect id null")
            return
        }
        if(collectLd.value == true){
            DbManager.delCollectSync(simpleInfoBean.id!!.toLong())
            collectLd.postValue(false)
            val msgEvent = MsgEvent()
            msgEvent.msgType = MsgEvent.COLLECT_REMOVE
            msgEvent.msgObject = simpleInfoBean.id
            EventBus.getDefault().post(msgEvent)
        }else{
            val collectBean = CollectBean()
            collectBean.id = simpleInfoBean.id
            collectBean.name = simpleInfoBean.title
            collectBean.imgUrl = testUrl[0]
            collectBean.lastReadingChapter = readingChapterLd.value
            collectBean.lastReadingChapterTitle = "woyebuzhidao"
            collectBean.lastUpdateChapter = 16
            collectBean.status = 1
            collectBean.userId = -1
            DbManager.insertCollectSync(collectBean)
            collectLd.postValue(true)
            collectSucLd.postValue(true)
            val msgEvent = MsgEvent()
            msgEvent.msgType = MsgEvent.COLLECT_ADD
            msgEvent.msgObject = collectBean
            EventBus.getDefault().post(msgEvent)
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
        val chapter3 = CartoonDetailBean.ChapterInfo()
        chapter3.id = 3
        chapter3.name = "嘎嘎嘎"
        chapter3.imgUrl = testUrl[5]
        chapter3.time = "2023-12-5"
        chapterInfos.add(chapter1)
        chapterInfos.add(chapter2)
        chapterInfos.add(chapter3)

        detailBean.chapterInfos = chapterInfos

        cartoonDetailBeanLd.postValue(detailBean)
    }

    private fun testCollect(simpleInfoBean: CartoonSimpleInfoBean?){
        if(simpleInfoBean?.id == null){
            Log.e(TAG,"query collect id null")
            return
        }
        val collectById = DbManager.getCollectById(-1, simpleInfoBean?.id!!.toLong())
        if(collectById != null)
            collectLd.postValue(true)
        else
            collectLd.postValue(false)
    }

    private fun testReadingChapter(simpleInfoBean: CartoonSimpleInfoBean?){
        if(simpleInfoBean?.id == null){
            Log.e(TAG,"query testReadingChapter id null")
            return
        }
        val lastReadingChapterBean = DbManager.getLastReadingChapterById(-1, simpleInfoBean?.id!!.toLong())
        Log.e(TAG,"query testReadingChapter $lastReadingChapterBean")
        if(lastReadingChapterBean != null)
            readingChapterLd.postValue(lastReadingChapterBean.lastReadingChapter)
    }

    private fun testComment(){
        val reply1 = CommentBean.CommentReply()
        reply1.id = 1
        reply1.nickname = "kkk"
        reply1.reply= "alksdjlasjdflaskjdfklasjklfdjaslkdjflaskdjflaskjdfkaslkdfsdafasd"
        val reply2 = CommentBean.CommentReply()
        reply2.id = 2
        reply2.nickname = "zzz"
        reply2.reply= "alksdjlasjdflaskjdfk"

        val replyList1 = ArrayList<CommentBean.CommentReply>()
        replyList1.add(reply1)

        val replyList2 = ArrayList<CommentBean.CommentReply>()
        replyList2.add(reply1)
        replyList2.add(reply2)

        val commentBean1 = CommentBean()
        commentBean1.id = 1
        commentBean1.nickname = "没有昵称"
        commentBean1.comment  = "忍者无敌卡卡卡卡"
        commentBean1.date = "2023-12-06"

        val commentBean2 = CommentBean()
        commentBean2.id = 2
        commentBean2.nickname = "没有昵称2"
        commentBean2.comment  = "哇哈哈哈"
        commentBean2.date = "2023-12-06"
        commentBean2.commentReplyList = replyList1
        commentBean2.like = 0
        commentBean2.likeNum = 100

        val commentBean3 = CommentBean()
        commentBean3.id = 2
        commentBean3.nickname = "没有昵称3"
        commentBean3.comment  = "你好哇"
        commentBean3.date = "2023-12-06"
        commentBean3.commentReplyList = replyList2
        commentBean3.like = 1
        commentBean3.likeNum = 250


        commentList.add(commentBean1)
        commentList.add(commentBean2)
        commentList.add(commentBean3)
        commentList.add(commentBean1)
        commentList.add(commentBean2)
        commentList.add(commentBean3)
        commentListLd.postValue(commentList)
    }

}