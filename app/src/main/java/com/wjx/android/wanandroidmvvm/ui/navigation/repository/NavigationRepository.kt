package com.wjx.android.wanandroidmvvm.ui.navigation.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.ui.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.network.dataConvert
import com.wjx.android.wanandroidmvvm.ui.navigation.data.NavigationTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/29
 * Time: 19:59
 */
class NavigationRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadNavigationTab(liveData: MutableLiveData<BaseResponse<List<NavigationTabNameResponse>>>) {
        apiService.loadNavigationTab()
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

    suspend fun loadNavigationTab(): List<NavigationTabNameResponse> {
        return apiService.loadNavigationTabCo().dataConvert(loadState)
    }
}