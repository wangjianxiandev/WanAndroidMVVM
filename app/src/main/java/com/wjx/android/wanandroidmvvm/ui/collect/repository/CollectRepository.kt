package com.wjx.android.wanandroidmvvm.ui.collect.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.https.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.collect.data.CollectResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/03
 * Time: 14:42
 */
class CollectRepository(loadState: MutableLiveData<State>) : BaseArticleRepository(loadState) {

    fun loadCollectArticle(pageNum: Int, liveData: MutableLiveData<BaseResponse<CollectResponse>>) {
        apiService.loadCollectArticle(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }

    fun unCollect(id: Int, originId: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.unCollect(id, originId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }
}