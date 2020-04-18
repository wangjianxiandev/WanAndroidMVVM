package com.wjx.android.wanandroidmvvm.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.base.viewmodel.CoroutineViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.repository.CoroutineHomeRepository
import com.wjx.android.wanandroidmvvm.ui.home.repository.HomeRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/18 11:49
 */
class CoroutinHomeViewModel(application: Application) : CoroutineViewModel<CoroutineHomeRepository>(application) {
    val mBannerData: MutableLiveData<BaseResponse<List<BannerResponse>>> = MutableLiveData()

    fun loadBannerData() {
        viewModelScope.launch {
            try {
                mRepository.loadBannerData(mBannerData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}