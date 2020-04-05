package com.wjx.android.wanandroidmvvm.base.basearticle.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.respository.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.https.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:39
 */

abstract class BaseArticleViewModel<T : BaseArticleRepository>(application: Application) : BaseViewModel<T>(application) {
    var mCollectData : MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun collect(id : Int) {
        mRepository.collect(id, mCollectData)
    }

    fun unCollect(id : Int) {
        mRepository.unCollect(id , mCollectData)
    }

}