package com.wjx.android.wanandroidmvvm.ui.collect.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.https.EmptyResponse
import com.wjx.android.wanandroidmvvm.ui.collect.data.CollectResponse
import com.wjx.android.wanandroidmvvm.ui.collect.repository.CollectRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/03
 * Time: 14:48
 */
class CollectViewModel(application: Application) :
    BaseArticleViewModel<CollectRepository>(application) {
    val mCollectArticleData: MutableLiveData<BaseResponse<CollectResponse>> = MutableLiveData()
    val mAddCollectData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mUnCollectData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadCollectArticle(pageNum: Int) {
        mRepository.loadCollectArticle(pageNum, mCollectArticleData)
    }

    fun addCollectArticle(title: String, author: String, link: String) {
        mRepository.addCollectArticle(title, author, link, mAddCollectData)
    }

    fun unCollect(id: Int, originId: Int) {
        mRepository.unCollect(id, originId, mUnCollectData)
    }
}