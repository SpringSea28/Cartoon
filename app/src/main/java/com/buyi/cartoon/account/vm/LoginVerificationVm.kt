package com.buyi.cartoon.account.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tencent.mmkv.MMKV
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class LoginVerificationVm(application: Application) : AndroidViewModel(application) {

    val resetCountDownLd = MutableLiveData<Boolean>()
    val countDownSecondLd = MutableLiveData<Int>()
    val loginResultLd = MutableLiveData<Boolean>()
    private var isInCountDown = false
    private var countDown: Disposable? = null
    val networkErrorDataLd = MutableLiveData<String>()
    val requestErrorLd = MutableLiveData<String>()
    val codeIdErrorLd = MutableLiveData<Boolean>()
    val codeSucLd = MutableLiveData<Boolean>()
    var codeId:String? = null


    override fun onCleared() {
        Log.i(TAG, "onCleared")
        stopTimer()
        super.onCleared()
    }

    private fun startTimer(){
        if(isInCountDown){
            return
        }
        isInCountDown = true
        countDown = Observable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val restTime = 60 -it -1
                countDownSecondLd.postValue(restTime.toInt())
                if(restTime == 0L){
                    stopTimer()
                }
            }
    }

    private fun stopTimer(){
        isInCountDown = false
        resetCountDownLd.postValue(true)
        if(countDown !=null && !countDown!!.isDisposed){
            countDown!!.dispose()
        }
    }

    fun getVerification(phone: String){
        startTimer()
//        HttpReqManager.instance.getVerificationCode(phone).subscribe(
//            object : CustomObserver<VerificationCodeHttpBean>() {
//            override fun onCustomNext(result: VerificationCodeHttpBean) {
//                if (result.code == HttpCode.SUCCESS) {
//                    codeSucLd.postValue(true)
//                    codeId = result.data
//                } else {
//                    stopTimer()
//                    requestErrorLd.postValue(result.msg)
//                }
//            }
//
//            override fun onCustomComplete(hasError: Boolean, e: Throwable?) {
//                if(hasError) {
//                    stopTimer()
//                    networkErrorDataLd.postValue(e?.message)
//                }
//            }
//        })
    }

    fun login(phone:String,verification:String){
        if(codeId == null){
            codeIdErrorLd.postValue(true)
            return
        }
//        HttpReqManager.instance.verificationLogin(phone,verification, codeId!!).subscribe(
//            object : CustomObserver<BaseHttpBean2<String>>() {
//                override fun onCustomNext(result: BaseHttpBean2<String>) {
//                    if (result.code == HttpCode.SUCCESS) {
//                        val token = result.data
//                        MMKV.defaultMMKV().encode(Constant.KEY_TOKEN, token)
//                        loginResultLd.postValue(true)
//                    } else {
//                        requestErrorLd.postValue(result.msg)
//                    }
//                }
//
//                override fun onCustomComplete(hasError: Boolean, e: Throwable?) {
//                    if(hasError) {
//                        networkErrorDataLd.postValue(e?.message)
//                    }
//                }
//            })
    }



    companion object {
        private val TAG = LoginVerificationVm::class.java.simpleName
    }
}