package com.buyi.cartoon.detail.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityCartoonDetailBinding
import com.buyi.cartoon.detail.vm.CartoonDetailVM
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp

class CartoonDetailActivity : BaseActivity<ActivityCartoonDetailBinding>() {

    override val TAG: String
        get() = CartoonDetailActivity::class.simpleName!!

    private var cartoonSimpleInfoBean:CartoonSimpleInfoBean? = null
    private val cartoonDetailVM:CartoonDetailVM by viewModels()

    override fun getBindingView(): ActivityCartoonDetailBinding {
        return ActivityCartoonDetailBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        getIntentData()
        initUi()
        initVm()
        fetchData()
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

    private fun initVm(){
        cartoonDetailVM.cartoonDetailBeanLd.observe(this){
            Glide.with(this@CartoonDetailActivity)
                .load(it.imgUrl)
                .into(binding.clTop.imgCover)
            Glide.with(this@CartoonDetailActivity)
                .load(it.imgBigUrl)
                .into(binding.clTop.imgCoverBig)
            binding.clTop.tvTitle.text = it.title
            binding.clTop.tvScore.text = String.format("%.1f",it.score)
            if(it.scoreNumbers== null || it.scoreNumbers!! < 50) {
                binding.clTop.tvAppraisalNum.text = getString(R.string.detail_score_num_less_50)
            }else{
                binding.clTop.tvAppraisalNum.text = getString(R.string.detail_score_num,it.scoreNumbers!!)
            }
            binding.clTop.tvDescription.text = it.description
            if(it.status == 1){
                binding.clTop.tvStatus.text = getString(R.string.detail_status_serialization)
            }else{
                binding.clTop.tvStatus.text = getString(R.string.detail_status_over)
            }

            if(it.totalChapter == it.latestChapter){
                binding.clTop.tvMore.text = getString(R.string.detail_more_over)
            }else{
                binding.clTop.tvMore.text = getString(R.string.detail_more_serialization,it.latestChapter)
            }

        }
    }

    private fun fetchData(){
        cartoonDetailVM.fetchDetail()
    }
}