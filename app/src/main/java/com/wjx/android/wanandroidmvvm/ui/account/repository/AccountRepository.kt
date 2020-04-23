package com.wjx.android.wanandroidmvvm.ui.account.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.base.repository.ApiRepository
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.network.dataConvert
import com.wjx.android.wanandroidmvvm.ui.account.data.LoginResponse
import com.wjx.android.wanandroidmvvm.ui.account.data.RegisterResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:59
 */
class AccountRepository(val loadState: MutableLiveData<State>) : ApiRepository() {
    fun login(
        username: String,
        password: String,
        liveData: MutableLiveData<BaseResponse<LoginResponse>>
    ) {
        apiService.onLogin(username, password)
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

    fun register(
        username: String,
        password: String,
        repassword: String,
        liveData: MutableLiveData<BaseResponse<RegisterResponse>>
    ) {
        apiService.onRegister(username, password, repassword)
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

    // 使用协程 + Retrofit2.6
    suspend fun loginCo(username: String, password: String): LoginResponse {
        return apiService.onLoginCo(username, password).dataConvert(loadState)
    }

    suspend fun registerCo(
        username: String,
        password: String,
        repassword: String
    ): RegisterResponse {
        return apiService.onRegisterCo(username, password, repassword).dataConvert(loadState)
    }
}