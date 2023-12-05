package com.buyi.cartoon.detail.ui

import android.os.Bundle
import com.buyi.cartoon.databinding.ActivityCartoonDetailBinding
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp

class CartoonDetailActivity : BaseActivity<ActivityCartoonDetailBinding>() {

    override val TAG: String
        get() = CartoonDetailActivity::class.simpleName!!

    private var cartoonSimpleInfoBean:CartoonSimpleInfoBean? = null

    override fun getBindingView(): ActivityCartoonDetailBinding {
        return ActivityCartoonDetailBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        getIntentData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getIntentData(){
        cartoonSimpleInfoBean = intent.getParcelableExtra(ConstantApp.INTENT_CARTOON_DETAIL)
    }

    private fun initUi(){
        setPadding(false)
    }
}