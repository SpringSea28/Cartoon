package com.buyi.cartoon.home.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.R
import com.buyi.cartoon.databinding.ActivityRankingBinding
import com.buyi.cartoon.databinding.ActivitySearchBinding
import com.buyi.cartoon.home.ui.adapter.HomeRankingContentAdapter
import com.buyi.cartoon.home.ui.adapter.HomeRankingLabelItemAdapter
import com.buyi.cartoon.home.ui.adapter.SearchingLabelItemAdapter
import com.buyi.cartoon.home.vm.RankingVM
import com.buyi.cartoon.home.vm.SearchingVM
import com.buyi.cartoon.http.bean.ClassifyInfoBean
import com.buyi.cartoon.main.base.BaseActivity
import com.example.pagingdatademo.mvvm.ui.adapter.LoadStateFooterAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override val TAG: String
        get() = SearchActivity::class.simpleName!!

    private val searchingVM:SearchingVM by viewModels()
    private val labelAdapter: SearchingLabelItemAdapter = SearchingLabelItemAdapter()


    private val contentAdapter = HomeRankingContentAdapter()
    private val footerAdapter = LoadStateFooterAdapter {
        contentAdapter.retry()
    }

    override fun getBindingView(): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun initAllMembersData(savedInstanceState: Bundle?) {
        initUi()
        initVm()
        getData()
    }

    private fun initUi(){
        setPadding(true)
        binding.tvCancel.setOnClickListener { finish() }

        binding.edtSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()){
                    binding.imgSearchIcon.isSelected = false
                    binding.imgCancel.visibility = View.GONE
                    if(searchingVM.historyLabelLd.value?.isNotEmpty() == true) {
                        binding.clHistory.visibility = View.VISIBLE
                    }
                }else{
                    binding.imgSearchIcon.isSelected = true
                    binding.imgCancel.visibility = View.VISIBLE
                }
            }
        })
        binding.edtSearch.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyCode != KeyEvent.ACTION_UP) {
                val label = binding.edtSearch.text.toString()
                if(label.isNullOrEmpty()){
                    return@setOnKeyListener false
                }
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (inputMethodManager.isActive) {
                    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
                }
                searchLabel(label)
                return@setOnKeyListener true
            } else return@setOnKeyListener false
        }
        binding.imgCancel.setOnClickListener {
            binding.edtSearch.text = null
        }
        binding.imgHistoryClear.setOnClickListener {
            searchingVM.clearLabel()
        }

        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        binding.rcvHistory.layoutManager = flexboxLayoutManager
        binding.rcvHistory.setHasFixedSize(true)
        binding.rcvHistory.adapter = labelAdapter
        labelAdapter.onItemClickListener = { pos, label ->
            searchLabel(label)
        }


        binding.rcvCartoon.layoutManager= LinearLayoutManager(this)
        binding.rcvCartoon.adapter = contentAdapter.withLoadStateFooter(footerAdapter)



        contentAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.Loading -> {
                    Log.d(TAG,"refresh: loading")
                }
                is LoadState.NotLoading -> {
                    Log.d(TAG,"refresh: NotLoading")
                }
                is LoadState.Error -> {Log.d(TAG,"refresh: Error")
                }
            }
            when(it.append){
                is LoadState.Loading -> {Log.d(TAG,"append: loading")}
                is LoadState.NotLoading -> { Log.d(TAG, "append: NotLoading") }
                is LoadState.Error -> {Log.d(TAG,"append: Error")
                }
            }
        }

    }

    private fun searchLabel(label:String){
        binding.clHistory.visibility = View.GONE
        searchingVM.addLabel(label)
        fetchCartoon(label)
    }


    private fun initVm(){
        searchingVM.historyLabelLd.observe(this){

            if(it.isNullOrEmpty()){

                binding.clHistory.visibility = View.GONE
            }else{
                labelAdapter.setData(it)
            }
        }
    }

    private var dataJob: Job? = null
    private fun getData(){
        searchingVM.fetchLabel()
    }

    private fun fetchCartoon(key:String){
        dataJob?.cancel()
        dataJob = lifecycleScope.launch {
            searchingVM.flow.collectLatest {
                contentAdapter.submitData(it)
            }
        }
    }


}