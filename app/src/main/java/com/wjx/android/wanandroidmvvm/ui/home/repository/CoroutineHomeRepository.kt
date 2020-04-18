package com.wjx.android.wanandroidmvvm.ui.home.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.repository.CoroutineApiRepository
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/18 11:48
 */
class CoroutineHomeRepository(val loadState: MutableLiveData<State>) : CoroutineApiRepository() {
    suspend fun loadBannerData(liveData: MutableLiveData<BaseResponse<List<BannerResponse>>>) {
        apiCall { executeResponse(apiService.loadBannerData(), liveData, loadState) }
    }
}