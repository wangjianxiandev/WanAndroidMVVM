package com.wjx.android.wanandroidmvvm.module.rank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.module.rank.model.IntegralHistoryListResponse
import com.wjx.android.wanandroidmvvm.module.rank.model.IntegralResponse
import com.wjx.android.wanandroidmvvm.module.rank.model.RankResponse
import com.wjx.android.wanandroidmvvm.module.rank.repository.RankRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/3/29 18:13
 */
class RankViewModel : BaseViewModel<RankRepository>() {
    val mRankListData: MutableLiveData<BaseResponse<RankResponse>> = MutableLiveData()
    val mMeRankInfo: MutableLiveData<BaseResponse<IntegralResponse>> = MutableLiveData()
    val mIntegralHistoryListData: MutableLiveData<BaseResponse<IntegralHistoryListResponse>> =
        MutableLiveData()

    fun loadRankList(pageNum: Int) {
        mRepository.loadRankList(pageNum, mRankListData)
    }

    fun loadMeRankInfo() {
        mRepository.loadMeRankInfo(mMeRankInfo)
    }

    fun loadIntegralHistoryList(pageNum: Int) {
        mRepository.loadIntegralHistoryList(pageNum, mIntegralHistoryListData)
    }
}