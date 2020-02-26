package com.wjx.android.wanandroidmvvm.base.repository

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created with Android Studio.
 * Description: 仓库对数据订阅事件进行管理
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 14:40
 */
open class BaseRepository {
    private val mCompositeDisposable by lazy { CompositeDisposable() }

    fun addSubscribe(disposable: Disposable) = mCompositeDisposable.add(disposable)

    fun unSubscribe() = mCompositeDisposable.dispose()
}