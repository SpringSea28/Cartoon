package com.buyi.cartoon.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityClassifyBinding
import com.buyi.cartoon.home.ui.adapter.HomeClassifyNavItemAdapter
import com.buyi.cartoon.home.vm.ClassifyVM
import com.buyi.cartoon.main.base.BaseActivity

class ClassifyActivity : BaseActivity<ActivityClassifyBinding>() {

    override val TAG: String
        get() = ClassifyActivity::class.simpleName!!

    private val classifyVm:ClassifyVM by viewModels()
    private val classAdapter:HomeClassifyNavItemAdapter = HomeClassifyNavItemAdapter()

    private var typeSel = ClassifyVM.TYPE_WHOLE
    private var statusSel = ClassifyVM.STATUS_WHOLE

    override fun getBindingView(): ActivityClassifyBinding {
        return ActivityClassifyBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
        getData()
    }

    private fun initUi(){
        setPadding(true)
        binding.title.tvTile.text = getString(R.string.home_nav_classify)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rcvNav.layoutManager = linearLayoutManager
        binding.rcvNav.setHasFixedSize(true)
        binding.rcvNav.adapter = classAdapter
        classAdapter.onItemClickListener = { pos,selId ->
            classAdapter.updateSel(selId)
        }

        updateTabTypeUi(typeSel)
        binding.tvTabWhole.setOnClickListener { onTabTypeClick(ClassifyVM.TYPE_WHOLE) }
        binding.tvTabUniversal.setOnClickListener { onTabTypeClick(ClassifyVM.TYPE_UNIVERSAL) }
        binding.tvTabBoy.setOnClickListener { onTabTypeClick(ClassifyVM.TYPE_BOY) }
        binding.tvTabGirl.setOnClickListener { onTabTypeClick(ClassifyVM.TYPE_GIRL) }

        updateTabStatusUi(statusSel)
        binding.tvTabStatusWhole.setOnClickListener { onTabStatusClick(ClassifyVM.STATUS_WHOLE) }
        binding.tvTabSerialization.setOnClickListener { onTabStatusClick(ClassifyVM.STATUS_SERIALIZATION) }
        binding.tvTabStatusOver.setOnClickListener { onTabStatusClick(ClassifyVM.STATUS_OVER) }
    }

    private fun onTabTypeClick(type:Int){
        typeSel = type
        updateTabTypeUi(type)
        classifyVm.fetchCartoonList(typeSel,statusSel)
    }
    private fun updateTabTypeUi(type:Int){
        binding.tvTabWhole.isSelected = false
        binding.tvTabUniversal.isSelected = false
        binding.tvTabBoy.isSelected = false
        binding.tvTabGirl.isSelected = false
        when(type){
            ClassifyVM.TYPE_WHOLE -> binding.tvTabWhole.isSelected = true
            ClassifyVM.TYPE_UNIVERSAL -> binding.tvTabUniversal.isSelected = true
            ClassifyVM.TYPE_BOY -> binding.tvTabBoy.isSelected = true
            ClassifyVM.TYPE_GIRL -> binding.tvTabGirl.isSelected = true
        }
    }

    private fun onTabStatusClick(status:Int){
        statusSel = status
        updateTabStatusUi(status)
        classifyVm.fetchCartoonList(typeSel,statusSel)
    }
    private fun updateTabStatusUi(status:Int){
        binding.tvTabStatusWhole.isSelected = false
        binding.tvTabSerialization.isSelected = false
        binding.tvTabStatusOver.isSelected = false
        when(status){
            ClassifyVM.STATUS_WHOLE -> binding.tvTabStatusWhole.isSelected = true
            ClassifyVM.STATUS_SERIALIZATION -> binding.tvTabSerialization.isSelected = true
            ClassifyVM.STATUS_OVER -> binding.tvTabStatusOver.isSelected = true
        }
    }

    private fun initVm(){
        classifyVm.classListLD.observe(this){
            classAdapter.setData(it)
            if(it.isNotEmpty()) {
                it[0].id?.let { it1 -> classAdapter.updateSel(it1) }
            }
        }
    }

    private fun getData(){
        classifyVm.fetchClassList()
        classifyVm.fetchCartoonList(typeSel,statusSel)
    }

}