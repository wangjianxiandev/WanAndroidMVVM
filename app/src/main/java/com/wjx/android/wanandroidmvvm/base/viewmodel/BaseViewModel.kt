package com.wjx.android.wanandroidmvvm.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.repository.BaseRepository
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.base.utils.Util

/**
 * Created with Android Studio.
 * Description: 使用AndroidViewModel可以直接访问application
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 15:26
 */

open class BaseViewModel<T : BaseRepository>(application : Application) : AndroidViewModel(application) {
    val loadState by lazy {
        MutableLiveData<State>()
    }

    /**
     * 通过反射注入mRepository
     */
    val mRepository : T by lazy {
        // 获取对应Repository 实例 (有参构造函数)
        (Util.getClass<T>(this))
                // 获取构造函数的构造器，传入参数class
            .getDeclaredConstructor(MutableLiveData::class.java)
                // 传入加载状态
            .newInstance(loadState)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.unSubscribe()
    }
}