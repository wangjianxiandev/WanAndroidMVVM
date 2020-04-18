package com.wjx.android.wanandroidmvvm.base.repository

import com.wjx.android.wanandroidmvvm.network.ApiService
import com.wjx.android.wanandroidmvvm.network.RetrofitFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/18 11:00
 */
abstract class CoroutineApiRepository : CoroutineRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}