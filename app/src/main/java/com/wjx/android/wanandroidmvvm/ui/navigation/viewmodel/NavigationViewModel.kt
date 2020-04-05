package com.wjx.android.wanandroidmvvm.ui.navigation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.navigation.data.NavigationTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.navigation.repository.NavigationRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/29
 * Time: 20:10
 */
class NavigationViewModel(application: Application) : BaseViewModel<NavigationRepository>(application) {
    val mNavigationTabData : MutableLiveData<BaseResponse<List<NavigationTabNameResponse>>> = MutableLiveData()

    fun loadNavigationTab() {
        mRepository.loadNavigationTab(mNavigationTabData)
    }
}