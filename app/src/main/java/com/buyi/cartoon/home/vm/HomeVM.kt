package com.buyi.cartoon.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.R
import com.buyi.cartoon.home.bean.HomeRecommendItemBean
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean

class HomeVM(application: Application) : AndroidViewModel(application) {

    val recommendItemBeanList = MutableLiveData<List<HomeRecommendItemBean>>()

    val testUrl = arrayOf(
        "https://img1.baidu.com/it/u=225041176,855892897&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422",
"https://img2.baidu.com/it/u=1665640482,1824915280&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img0.baidu.com/it/u=3145022701,3943645695&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889",
        "https://img2.baidu.com/it/u=710071810,2487788150&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=800",
        "https://img2.baidu.com/it/u=2526519423,798155762&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800",
        "https://img2.baidu.com/it/u=3574827082,3267311681&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800",
        "https://img0.baidu.com/it/u=3279376560,3507705273&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img2.baidu.com/it/u=2242228201,4108939845&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800"
        )

    fun fetchRecommendList(){
        val recommendList = ArrayList<HomeRecommendItemBean>()
        val cartoonList = ArrayList<CartoonSimpleInfoBean>()
        for (i in 0 until 8){
            val cartoonSimpleInfoBean = CartoonSimpleInfoBean()
            var index = (Math.random() * 10).toInt()
            if(index>8){
                index = 7
            }
            cartoonSimpleInfoBean.id = i
            cartoonSimpleInfoBean.title = "测试标题$i"
            cartoonSimpleInfoBean.imgUrl = testUrl[index]
            cartoonList.add(cartoonSimpleInfoBean)
        }

        val item1 = HomeRecommendItemBean()
        item1.id = 1
        item1.title = getApplication<Application>().getString(R.string.home_item_1_tile)
        item1.cartoonsList = cartoonList
        recommendList.add(item1)

        val item2 = HomeRecommendItemBean()
        item2.id = 2
        item2.title = getApplication<Application>().getString(R.string.home_item_2_tile)
        item2.cartoonsList = cartoonList
        recommendList.add(item2)

        val item3 = HomeRecommendItemBean()
        item3.id = 2
        item3.title = getApplication<Application>().getString(R.string.home_item_3_tile)
        item3.cartoonsList = cartoonList
        recommendList.add(item3)

        recommendItemBeanList.postValue(recommendList)
    }

}