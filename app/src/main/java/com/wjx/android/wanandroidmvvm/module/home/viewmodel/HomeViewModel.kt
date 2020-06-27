package com.wjx.android.wanandroidmvvm.module.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.module.common.model.Article
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.module.home.model.BannerResponse
import com.wjx.android.wanandroidmvvm.module.home.model.HomeArticleResponse
import com.wjx.android.wanandroidmvvm.module.home.repository.HomeRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 10:49
 */
class HomeViewModel :
    ArticleViewModel<HomeRepository>() {
    // Rxjava2版本
//    val mBannerData: MutableLiveData<BaseResponse<List<BannerResponse>>> = MutableLiveData()
//    val mHomeArticleData: MutableLiveData<BaseResponse<HomeArticleResponse>> = MutableLiveData()
//    val mTopArticleData: MutableLiveData<BaseResponse<List<Article>>> = MutableLiveData()

//    fun loadBanner() {
//        mRepository.loadBanner(mBannerData)
//    }

//    fun loadHomeArticleData(pageNum: Int) {
//        if (pageNum == 0) {
//            mRepository.loadTopArticle(mTopArticleData)
//        }
//        mRepository.loadHomeArticle(pageNum, mHomeArticleData)
//    }

//    fun loadTopArticle() {
//        mRepository.loadTopArticle(mTopArticleData)
//    }

    // 使用协程 + Retrofit2.6以上版本
    var mBannerData: MutableLiveData<List<BannerResponse>> = MutableLiveData()
    var mHomeArticleData: MutableLiveData<HomeArticleResponse> = MutableLiveData()
    var mTopArticleData: MutableLiveData<List<Article>> = MutableLiveData()

    fun loadBannerCo() {
        initiateRequest({
            mBannerData.value = mRepository.loadBannerCo()
        }, loadState)
    }

    fun loadHomeArticleDataCo(pageNum: Int) {
        initiateRequest({
            if (pageNum == 0) {
                mTopArticleData.value = mRepository.loadTopArticleCo()
            }
            mHomeArticleData.value = mRepository.loadHomeArticleCo(pageNum)
        }, loadState)
    }
}