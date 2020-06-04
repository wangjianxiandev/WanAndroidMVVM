package com.wjx.android.wanandroidmvvm.module.system.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.module.system.model.SystemArticleResponse
import com.wjx.android.wanandroidmvvm.module.system.model.SystemTabNameResponse
import com.wjx.android.wanandroidmvvm.module.system.repository.SystemRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:09
 */
class SystemViewModel(application: Application) : ArticleViewModel<SystemRepository>(application) {
//    val mSystemTabNameData : MutableLiveData<BaseResponse<List<SystemTabNameResponse>>> = MutableLiveData()
//    val mSystemArticleData : MutableLiveData<BaseResponse<SystemArticleResponse>> = MutableLiveData()
//
//    fun loadSystemTab() {
//        mRepository.loadSystemTab(mSystemTabNameData)
//    }
//
//    fun loadSystemArticle(pageNum : Int, cid : Int?) {
//        mRepository.loadSystemArticle(pageNum, cid, mSystemArticleData)
//    }

    val mSystemTabNameData: MutableLiveData<List<SystemTabNameResponse>> = MutableLiveData()
    val mSystemArticleData: MutableLiveData<SystemArticleResponse> = MutableLiveData()

    fun loadSystemTab() {
        initiateRequest({ mSystemTabNameData.value = mRepository.loadSystemTabCo() }, loadState)
    }

    fun loadSystemArticle(pageNum: Int, cid: Int?) {
        initiateRequest({
            mSystemArticleData.value = mRepository.loadsystemArticleCo(pageNum, cid)
        }, loadState)
    }
}