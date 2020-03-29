package com.wjx.android.wanandroidmvvm.ui.rank.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.repository.ApiRepository
import com.wjx.android.wanandroidmvvm.base.repository.BaseRepository
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.rank.data.IntegralResponse
import com.wjx.android.wanandroidmvvm.ui.rank.data.RankResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/3/29 18:09
 */
class RankRepository(var loadState: MutableLiveData<State>) : ApiRepository() {

    fun loadRankList(pageNum: Int, liveData: MutableLiveData<BaseResponse<RankResponse>>) {
        apiService.loadRankList(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }

    fun loadMeRankInfo(liveData: MutableLiveData<BaseResponse<IntegralResponse>>) {
        apiService.loadMeRankInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }
}