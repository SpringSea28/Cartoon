package com.buyi.cartoon.account.ui

import android.os.Bundle
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityPersonInfoBinding
import com.buyi.cartoon.main.base.BaseActivity

class PersonInfoActivity : BaseActivity<ActivityPersonInfoBinding>() {

    override val TAG: String
        get() = PersonInfoActivity::class.simpleName!!


    override fun getBindingView(): ActivityPersonInfoBinding {
        return ActivityPersonInfoBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.person_title)
        binding.title.imgBack.setOnClickListener { finish() }


    }

    private fun initVm(){

    }

}