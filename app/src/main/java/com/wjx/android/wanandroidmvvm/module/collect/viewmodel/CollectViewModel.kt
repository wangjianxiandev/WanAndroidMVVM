package com.wjx.android.wanandroidmvvm.module.collect.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.module.collect.model.CollectResponse
import com.wjx.android.wanandroidmvvm.module.collect.repository.CollectRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/03
 * Time: 14:48
 */
class CollectViewModel :
    ArticleViewModel<CollectRepository>() {
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