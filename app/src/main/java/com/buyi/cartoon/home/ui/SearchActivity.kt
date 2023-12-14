package com.buyi.cartoon.home.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.buyi.cartoon.databinding.ActivitySearchBinding
import com.buyi.cartoon.home.ui.adapter.SearchContentAdapter
import com.buyi.cartoon.home.ui.adapter.SearchingLabelItemAdapter
import com.buyi.cartoon.home.vm.SearchingVM
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


    private val contentAdapter = SearchContentAdapter()
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

        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val label = binding.edtSearch.text.toString()
                if(label.isNullOrEmpty()){
                    return@setOnEditorActionListener false
                }
                hideSoftInput()
                searchLabel(label)
            }
            return@setOnEditorActionListener false

        }
        binding.edtSearch.requestFocus()
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
            hideSoftInput()
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
                    if(contentAdapter.itemCount == 0){
                        binding.searchEmpty.root.visibility = View.VISIBLE
                    }else{
                        binding.searchEmpty.root.visibility = View.GONE
                    }
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
        contentAdapter.updateKey(label)
        searchingVM.addLabel(label)
        fetchCartoon(label)
    }

    private fun hideSoftInput(){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
        }
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
            searchingVM.fetchCartoonList().collectLatest {
                contentAdapter.submitData(it)

            }
        }
    }


}