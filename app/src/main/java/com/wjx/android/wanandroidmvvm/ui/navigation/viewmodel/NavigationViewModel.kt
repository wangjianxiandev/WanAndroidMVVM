package com.wjx.android.wanandroidmvvm.ui.navigation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.navigation.data.NavigationTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.navigation.repository.NavigationRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/29
 * Time: 20:10
 */
class NavigationViewModel(application: Application) :
    BaseViewModel<NavigationRepository>(application) {
//    val mNavigationTabData : MutableLiveData<BaseResponse<List<NavigationTabNameResponse>>> = MutableLiveData()
//
//    fun loadNavigationTab() {
//        mRepository.loadNavigationTab(mNavigationTabData)
//    }

    val mNavigationTabData: MutableLiveData<List<NavigationTabNameResponse>> = MutableLiveData()

    fun loadNavigationTab() {
        initiateRequest({mNavigationTabData.value = mRepository.loadNavigationTab()},loadState)
    }
}