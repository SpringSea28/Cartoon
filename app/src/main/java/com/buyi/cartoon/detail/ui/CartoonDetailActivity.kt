package com.buyi.cartoon.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.buyi.cartoon.R
import com.buyi.cartoon.account.ui.LoginActivity
import com.buyi.cartoon.account.util.UserManager
import com.buyi.cartoon.databinding.ActivityCartoonDetailBinding
import com.buyi.cartoon.detail.ui.adapter.DetailChapterAdapter
import com.buyi.cartoon.detail.ui.adapter.DetailCommentAdapter
import com.buyi.cartoon.detail.ui.adapter.DetailLabelItemAdapter
import com.buyi.cartoon.detail.vm.CartoonDetailVM
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import com.buyi.cartoon.main.base.BaseActivity
import com.buyi.cartoon.main.dialog.ConfirmDialog
import com.buyi.cartoon.main.dialog.ToastDialog
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
    private val detailChapterAdapter = DetailChapterAdapter()
    private val labelAdapter = DetailLabelItemAdapter()
    private val detailCommentAdapter = DetailCommentAdapter()

    private val cartoonDetailVM:CartoonDetailVM by viewModels()
    private var chapter:Int = -1

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
        chapter = intent.getIntExtra(ConstantApp.INTENT_CHAPTER,1)
    }

    private fun initUi(){
        setPadding(false)
        binding.clTop.title.imgBack.setImageResource(R.mipmap.go_back_white)
        binding.clTop.title.imgBack.setOnClickListener{finish()}
        binding.clTop.title.tvTile.text = null
        binding.clTop.title.imgRight.setImageResource(R.mipmap.share)

        val gridLayoutManager = GridLayoutManager(this, 3)
        detailChapterAdapter.onItemClickListener = {position, chapterInfo ->
            cartoonDetailVM.readingChapterLd.postValue(chapterInfo.id)
            goReading(cartoonSimpleInfoBean?.id,chapterInfo.id)
        }
        binding.clTop.rcvChapter.layoutManager = gridLayoutManager
        binding.clTop.rcvChapter.setHasFixedSize(true)
        binding.clTop.rcvChapter.adapter = detailChapterAdapter

        binding.clTop.tvMore.setOnClickListener {
            goDirectory()
        }

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        binding.clTop.rcvLabel.layoutManager = flexboxLayoutManager
        binding.clTop.rcvLabel.setHasFixedSize(true)
        binding.clTop.rcvLabel.adapter = labelAdapter


        binding.clBottom.tvChapter.text = getString(R.string.detail_start_read,chapter)

        binding.clBottom.clCollect.setOnClickListener {
            if(cartoonDetailVM.collectLd.value == true){
                cancelCollect()
            }else {
                cartoonDetailVM.setCollect(cartoonSimpleInfoBean)
            }
        }

        binding.clBottom.tvChapter.setOnClickListener {
            goReading(cartoonSimpleInfoBean?.id,cartoonDetailVM.readingChapterLd.value)
        }


        val linearLayoutManager = LinearLayoutManager(this)
        binding.rcvComment.layoutManager = linearLayoutManager
        binding.rcvComment.setHasFixedSize(true)
        binding.rcvComment.adapter = detailCommentAdapter
        detailCommentAdapter.onItemClickListener = {position, commentBean ->
            goWriteComment()
        }

        detailCommentAdapter.onItemMoreClickListener = {position, commentBean ->

        }
        detailCommentAdapter.onItemLikeClickListener = {position, commentBean,like ->

        }

        binding.refresh.setOnLoadMoreListener {
            cartoonDetailVM.fetchComment()
        }

    }

    private fun initVm(){
        cartoonDetailVM.readingChapterLd.postValue(chapter)
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
        cartoonDetailVM.readingChapterLd.observe(this){
            this.chapter = it
            detailChapterAdapter.updateReadingChapter(it)
            binding.clBottom.tvChapter.text = getString(R.string.detail_start_read,it)
        }

        cartoonDetailVM.collectLd.observe(this){
            binding.clBottom.imgCollect.isSelected = it
        }
        cartoonDetailVM.collectSucLd.observe(this){
            if(it){
                val dialog = ToastDialog()
                dialog.message = getString(R.string.detail_collect_suc)
                dialog.showDialog(supportFragmentManager)
            }
        }
        cartoonDetailVM.commentListLd.observe(this){
            binding.refresh.finishLoadMore()
            detailCommentAdapter.setData(it)
        }
    }

    private fun fetchData(){
        cartoonDetailVM.fetchDetail()
        cartoonDetailVM.fetchComment()
        cartoonDetailVM.fetchCollect(cartoonSimpleInfoBean)
        cartoonDetailVM.fetchReadingChapter(cartoonSimpleInfoBean)
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

    private fun cancelCollect(){
        val dialog = ConfirmDialog()
        dialog.title = getString(R.string.detail_collect_cancel_title)
        dialog.leftStr = getString(R.string.detail_collect_cancel)
        dialog.rightStr = getString(R.string.detail_collect_confirm)
        dialog.leftListener = {
            cartoonDetailVM.setCollect(cartoonSimpleInfoBean)
        }
        dialog.showDialog(supportFragmentManager)
    }

    private fun goMoreComment(){

    }

    private fun goWriteComment(){
        if(UserManager.getToken().isNullOrBlank()){
            loginLaunch.launch(Intent(this, LoginActivity::class.java))
        }else{
            writeCommentLaunch.launch(Intent(this,WriteCommentActivity::class.java))
        }
    }

    private val writeCommentLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val result = it.data?.getBooleanExtra(WriteCommentActivity.EXTRA_WRITE_COMMENT,false)
            if(result == true){
                cartoonDetailVM.fetchComment()
            }
        }
    }

    private val loginLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == AppCompatActivity.RESULT_OK){
            val login = it.data?.getBooleanExtra(LoginActivity.EXTRA_LOGIN_RESULT,false)
            if(login == true){
                goWriteComment()
            }
        }
    }

    private val readingLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val chapter = it.data?.getIntExtra(ConstantApp.INTENT_CHAPTER,-1)
            if(chapter!= null && chapter > 0){
                cartoonDetailVM.readingChapterLd.postValue(chapter)
            }
        }
    }

    private fun goReading(cartoonId:Int?,chapter:Int?){
        val intent = Intent(this@CartoonDetailActivity,ReadingActivity::class.java)
        intent.putExtra(ConstantApp.INTENT_CHAPTER,chapter)
        intent.putExtra(ConstantApp.INTENT_CARTOON_ID,cartoonId)
        intent.putExtra(ConstantApp.INTENT_CARTOON_DETAIL,cartoonSimpleInfoBean)
        readingLaunch.launch(intent)
    }

    private fun goDirectory(){
        val intent = Intent(this,ChapterDirectoryActivity::class.java)
        intent.putExtra(ConstantApp.INTENT_CHAPTER,chapter)
        directoryLaunch.launch(intent)
    }

    private val directoryLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val chapter = it.data?.getIntExtra(ConstantApp.INTENT_CHAPTER,-1)
            if(chapter!= null && chapter > 0){
                cartoonDetailVM.readingChapterLd.postValue(chapter)
                goReading(cartoonSimpleInfoBean?.id,chapter)
            }
        }
    }


}