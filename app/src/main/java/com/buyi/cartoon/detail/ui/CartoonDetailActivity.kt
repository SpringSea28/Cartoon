package com.buyi.cartoon.detail.ui

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityCartoonDetailBinding
import com.buyi.cartoon.detail.ui.adapter.DetailChapterAdapter
import com.buyi.cartoon.detail.ui.adapter.DetailLabelItemAdapter
import com.buyi.cartoon.detail.vm.CartoonDetailVM
import com.buyi.cartoon.home.ui.adapter.HomeItemClass2AdapterBig3
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.utils.ConstantApp
import com.buyi.cartoon.main.utils.Tools
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.math.ceil

class CartoonDetailActivity : BaseActivity<ActivityCartoonDetailBinding>() {

    override val TAG: String
        get() = CartoonDetailActivity::class.simpleName!!

    private var cartoonSimpleInfoBean:CartoonSimpleInfoBean? = null
    private val cartoonDetailVM:CartoonDetailVM by viewModels()
    private val detailChapterAdapter = DetailChapterAdapter()
    private val labelAdapter = DetailLabelItemAdapter()

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
        binding.clTop.title.imgBack.setImageResource(R.mipmap.go_back_round)
        binding.clTop.title.imgBack.setOnClickListener{finish()}
        binding.clTop.title.tvTile.text = null
        binding.clTop.title.imgRight.setImageResource(R.mipmap.share)

        val gridLayoutManager = GridLayoutManager(this, 3)

        detailChapterAdapter.onItemClickListener = {position, chapterInfo ->

        }
        binding.clTop.rcvChapter.layoutManager = gridLayoutManager
        binding.clTop.rcvChapter.setHasFixedSize(true)
        binding.clTop.rcvChapter.adapter = detailChapterAdapter

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        binding.clTop.rcvLabel.layoutManager = flexboxLayoutManager
        binding.clTop.rcvLabel.setHasFixedSize(true)
        binding.clTop.rcvLabel.adapter = labelAdapter

    }

    private fun initVm(){
        cartoonDetailVM.cartoonDetailBeanLd.observe(this){
            Glide.with(this@CartoonDetailActivity)
                .load(it.imgUrl)
                .centerCrop()
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
            binding.clTop.tvAuthor.text = getString(R.string.detail_author,it.author)
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
            detailChapterAdapter.setData(it.chapterInfos)
            labelAdapter.setData(it.labels)
            addStars(it.score)
        }
    }

    private fun fetchData(){
        cartoonDetailVM.fetchDetail()
    }

    private fun addStars(score:Float?){
        binding.clTop.llStar.removeAllViews()
        if(score == null){
            return
        }
        val size = ceil(score/2.0).toInt()
        for(i in 0 until size){
            addStar(this,binding.clTop.llStar)
        }
    }
    private fun addStar(context: Context, linearLayout: LinearLayout){
        val star = ImageView(context)
        val layoutParam = LinearLayout.LayoutParams(
            Tools.dip2px(context,21.0f),
            Tools.dip2px(context,21.0f))
        layoutParam.marginEnd = Tools.dip2px(context,3.0f)
        star.setImageResource(R.mipmap.star_ico)
        linearLayout.addView(star,layoutParam)
    }
}