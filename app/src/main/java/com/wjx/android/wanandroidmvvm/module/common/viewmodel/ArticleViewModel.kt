package com.wjx.android.wanandroidmvvm.module.common.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.common.utils.RoomHelper
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.module.common.data.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:39
 */

abstract class ArticleViewModel<T : ArticleRepository>(application: Application) :
    BaseViewModel<T>(application) {
    // RxJava2
//    var mCollectData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()


//    fun collect(id: Int) {
//        mRepository.collect(id, mCollectData)
//    }

//    fun unCollect(id: Int) {
//        mRepository.unCollect(id, mCollectData)
//    }

    // 使用协程 + Retrofit2.6以上版本
    var mCollectData : MutableLiveData<EmptyResponse> = MutableLiveData()

    fun collectCo(id: Int) {
        initiateRequest({mCollectData.value = mRepository.collectCo(id)},loadState)
    }

    fun unCollectCo(id: Int) {
        initiateRequest({mCollectData.value = mRepository.unCollectCo(id)}, loadState)
    }

    fun addFootPrint(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RoomHelper.insertFootPrint(article)
            }
        }
    }
}