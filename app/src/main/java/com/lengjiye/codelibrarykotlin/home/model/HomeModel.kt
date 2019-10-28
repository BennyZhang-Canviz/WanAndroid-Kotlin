package com.lengjiye.codelibrarykotlin.home.model

import androidx.lifecycle.LifecycleOwner
import com.lengjiye.codelibrarykotlin.home.bean.Article
import com.lengjiye.codelibrarykotlin.home.bean.HomeBean
import com.lengjiye.codelibrarykotlin.home.service.HomeService
import com.lengjiye.network.BaseModel
import com.lengjiye.network.HttpResultFunc
import com.lengjiye.network.ServiceHolder
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers

class HomeModel : BaseModel() {
    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = HomeModel()
    }

    private fun getService(): HomeService? {
        return ServiceHolder.singleton.getService(HomeService::class.java)
    }

    fun getHomeListData(lifecycleOwner: LifecycleOwner, page: Int, observer: Observer<Article>) {
        // 合并请求
        val observableList = getService()?.getArticleList(page)?.map(HttpResultFunc())
        observableList?.let { makeSubscribe(lifecycleOwner, it, observer) }
    }

    /**
     * 获取首页置顶和第一页的数据
     */
    fun getHomeTopAndFirstListData(lifecycleOwner: LifecycleOwner, observer: Observer<List<HomeBean>>) {
        val observableTop = getService()?.getArticleTopList()?.map(HttpResultFunc())
        val observableList = getService()?.getArticleList(0)?.map(HttpResultFunc())
        val observableData: Observable<List<HomeBean>>
        observableList?.let {
            observableData = it.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).map { t -> t.datas }
            val observable = Observable.concat(observableTop, observableData)
            makeSubscribe(lifecycleOwner, observable, observer)
        }
    }
}
