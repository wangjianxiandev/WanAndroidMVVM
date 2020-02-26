package com.wjx.android.wanandroidmvvm.base.BaseArticle

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.https.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.repository.ApiRepository
import com.wjx.android.wanandroidmvvm.base.state.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:40
 */
abstract class BaseArticleRepository(val loadState: MutableLiveData<State>) : ApiRepository() {

    fun collect(id: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.collect(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }

    fun unCollect(id: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.unCollect(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }
}