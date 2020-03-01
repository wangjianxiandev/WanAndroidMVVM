package com.wjx.android.wanandroidmvvm.ui.account.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.base.state.StateType
import com.wjx.android.wanandroidmvvm.ui.account.data.LoginResponse
import com.wjx.android.wanandroidmvvm.ui.account.data.RegisterResponse
import com.wjx.android.wanandroidmvvm.ui.account.repository.AccountReponsitory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:58
 */
class AccountViewModel(application: Application) : BaseViewModel<AccountReponsitory>(application) {
    val mLoginData = MutableLiveData<BaseResponse<LoginResponse>>()
    val mRegisterData = MutableLiveData<BaseResponse<RegisterResponse>>()

    fun login(username: String, password: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            loadState.postValue(State(StateType.TIP, tip = R.string.accountOrpasswordempty))
        } else {
            mRepository.login(username, password, mLoginData)
        }
    }

    fun register(username: String, password: String, repassword: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty() || password != repassword) {
            loadState.postValue(State(StateType.TIP, tip = R.string.accountOrpasswordempty))
        } else {
            mRepository.register(username, password, repassword, mRegisterData)
        }
    }
}
