package com.wjx.android.wanandroidmvvm.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.ui.common.data.Article
import com.wjx.android.wanandroidmvvm.ui.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.HomeArticleResponse
import com.wjx.android.wanandroidmvvm.ui.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 10:49
 */
class HomeViewModel(application: Application) :
    ArticleViewModel<HomeRepository>(application) {
    val mBannerData: MutableLiveData<BaseResponse<List<BannerResponse>>> = MutableLiveData()
    val mHomeArticleData: MutableLiveData<BaseResponse<HomeArticleResponse>> = MutableLiveData()
    val mTopArticleData: MutableLiveData<BaseResponse<List<Article>>> = MutableLiveData()

    fun loadBanner() {
        mRepository.loadBanner(mBannerData)
    }

    fun loadBannerData() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    mRepository.loadBannerData(mBannerData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadHomeArticleData(pageNum: Int) {
        if (pageNum == 0) {
            mRepository.loadTopArticle(mTopArticleData)
        }
        mRepository.loadHomeArticle(pageNum, mHomeArticleData)
    }

//    fun loadTopArticle() {
//        mRepository.loadTopArticle(mTopArticleData)
//    }
}