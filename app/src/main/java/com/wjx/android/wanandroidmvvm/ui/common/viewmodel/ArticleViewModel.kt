package com.wjx.android.wanandroidmvvm.ui.common.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.ui.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:39
 */

abstract class ArticleViewModel<T : ArticleRepository>(application: Application) : BaseViewModel<T>(application) {
    var mCollectData : MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun collect(id : Int) {
        mRepository.collect(id, mCollectData)
    }

    fun unCollect(id : Int) {
        mRepository.unCollect(id , mCollectData)
    }

}