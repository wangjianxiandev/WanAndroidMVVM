package com.wjx.android.wanandroidmvvm.module.system.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.network.dataConvert
import com.wjx.android.wanandroidmvvm.module.system.data.SystemArticleResponse
import com.wjx.android.wanandroidmvvm.module.system.data.SystemTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:09
 */
class SystemRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadSystemTab(liveData: MutableLiveData<BaseResponse<List<SystemTabNameResponse>>>) {
        apiService.loadSystemTab()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun loadSystemArticle(
        pageNum: Int,
        cid: Int?,
        liveData: MutableLiveData<BaseResponse<SystemArticleResponse>>
    ) {
        apiService.loadSystemArticles(pageNum, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    suspend fun loadSystemTabCo(): List<SystemTabNameResponse> {
        return apiService.loadSystemTabCo().dataConvert(loadState)
    }

    suspend fun loadsystemArticleCo(pageNum: Int, cid: Int?): SystemArticleResponse {
        return apiService.loadSystemArticlesCo(pageNum, cid).dataConvert(loadState)
    }
}