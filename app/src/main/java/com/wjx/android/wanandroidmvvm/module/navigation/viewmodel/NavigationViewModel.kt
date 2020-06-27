package com.wjx.android.wanandroidmvvm.module.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.module.navigation.model.NavigationTabNameResponse
import com.wjx.android.wanandroidmvvm.module.navigation.repository.NavigationRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/29
 * Time: 20:10
 */
class NavigationViewModel :
    BaseViewModel<NavigationRepository>() {
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