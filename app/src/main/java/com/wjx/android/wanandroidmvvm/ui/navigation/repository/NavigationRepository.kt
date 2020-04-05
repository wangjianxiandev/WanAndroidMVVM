package com.wjx.android.wanandroidmvvm.ui.navigation.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.repository.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.navigation.data.NavigationTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/29
 * Time: 19:59
 */
class NavigationRepository (loadState : MutableLiveData<State>) : BaseArticleRepository(loadState) {
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
}