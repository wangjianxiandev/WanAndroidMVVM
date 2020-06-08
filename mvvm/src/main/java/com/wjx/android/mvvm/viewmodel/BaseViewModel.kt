package com.wjx.android.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wjx.android.mvvm.repository.BaseRepository
import com.wjx.android.mvvm.common.*
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.common.utils.CommonUtil

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 8:16
 */
open class BaseViewModel<T : BaseRepository>(application: Application) : AndroidViewModel(
    application
) {
    val loadState by lazy {
        MutableLiveData<State>()
    }

    val mRepository : T by lazy {
        (CommonUtil.getClass<T>(this))
            .getDeclaredConstructor(MutableLiveData::class.java)
            .newInstance(loadState)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.unSubscribe()
    }
}