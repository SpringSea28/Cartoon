package com.buyi.cartoon.my.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class CustomConstraintLayout : ConstraintLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(interruptTouch ){
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }


    var interruptTouch = false
}