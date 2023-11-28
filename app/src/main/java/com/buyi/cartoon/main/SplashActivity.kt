package com.buyi.cartoon.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import autodispose2.AutoDispose
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import com.buyi.cartoon.databinding.ActivitySplashBinding
import com.buyi.cartoon.main.base.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable


import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val TAG: String
        get() = SplashActivity::class.simpleName!!


    override fun getBindingView(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        CartoonApp.instance().appStatus = CartoonApp.APP_STATUS_NORMAL
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            val intent = intent
            val action = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                finish()
            }
        }
    }


    override fun initAllMembersData(savedInstanceState: Bundle?) {
        binding.tvVersion.text = getVersion()
        if(showPermission()){
            return
        }
        val subscribe = Observable.timer(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .to(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))
            .subscribe {
                goMain()
            }

        return
    }


    private fun goMain(){
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    private fun getVersion(): String {
        return try {
            val info = packageManager.getPackageInfo(packageName, 0)
            "V" + info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }

    private fun showPermission() : Boolean{
        return false
//        val agree = MMKV.defaultMMKV().getBoolean("agree", false)
//        if(agree){
//            return false
//        }
//
//        val dialog = PermissionDialog()
//        dialog.agreeListener = {
//            MMKV.defaultMMKV().putBoolean("agree", true)
//            goMain()
//        }
//        dialog.disagreeListener = {
//            finish()
//        }
//        dialog.showDialog(supportFragmentManager)
//        return true
    }

}