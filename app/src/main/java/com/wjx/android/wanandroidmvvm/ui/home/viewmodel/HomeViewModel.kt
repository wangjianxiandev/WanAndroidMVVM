package com.wjx.android.wanandroidmvvm.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.data.Article
import com.wjx.android.wanandroidmvvm.base.basearticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.HomeArticleResponse
import com.wjx.android.wanandroidmvvm.ui.home.repository.HomeRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 10:49
 */
class HomeViewModel(application: Application) :
    BaseArticleViewModel<HomeRepository>(application) {
    val mBannerData : MutableLiveData<BaseResponse<List<BannerResponse>>> = MutableLiveData()
    val mHomeArticleData : MutableLiveData<BaseResponse<HomeArticleResponse>> = MutableLiveData()
    val mTopArticleData : MutableLiveData<BaseResponse<List<Article>>> = MutableLiveData()

    fun loadBanner() {
        mRepository.loadBanner(mBannerData)
    }

    fun loadHomeArticleData(pageNum : Int) {
        if (pageNum == 0) {
            mRepository.loadTopArticle(mTopArticleData)
        }
        mRepository.loadHomeArticle(pageNum, mHomeArticleData)
    }

//    fun loadTopArticle() {
//        mRepository.loadTopArticle(mTopArticleData)
//    }
}