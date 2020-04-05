package com.wjx.android.wanandroidmvvm.ui.meshare.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.repository.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.https.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.meshare.data.MeShareArticleResponse
import com.wjx.android.wanandroidmvvm.ui.meshare.data.MeShareResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 17:34
 */
class MeShareRepository(loadState: MutableLiveData<State>) : BaseArticleRepository(loadState) {
    fun loadShareArticle(pageNum : Int, liveData : MutableLiveData<BaseResponse<MeShareResponse<MeShareArticleResponse>>>) {
        apiService.loadMeShareArticle(pageNum)
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

    fun deleteShareArticle(id: Int, liveData : MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.deleteShareArticle(id)
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