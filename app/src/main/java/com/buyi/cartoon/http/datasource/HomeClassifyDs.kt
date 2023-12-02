package com.buyi.cartoon.http.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.buyi.cartoon.http.bean.DemoReqData
import com.buyi.cartoon.http.respority.DataRespority

class HomeClassifyDs: PagingSource<Int, DemoReqData.DataBean.DatasBean>() {
    override fun getRefreshKey(state: PagingState<Int, DemoReqData.DataBean.DatasBean>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DemoReqData.DataBean.DatasBean> {
        return try {
            //页码未定义置为1
            var currentPage = params.key ?: 1
            //仓库层请求数据
            var demoReqData = DataRespority().loadData(currentPage)
            //当前页码 小于 总页码 页面加1
            var nextPage = if (currentPage < demoReqData?.data?.pageCount ?: 0) {
                currentPage + 1
            } else {
                //没有更多数据
                null
            }
            if (demoReqData != null) {
                LoadResult.Page(
                    data = demoReqData.data.datas,
                    prevKey = null,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(throwable = Throwable())
            }
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }

}