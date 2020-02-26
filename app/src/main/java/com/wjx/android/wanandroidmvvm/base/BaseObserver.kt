package com.wjx.android.wanandroidmvvm.base

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.repository.BaseRepository
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.base.state.StateType
import com.wjx.android.wanandroidmvvm.base.utils.Constant
import io.reactivex.disposables.Disposable

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:44
 */

class BaseObserver<T : BaseResponse<*>>(val liveData : MutableLiveData<T>,
                                        val loadState : MutableLiveData<State>,
                                        val repository: BaseRepository) : Observer<T> {
    override fun onNext(response: T) {
        when(response.errorCode) {
            Constant.SUCCESS -> {
                if (response.data is List<*>) {
                    if ((response.data as List<*>).isEmpty()) {
                        loadState.postValue(State(StateType.EMPTY))
                    }
                }
                loadState.postValue(State(StateType.SUCCESS))
                liveData.postValue(response)
            }

        }
    }

    override fun onSubscribe(disposable: Disposable) {
        repository.addSubscribe(disposable)
    }

    override fun onError(p0: Throwable) {
        loadState.postValue(State(StateType.ERROR))
    }

    override fun onComplete() {
    }

}