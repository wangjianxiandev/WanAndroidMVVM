package com.wjx.android.mvvm.repository

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 8:16
 */
open class BaseRepository {
    private val mCompositeDisposable by lazy { CompositeDisposable() }

    fun addSubscribe(disposable: Disposable) = mCompositeDisposable.add(disposable)

    fun unSubscribe() = mCompositeDisposable.dispose()
}