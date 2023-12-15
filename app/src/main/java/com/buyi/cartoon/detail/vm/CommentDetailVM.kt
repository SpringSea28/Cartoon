package com.buyi.cartoon.detail.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.http.bean.CommentBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class CommentDetailVM(application: Application) : AndroidViewModel(application) {

    private val TAG = CommentDetailVM::class.java.simpleName

    val commentLd = MutableLiveData<CommentBean>()


    fun fetchComment(){
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                testComment()
            }
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


        commentLd.postValue(commentBean1)
    }

}