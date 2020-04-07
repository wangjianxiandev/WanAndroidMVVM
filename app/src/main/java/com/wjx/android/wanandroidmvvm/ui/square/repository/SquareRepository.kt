package com.wjx.android.wanandroidmvvm.ui.square.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.ui.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.ui.square.data.SquareResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 17:05
 */
class SquareRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadSquareArticle(pageNum : Int, liveData : MutableLiveData<BaseResponse<SquareResponse>>) {
        apiService.loadSquareArticle(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun addShareArticle(title : String, link : String, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.addShareArticle(title, link)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }
}