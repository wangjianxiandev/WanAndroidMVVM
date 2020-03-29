package com.wjx.android.wanandroidmvvm.ui.rank.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.rank.data.IntegralResponse
import com.wjx.android.wanandroidmvvm.ui.rank.data.RankResponse
import com.wjx.android.wanandroidmvvm.ui.rank.repository.RankRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/3/29 18:13
 */
class RankViewModel(application: Application) : BaseViewModel<RankRepository>(application) {
    val mRankListData: MutableLiveData<BaseResponse<RankResponse>> = MutableLiveData()
    val mMeRankInfo: MutableLiveData<BaseResponse<IntegralResponse>> = MutableLiveData()

    fun loadRankList(pageNum: Int) {
        mRepository.loadRankList(pageNum, mRankListData)
    }

    fun loadMeRankInfo() {
        mRepository.loadMeRankInfo(mMeRankInfo)
    }
}