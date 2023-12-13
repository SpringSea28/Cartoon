package com.buyi.cartoon.my.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivitySettingBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.dialog.ConfirmDialog
import com.buyi.cartoon.my.ui.dialog.AccountCancelDialog
import com.buyi.cartoon.my.vm.SettingVm
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.math.RoundingMode
import java.text.NumberFormat


class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override val TAG: String
        get() = SettingActivity::class.simpleName!!

    private val settingVm:SettingVm by viewModels()

    override fun getBindingView(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initCache()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.setting_title)
        binding.title.imgBack.setOnClickListener { finish() }

        val token = UserManager.getToken()
        if(token.isNullOrBlank()){
            binding.rlAccountCancel.visibility = View.GONE
            binding.tvLoginOut.visibility = View.GONE
        }
        binding.tvLoginOut.setOnClickListener {
            loginOut()
        }
        binding.rlAccountCancel.setOnClickListener {
            accountCancel()
        }
        binding.rlCache.setOnClickListener { deleteCache() }
    }

    private fun initVm(){
        settingVm.loginOutResultLd.observe(this){
            if(it){
                val intent = Intent()
                intent.putExtra(LOGIN_OUT,true)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
        settingVm.accountCancelResultLd.observe(this){
            if(it){
                val intent = Intent()
                intent.putExtra(ACCOUNT_CANCEL,true)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }
    private fun loginOut(){
        val dialog = ConfirmDialog()
        dialog.title = getString(R.string.setting_login_out_title)
        dialog.tvMsg = getString(R.string.setting_login_out_msg)
        dialog.leftListener = {
            settingVm.loginOut()
        }
        dialog.showDialog(supportFragmentManager)
    }

    private fun accountCancel(){
        val dialog = AccountCancelDialog()
        dialog.leftListener = {
            settingVm.accountCancel()
        }
        dialog.showDialog(supportFragmentManager)
    }

    companion object{
        const val LOGIN_OUT = "LOGIN_OUT"
        const val ACCOUNT_CANCEL = "ACCOUNT_CANCEL"
    }




    private fun initCache(){
        val observable = Observable.create<Long>(
            object : ObservableOnSubscribe<Long> {
                @Throws(Exception::class)
                override fun subscribe(emitter: ObservableEmitter<Long>) {
                    emitter.onNext(calCache())
                }
            }
        )
        val subscribe = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
            .subscribe(object : Observer<Long>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(length: Long) {
                    if (length < 1024) {
                        binding.cacheValue.text = "${length}B"
                    } else if (length < 1024 * 1024) {
                        binding.cacheValue.text = "${format(length / 1024f)}KB"
                    } else if (length < 1024 * 1024 * 1024) {
                        binding.cacheValue.text = "${format(length / 1024f / 1024f)}MB"
                    } else {
                        binding.cacheValue.text = "${format(length / 1024f / 1024f / 1024f)}GB"
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })

    }

    private fun calCache():Long{
        val cachePath = externalCacheDir!!.absolutePath
        val cacheFile = File(cachePath)
        var length = getFileSizes(cacheFile)


        val cachePath2 = Glide.getPhotoCacheDir(this)!!.absolutePath
        val cacheFile2 = File(cachePath2)
        val length2 = getFileSizes(cacheFile2)
        length += length2
        return length
    }

    private fun getFileSizes(f: File): Long {
        var size: Long = 0
        f.listFiles()?.let {
            for (i in it.indices) {
                size += if (it[i].isDirectory) {
                    getFileSizes(it[i])
                } else {
                    getFileSize(it[i])
                }
            }
        }
        return size
    }

    private fun getFileSize(file: File): Long {
        var size: Long = 0
        if (file.exists()) {
            val fis: FileInputStream?
            fis = FileInputStream(file)
            size = fis.available().toLong()
        } else {
            file.createNewFile()
        }
        return size
    }

    private fun format(value: Float): String {
        val nf = NumberFormat.getNumberInstance()
        nf.maximumFractionDigits = 1
        nf.minimumFractionDigits = 0
        nf.roundingMode = RoundingMode.HALF_UP
        nf.isGroupingUsed = false
        return nf.format(value)
    }

    private fun deleteCache() {
        val observable = Observable.create<Int>(
            object : ObservableOnSubscribe<Int> {
                @Throws(Exception::class)
                override fun subscribe(emitter: ObservableEmitter<Int>) {
                    val cachePath = externalCacheDir!!.absolutePath
                    val cacheFile = File(cachePath)
                    deleteFiles(cacheFile)
                    Glide.get(this@SettingActivity).clearDiskCache()
                    emitter.onNext(1)
                }
            }
        )
        val subscribe = observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
            .subscribe(object : Observer<Int>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    initCache()
                    Toast.makeText(this@SettingActivity,
                        getString(R.string.setting_cache_suc),
                        Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

    private fun deleteFiles(f: File) {
        f.listFiles()?.let {
            for (i in it.indices) {
                if (it[i].isDirectory) {
                    deleteFiles(it[i])
                } else {
                    val delete = it[i].delete()
                }
            }
        }
    }

}