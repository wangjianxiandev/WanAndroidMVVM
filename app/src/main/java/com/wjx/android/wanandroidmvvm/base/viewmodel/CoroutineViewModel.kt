package com.wjx.android.wanandroidmvvm.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.repository.CoroutineRepository
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.common.utils.CommonUtil

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/18 10:53
 */
abstract class CoroutineViewModel<T : CoroutineRepository>(application: Application) : AndroidViewModel(application) {
    val loadState by lazy {
        MutableLiveData<State>()
    }

    /**
     * 通过反射注入mRepository
     */
    val mRepository : T by lazy {
        // 获取对应Repository 实例 (有参构造函数)
        (CommonUtil.getClass<T>(this))
            // 获取构造函数的构造器，传入参数class
            .getDeclaredConstructor(MutableLiveData::class.java)
            // 传入加载状态
            .newInstance(loadState)
    }
}