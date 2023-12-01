package com.buyi.cartoon.home.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.buyi.cartoon.R
import com.buyi.cartoon.home.bean.HomeRecommendItemBean
import com.buyi.cartoon.http.bean.CartoonSimpleInfoBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class HomeVM(application: Application) : AndroidViewModel(application) {

    val recommendItemBeanListLD = MutableLiveData<List<HomeRecommendItemBean>>()
    val refreshFinishLD = MutableLiveData<Boolean>()

    val recommendItemBeanLD = MutableLiveData<HomeRecommendItemBean>()

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
        val subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                test()
            }
    }

    fun fetchRecommendList(id:Int?){
        if(id == null){
            return
        }
        testReplace(id)
    }


    /**
     * 判断当前网络是否可用(6.0以上版本)
     * 实时
     * @param context
     * @return
     */
    fun isNetSystemUsable(context: Context): Boolean {
        var isNetUsable = false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            if (networkCapabilities != null) {
                isNetUsable =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        }
        return isNetUsable
    }


    private fun test(){
        val recommendList = ArrayList<HomeRecommendItemBean>()
        val cartoonList = ArrayList<CartoonSimpleInfoBean>()
        for (i in 0 until 8){
            val cartoonSimpleInfoBean = CartoonSimpleInfoBean()
            var index = (Math.random() * 10).toInt()
            if(index>=8){
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
        item3.id = 3
        item3.title = getApplication<Application>().getString(R.string.home_item_3_tile)
        item3.cartoonsList = cartoonList
        recommendList.add(item3)

        val item4 = HomeRecommendItemBean()
        item4.id = 4
        item4.title = getApplication<Application>().getString(R.string.home_item_4_tile)
        item4.cartoonsList = cartoonList
        recommendList.add(item4)

        val item5 = HomeRecommendItemBean()
        item5.id = 5
        item5.title = getApplication<Application>().getString(R.string.home_item_5_tile)
        item5.cartoonsList = cartoonList
        recommendList.add(item5)

        recommendItemBeanListLD.postValue(recommendList)
//        network = !network
        refreshFinishLD.postValue(network)
    }
    var network = true

    fun testReplace(id:Int){
        val homeRecommendItemBeans = recommendItemBeanListLD.value
        if(homeRecommendItemBeans == null){
            return
        }
        val cartoonList = ArrayList<CartoonSimpleInfoBean>()
        for (i in 0 until 8){
            val cartoonSimpleInfoBean = CartoonSimpleInfoBean()
            var index = (Math.random() * 10).toInt()
            if(index>=8){
                index = 7
            }
            cartoonSimpleInfoBean.id = i
            cartoonSimpleInfoBean.title = "测试标题$i"
            cartoonSimpleInfoBean.imgUrl = testUrl[index]
            cartoonList.add(cartoonSimpleInfoBean)
        }

        var recommendItemBean :HomeRecommendItemBean? = null
        for (item in homeRecommendItemBeans.iterator()){
            if(item.id == id){
                item.cartoonsList = cartoonList
                recommendItemBean = item
            }
        }
        if(recommendItemBean == null){
            return
        }

        recommendItemBeanLD.postValue(recommendItemBean!!)
    }

}