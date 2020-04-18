package com.wjx.android.wanandroidmvvm.base.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.common.state.StateType
import com.wjx.android.wanandroidmvvm.common.state.UserInfo
import com.wjx.android.wanandroidmvvm.common.utils.Constant
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/17 23:17
 */
abstract class CoroutineRepository {
    suspend fun apiCall(call: suspend () -> Unit) {
        try {
            call()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun <T : BaseResponse<*>> executeResponse(
        response: T,
        liveData: MutableLiveData<T>,
        loadState: MutableLiveData<State>
    ) {
        coroutineScope {
            when (response.errorCode) {
                Constant.SUCCESS -> {
                    if (response.data is List<*>) {
                        if ((response.data as List<*>).isEmpty()) {
                            loadState.postValue(State(StateType.EMPTY))
                        }
                    }
                    loadState.postValue(State(StateType.SUCCESS))
                    liveData.postValue(response)
                }
                Constant.NOT_LOGIN -> {
                    UserInfo.instance.logoutSuccess()
                    loadState.postValue(State(StateType.ERROR, message = "请重新登录"))
                }
                else -> {
                    loadState.postValue(State(StateType.ERROR, message = response.errorMessage))
                }
            }
        }
    }
}