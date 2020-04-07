package com.wjx.android.wanandroidmvvm.base.repository

import com.wjx.android.wanandroidmvvm.network.ApiService
import com.wjx.android.wanandroidmvvm.network.RetrofitFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:40
 */
abstract class ApiRepository : BaseRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}