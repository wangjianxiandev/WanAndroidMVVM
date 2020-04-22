package com.wjx.android.wanandroidmvvm.ui.home.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.ui.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.ui.common.data.Article
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.network.dataConvert
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.HomeArticleResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 10:53
 */
class HomeRepository (loadState : MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadBanner(liveData: MutableLiveData<BaseResponse<List<BannerResponse>>>) {
        apiService.loadBanner()
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

    fun loadHomeArticle(pageNum : Int, liveData: MutableLiveData<BaseResponse<HomeArticleResponse>>) {
        apiService.loadHomeArticle(pageNum)
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

    fun loadTopArticle(liveData: MutableLiveData<BaseResponse<List<Article>>>) {
        apiService.loadTopArticle()
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

    // 使用协程 + Retrofit2.6
    suspend fun loadBannerCo() : List<BannerResponse> {
        return withContext(Dispatchers.IO) {
            apiService.loadBannerCo().dataConvert(loadState)
        }
    }

    suspend fun loadTopArticleCo() :List<Article> {
        return withContext(Dispatchers.IO) {
            apiService.loadTopArticleCo().dataConvert(loadState)
        }
    }

    suspend fun loadHomeArticleCo(pageNum : Int) : HomeArticleResponse {
        return withContext(Dispatchers.IO) {
            apiService.loadHomeArticleCo(pageNum).dataConvert(loadState)
        }
    }
}