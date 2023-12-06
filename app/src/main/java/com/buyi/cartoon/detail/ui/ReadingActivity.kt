package com.buyi.cartoon.detail.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityCartoonReadingBinding
import com.buyi.cartoon.databinding.ActivityMainBinding
import com.buyi.cartoon.main.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReadingActivity : BaseActivity<ActivityCartoonReadingBinding>() {

    override val TAG: String
        get() = ReadingActivity::class.simpleName!!

    override fun getBindingView(): ActivityCartoonReadingBinding {
        return ActivityCartoonReadingBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        setPadding(true)
        binding.title.imgBack.setImageResource(R.mipmap.go_back_white)
        binding.title.imgBack.setOnClickListener{finish()}
        binding.title.imgRight.setImageResource(R.mipmap.more_white)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}