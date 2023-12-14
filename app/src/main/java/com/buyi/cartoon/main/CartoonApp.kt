package com.buyi.cartoon.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.tencent.mmkv.MMKV

class CartoonApp : Application() {

    var appStatus = APP_STATUS_KILLED
    private var activities: HashMap<Class<*>, Activity> = LinkedHashMap()
    private var devActivities: HashMap<Class<*>, Activity> = LinkedHashMap()

    var wxFrom: Int = 0

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
//        Glide.init(this, GlideBuilder())
    }


    fun reStartApp() {
        val intent = Intent(applicationContext, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun addActivity(activity: Activity, clz: Class<*>) {
        activities[clz] = activity
    }

    fun removeActivity(activity: Activity) {
        if (activities.containsValue(activity)) {
            activities.remove(activity.javaClass)
        }
    }

    fun finishActivity() {
        val keys: Set<Class<*>> = activities.keys
        val iter = keys.iterator()
        while (iter.hasNext()) {
            val next = iter.next()
            val activity = activities[next]
            activity?.finish()
        }
    }

    fun addDevActivity(activity: Activity, clz: Class<*>) {
        devActivities[clz] = activity
    }

    fun removeDevActivity(activity: Activity) {
        if (devActivities.containsValue(activity)) {
            devActivities.remove(activity.javaClass)
        }
    }

    fun finishDevActivity() {
        val keys: Set<Class<*>> = devActivities.keys
        val iter = keys.iterator()
        while (iter.hasNext()) {
            val next = iter.next()
            val activity = devActivities[next]
            activity?.finish()
        }
    }

    companion object{
        private lateinit var instance: CartoonApp
        fun instance() : CartoonApp = instance!!

        const val APP_STATUS_KILLED = 0 // 表示应用是被杀死后在启动的
        const val APP_STATUS_NORMAL = 1 // 表示应用时正常的启动流程
    }
}