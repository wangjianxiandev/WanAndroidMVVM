package com.wjx.android.mvvm.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.mvvm.common.state.State
import com.wjx.android.mvvm.common.state.StateType
import com.wjx.android.mvvm.common.utils.Constant
import com.wjx.android.mvvm.network.response.BaseResponse
import com.wjx.android.mvvm.repository.BaseRepository
import com.wjx.android.mvvm.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:数据解析扩展函数
 * @author: Wangjianxian
 * @CreateDate: 2020/4/19 17:35
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>
): T {
    when (errorCode) {
        Constant.SUCCESS -> {
            if (data is List<*>) {
                if ((data as List<*>).isEmpty()) {
                    loadState.postValue(State(StateType.EMPTY))
                }
            }
            loadState.postValue(State(StateType.SUCCESS))
            return data
        }
        Constant.NOT_LOGIN -> {

            loadState.postValue(State(StateType.ERROR, message = "请重新登录"))
            return data
        }
        else -> {
            loadState.postValue(State(StateType.ERROR, message = errorMsg))
            return data
        }
    }
}


fun <T : BaseRepository> BaseViewModel<T>.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}
