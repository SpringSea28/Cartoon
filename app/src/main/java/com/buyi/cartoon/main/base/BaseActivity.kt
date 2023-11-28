package com.buyi.cartoon.main.base

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.buyi.cartoon.main.CartoonApp
import com.buyi.cartoon.main.utils.ScreenAdaptation
import com.buyi.cartoon.main.utils.StatusBarAdaptation

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected abstract val TAG: String
    lateinit var binding: T
    protected abstract fun getBindingView(): T
    protected abstract fun initAllMembersData(savedInstanceState: Bundle?)
    protected var isNormalCreate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CartoonApp.instance().addActivity(this, javaClass)
        ScreenAdaptation.setCustomDensity(this, application)
        val appStatus = CartoonApp.instance().appStatus
        if (appStatus != CartoonApp.APP_STATUS_NORMAL) { // 非正常启动流程，直接重新初始化应用界面
            Log.e(TAG, "reStartApp")
            isNormalCreate = false
            CartoonApp.instance().reStartApp()
            finish()
        } else {
            binding = getBindingView()
            StatusBarAdaptation.initStatusBar(this, binding.root)
            setContentView(binding.root)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val layoutParams = window.attributes
                layoutParams.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                window.attributes = layoutParams
            }
            initAllMembersData(savedInstanceState)
        }
    }


    override fun onDestroy() {
        CartoonApp.instance().removeActivity(this)
        super.onDestroy()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private var lastClickTime = System.currentTimeMillis()

    private fun isFastDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        return if (timeD in 0..300) {
            true
        } else {
            lastClickTime = time
            false
        }
    }


    fun setPadding(needPadding :Boolean){
        val rootView = binding.root
        val paddingStart: Int = rootView.getPaddingStart()
        val paddingEnd: Int = rootView.getPaddingEnd()
        if(needPadding) {
            val paddingTop = StatusBarAdaptation.getStatusBarHeight()
            rootView.setPadding(paddingStart, paddingTop, paddingEnd, 0)
        }else{
            rootView.setPadding(paddingStart, 0, paddingEnd, 0)
        }
    }

}